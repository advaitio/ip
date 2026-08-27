---
name: test-ui
description: Maintain and run this project's data-driven command-line UI tests. Use when adding cases or validating observable CLI behavior; do not use solely for an internal refactor whose CLI behavior is unchanged.
---

# Test UI

Use `test/ui-test-plan.md` for the coverage overview, `test/ui-test-cases.json` for
case data, and `test/run_ui_tests.py` for deterministic batch execution. The runner
checks Java 25, compiles once, starts a fresh process per case, compares exact output,
and stops before later cases after a failure.

## Maintain cases

Preserve unrelated cases and execution order. Record each case's stable ID, title,
aim, setup, input commands, and expected response data in the JSON fixture. Use a
closed `repeat` range for genuinely repetitive commands. Update the plan's coverage
table when cases or expanded command counts change.

Do not invent missing commands or expectations. Do not change application behavior
or expectations merely to make a failure pass.

## Choose the scope

- Do not invoke this skill for an internal refactor with unchanged CLI behavior.
- Run affected case IDs for a localized command or output change.
- Run all cases for cross-cutting parsing, dispatch, output-framing, or task-storage
  changes; changes to the harness or shared expectations; and project milestones.

## Run cases

From the repository root, run all cases with:

```bash
python3 test/run_ui_tests.py
```

For affected cases only, repeat `--case`, for example:

```bash
python3 test/run_ui_tests.py --case UI-001 --case UI-004
```

## Report

On success, report only passed, failed, and not-run counts. Do not reproduce successful
console transcripts. On failure, report the case, originating command, reason, focused
diff, counts, and the path to `_temp/ui-test-failure.txt`, which contains complete input,
expected output, actual output, and standard error. Do not run later cases after failure.
