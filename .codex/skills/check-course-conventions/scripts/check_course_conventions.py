#!/usr/bin/env python3
"""Checks objective CS2103 Java conventions.

This lightweight checker intentionally leaves semantic style rules for manual
review rather than producing unreliable automated verdicts.
"""

from __future__ import annotations

import argparse
import re
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path


SOFT_LINE_LIMIT = 110
HARD_LINE_LIMIT = 120


@dataclass(frozen=True)
class Finding:
    """Represents one convention issue found by the checker."""

    severity: str
    code: str
    location: str
    message: str


def run_git(*args: str) -> list[str]:
    """Returns NUL-delimited paths produced by a Git command."""
    result = subprocess.run(
        ["git", *args],
        check=False,
        capture_output=True,
        text=True,
    )
    if result.returncode != 0:
        return []
    return [entry for entry in result.stdout.split("\0") if entry]


def repository_java_files(all_files: bool) -> list[Path]:
    """Finds changed Java files, or all Java files when requested or unchanged."""
    tracked: list[str] = []
    if not all_files:
        tracked = run_git("diff", "--name-only", "-z", "--diff-filter=ACMR", "HEAD", "--", "*.java")

    untracked = run_git("ls-files", "--others", "--exclude-standard", "-z", "--", "*.java")
    selected = tracked + untracked
    if all_files or not selected:
        selected = run_git("ls-files", "-z", "--cached", "--others", "--exclude-standard", "--", "*.java")

    return sorted({Path(name) for name in selected if Path(name).is_file()})


def expand_java_paths(raw_paths: list[str]) -> list[Path]:
    """Expands explicit file and directory arguments into Java files."""
    selected: set[Path] = set()
    for raw_path in raw_paths:
        path = Path(raw_path)
        if path.is_dir():
            selected.update(candidate for candidate in path.rglob("*.java") if candidate.is_file())
        elif path.is_file() and path.suffix == ".java":
            selected.add(path)
    return sorted(selected)


def has_preceding_javadoc(lines: list[str], declaration_index: int) -> bool:
    """Checks for a Javadoc block immediately before a declaration or annotations."""
    index = declaration_index - 1
    while index >= 0 and (not lines[index].strip() or lines[index].lstrip().startswith("@")):
        index -= 1
    if index < 0 or not lines[index].strip().endswith("*/"):
        return False
    while index >= 0:
        if "/**" in lines[index]:
            return True
        if "/*" in lines[index] or "//" in lines[index]:
            return False
        index -= 1
    return False


def check_java_file(path: Path) -> list[Finding]:
    """Checks convention rules that can be detected with low ambiguity."""
    findings: list[Finding] = []
    try:
        text = path.read_text(encoding="utf-8")
    except (OSError, UnicodeError) as error:
        return [Finding("ERROR", "JAVA000", str(path), f"cannot read UTF-8 source: {error}")]

    lines = text.splitlines()
    location = str(path)

    if path.name != "module-info.java":
        package_match = re.search(r"(?m)^\s*package\s+([A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*)\s*;", text)
        if package_match is None:
            findings.append(Finding("ERROR", "JAVA001", location, "class is not declared in a package"))
        else:
            package_name = package_match.group(1)
            package_line = text[: package_match.start()].count("\n") + 1
            if package_name != package_name.lower():
                findings.append(Finding("ERROR", "JAVA002", f"{location}:{package_line}", "package name must be lowercase"))
            if package_name.startswith("edu.nus.comp"):
                findings.append(Finding("WARNING", "JAVA003", f"{location}:{package_line}", "school projects should not use an edu.nus.comp package"))

    type_pattern = re.compile(r"\b(?:class|enum|interface|record)\s+([A-Za-z_$][\w$]*)")
    public_type_pattern = re.compile(r"\bpublic\s+(?:(?:abstract|final|sealed|non-sealed|static)\s+)*(?:class|enum|interface|record)\b")
    public_method_pattern = re.compile(
        r"^\s*public\s+(?:(?:abstract|default|final|native|static|strictfp|synchronized)\s+)*"
        r"(?:<[^>]+>\s+)?[\w$<>\[\],.?]+\s+[A-Za-z_$][\w$]*\s*\([^;]*\)"
    )

    for index, line in enumerate(lines, start=1):
        line_location = f"{location}:{index}"
        if "\t" in line:
            findings.append(Finding("ERROR", "JAVA004", line_location, "use four spaces for indentation, not tabs"))

        line_length = len(line)
        if line_length > HARD_LINE_LIMIT:
            findings.append(Finding("ERROR", "JAVA005", line_location, f"line is {line_length} characters; hard limit is {HARD_LINE_LIMIT}"))
        elif line_length > SOFT_LINE_LIMIT:
            findings.append(Finding("WARNING", "JAVA005", line_location, f"line is {line_length} characters; try to keep it within {SOFT_LINE_LIMIT}"))

        if re.match(r"^\s*import\s+(?:static\s+)?[\w.]+\.\*\s*;", line):
            findings.append(Finding("ERROR", "JAVA006", line_location, "list imported classes explicitly; wildcard import found"))

        for type_match in type_pattern.finditer(line):
            type_name = type_match.group(1)
            if not re.fullmatch(r"[A-Z][A-Za-z0-9]*", type_name):
                findings.append(Finding("ERROR", "JAVA007", line_location, f"type name '{type_name}' must use PascalCase"))

        if public_type_pattern.search(line) and not has_preceding_javadoc(lines, index - 1):
            findings.append(Finding("WARNING", "JAVA008", line_location, "public type may be missing its required Javadoc header"))

        stripped_line = line.split("//", 1)[0]
        if public_method_pattern.search(stripped_line) and not has_preceding_javadoc(lines, index - 1):
            findings.append(Finding("WARNING", "JAVA009", line_location, "public method may be missing its required Javadoc header"))

    return findings


def parse_args() -> argparse.Namespace:
    """Parses command-line arguments."""
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("paths", nargs="*", help="Java files or directories; defaults to changed Java files")
    parser.add_argument("--all-java", action="store_true", help="check all Java files in the repository")
    return parser.parse_args()


def main() -> int:
    """Runs the selected checks and returns a process-friendly status code."""
    args = parse_args()
    java_files = expand_java_paths(args.paths) if args.paths else repository_java_files(args.all_java)
    findings: list[Finding] = []

    if args.paths and not java_files:
        findings.append(Finding("ERROR", "TOOL001", "arguments", "no Java files found in the requested paths"))
    for java_file in java_files:
        findings.extend(check_java_file(java_file))

    for finding in findings:
        print(f"{finding.severity} {finding.code} {finding.location}: {finding.message}")

    errors = sum(finding.severity == "ERROR" for finding in findings)
    warnings = sum(finding.severity == "WARNING" for finding in findings)
    print(f"Checked {len(java_files)} Java file(s): {errors} error(s), {warnings} warning(s).")
    print("Manually review semantic naming, layout, control-flow braces, variable scope, and complete Javadoc coverage.")
    return 1 if errors else 0


if __name__ == "__main__":
    sys.exit(main())
