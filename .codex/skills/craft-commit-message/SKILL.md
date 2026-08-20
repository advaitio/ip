---
name: craft-commit-message
description: Craft or review a Git commit message from the current repository's changes. Use when asked to write, propose, refine, or evaluate a commit message, including after reviewing changes with present-changes-visually. Do not use merely to summarize changes when no commit message is requested.
---

# Craft Commit Message

Create a commit message that accurately explains the repository changes and their rationale.

## Gather context

1. Read the repository's instructions and follow any project-specific commit conventions.
2. Inspect `git status` and the relevant staged, unstaged, or committed diff. Use the scope identified by the user; otherwise consider all current changes without modifying the index or worktree.
3. Ask for clarification only when the intended purpose cannot be inferred safely from the changes or conversation.

## Write the message

- Use an imperative subject that states the main outcome.
- Keep the subject concise and specific. Use a conventional-commit prefix only when the project or user requires one.
- Add a body after a blank line. Explain the important changes and why they were made, including relevant design decisions or user-visible effects.
- Keep claims grounded in the inspected changes. Do not invent issue numbers, test results, or motivations.
- Return the proposed message in a copyable code block unless the user requests another format.

Do not stage files, create a commit, or push changes unless the user explicitly asks for those actions.
