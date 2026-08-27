---
name: craft-commit-message
description: Craft or review a course-compliant Git commit message when the user explicitly requests a message suggestion, message review, or commit creation. Do not use for change summaries, convention audits, or push-only requests.
---

# Craft Commit Message

Craft a message that accurately explains the repository changes and their rationale.
Use it to create a commit only when the user requested commit creation.

## Gather context

1. Read the repository's instructions and follow any project-specific commit conventions.
2. Inspect `git status` and the relevant staged, unstaged, or committed diff. Use the scope identified by the user; otherwise consider all current changes without modifying the index or worktree.
3. Ask for clarification only when the intended purpose cannot be inferred safely from the changes or conversation.

## Write the message

- Follow the [SE-EDU Git conventions](https://se-education.org/guides/conventions/git.html) required by the course.
- Use an imperative subject that states the main outcome, capitalize its first letter, and do not end it with a period.
- Aim for at most 50 characters in the subject and never exceed 72. An applicable `<scope>:` or `<category>:` prefix is allowed; do not add a conventional-commit prefix unless the project or user requires one.
- A body is optional for trivial commits but should be included for nontrivial changes or when project instructions require the rationale. Separate it from the subject with a blank line and wrap its lines at 72 characters.
- In a body, explain what changed and why rather than narrating how the diff implements it. Separate paragraphs with blank lines and use bullets where they improve clarity.
- Keep claims grounded in the inspected changes. Do not invent issue numbers, test results, or motivations.

Before returning or using the message, verify every subject rule above and, when a body is present,
its blank-line separation and 72-character wrapping.

For a suggestion or review, return the message in a copyable code block and do not
modify the repository. A commit request authorizes staging only the changes in scope
and creating the commit. Do not push unless the user also asks.
