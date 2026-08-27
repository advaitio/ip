---
name: check-course-conventions
description: Audit this project's Java against required course conventions. Invoke only through an explicit user request for $check-course-conventions; never select it automatically for Java edits, reviews, commits, or pushes.
---

# Check Course Conventions

Check the requested Java scope against the [SE-EDU basic and intermediate Java rules](https://se-education.org/guides/conventions/java/intermediate.html) required by the current AY2026/27 Semester 1 [course standards page](https://nus-cs2103-ay2627-s1.github.io/website/admin/standardsAndConventions.html).

Do not present optional Java advanced, Markdown, or documentation guidance as mandatory.

Run the bundled validator from the repository root:

```bash
python3 .codex/skills/check-course-conventions/scripts/check_course_conventions.py
```

By default, it checks changed and untracked Java files, falling back to all repository Java files when none have changed. Pass file or directory paths to narrow the scope, or use `--all-java` for a full audit.

Treat reported errors as failures. Review warnings and manually inspect the semantic rules that a lightweight script cannot determine reliably, including meaningful names, method names as verbs, boolean and collection naming, import-order consistency, wrapped-line indentation, K&R layout, whitespace within statements, variable scope, braces around control-flow bodies, and complete Javadoc coverage.

When Java implementation is authorized, correct convention problems introduced in or directly affected by that implementation. Report unrelated pre-existing problems instead of expanding the change without permission.

Report findings with file and line locations where available, distinguish new problems from pre-existing ones, and state which checks were manual. Do not claim that the script alone proves full compliance.
