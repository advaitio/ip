# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Course website

Use the current AY2026/27 Semester 1 course website for course requirements and guidance: https://nus-cs2103-ay2627-s1.github.io/website/index.html

Do not use course websites from previous semesters.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: Intermediate beginner; completed introductory programming and OOP courses, but still learning software engineering practices.
* IDE and level of expertise: IntelliJ IDEA; only basic familiarity.

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Course standards and conventions

* Treat only the requirements marked REQUIRED on the current [course standards page](https://nus-cs2103-ay2627-s1.github.io/website/admin/standardsAndConventions.html) as mandatory unless another project requirement says otherwise.
* Java code must follow the basic and intermediate rules in the [SE-EDU Java coding standard](https://se-education.org/guides/conventions/java/intermediate.html).
* After modifying or reviewing Java code, and before committing Java changes, use the `check-course-conventions` skill and run its validator on the relevant files. Automated checks supplement, but do not replace, manual review of rules requiring semantic judgment. Do not run it for ordinary questions or changes unrelated to Java.
* Git commit subjects must follow the [SE-EDU Git conventions](https://se-education.org/guides/conventions/git.html). A commit body is optional, but when present it must follow at least the basic body conventions. When proposing or creating a commit, use the `craft-commit-message` skill.

## UI testing

* After every application code update, review the coverage in `test/ui-test-plan.md` and update the data in `test/ui-test-cases.json` when changed behavior or required coverage makes an update necessary.
* After updating the UI cases if needed, invoke the `test-ui` skill. Run the applicable batch cases and follow its stop-on-failure and concise reporting requirements.
* Do not change expected output merely to make a failing test pass. Update expectations only when the intended application behavior has changed.

## Git

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.
