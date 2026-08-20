---
name: test-ui
description: Record and run command-line UI test cases for this project. Use when given commands and expected console outputs to test the application interactively; stop at the first mismatch and report the captured session.
---

# Test UI

Treat `test/ui-test-plan.md` as the source of truth for UI test cases. The project uses Java 25; before compiling or launching it, select `25.0.3.fx-zulu` with SDKMAN as required by `AGENTS.md`.

## Record the test plan

Before running tests, add or update the supplied cases in `test/ui-test-plan.md`. Preserve unrelated existing cases. Every case must have:

- a stable ID and concise title;
- an aim explaining the behavior being checked;
- any setup or preconditions;
- an ordered list of input commands, including blank input when significant;
- the exact output expected after startup and after each command, as applicable.

Also record the working directory, build and launch commands, comparison rules, timeout, and any state-reset procedure needed for repeatable runs. Infer a concise aim when the supplied intent is obvious. If a missing expected output or command makes comparison impossible, ask for that information instead of inventing it.

## Run the cases

Inspect the repository documentation and build files to determine how to compile and launch the program. A build is a preflight check, not part of the captured UI output.

Run cases in plan order, using a fresh program process and the recorded initial state for each case. For every case:

1. Start the program and compare its startup output, if the case specifies any.
2. Send one recorded command at a time, exactly as written.
3. Capture the output attributable to that command before sending the next command. Prefer an existing UI-test harness; otherwise use a PTY-backed process and wait for the prompt, process exit, or the documented idle timeout.
4. Compare actual and expected output exactly after normalizing only `CRLF` to `LF`, unless the plan explicitly records another rule. Preserve blank lines, spacing, punctuation, and letter case. Treat unexpected standard-error output, a non-zero exit, or a timeout as a failure unless the case explicitly expects it.
5. End the process with a recorded exit command or EOF. Do not silently add an input command.

Do not modify application code or expected output merely to make a failing test pass.

## Stop and report

On the first failure, terminate the running process if necessary and do not run later cases. Report:

- the failed case and command;
- the actual output and expected output verbatim in separate fenced blocks;
- a focused diff when it helps reveal the mismatch;
- the exit status, standard error, or timeout details when relevant;
- later cases as not run.

After either success or failure, show the captured console session for every case that started. Prefer a chronological transcript with input clearly marked; if the execution method does not echo input reliably, show separate `Console input` and `Console output` blocks rather than fabricating echoed lines. Finish with counts for passed, failed, and not-run cases.
