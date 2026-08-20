# UI Test Plan

This file is the source of truth for command-line UI test cases run with the `test-ui` skill.

## Test configuration

- Application: Nudge command-line interface
- Working directory: repository root
- Java runtime: `25.0.3.fx-zulu`
- Build command: `javac -d /tmp/nudge-ui-test-classes src/main/java/nudge/*.java`
- Launch command: `java -cp /tmp/nudge-ui-test-classes nudge.Nudge`
- Comparison: Exact text after normalizing `CRLF` line endings to `LF` only.
- Timeout: One second of inactivity after each command.
- Isolation: Compile once, then start a fresh program process for every test case. Nudge does not persist tasks.
- Failure policy: Stop at the first failed comparison; later cases are not run.

## Test cases

### UI-001: Add and update a todo

**Aim:** Verify that a todo can be added, listed, marked, and unmarked with the correct type and status.

**Setup and preconditions:** Start Nudge with an empty task list.

**Startup expected output:**

```text
____________________________________________________________
 _   _           _            
| \ | |_   _  __| | __ _  ___ 
|  \| | | | |/ _` |/ _` |/ _ \
| |\  | |_| | (_| | (_| |  __/
|_| \_|\__,_|\__,_|\__, |\___|
                   |___/
    > Hey! I'm Nudge. How can I help you today?
____________________________________________________________
```

#### Command 1

**Input:**

```text
todo borrow book
```

**Expected output:**

```text
____________________________________________________________
    > added: [T][ ] borrow book
____________________________________________________________
```

#### Command 2

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[T][ ] borrow book
____________________________________________________________
```

#### Command 3

**Input:**

```text
mark 1
```

**Expected output:**

```text
____________________________________________________________
    > Nice! I've marked this task as done:
      [T][X] borrow book
____________________________________________________________
```

#### Command 4

**Input:**

```text
unmark 1
```

**Expected output:**

```text
____________________________________________________________
    > OK, I've marked this task as not done yet:
      [T][ ] borrow book
____________________________________________________________
```

#### Command 5

**Input:**

```text
bye
```

**Expected output:**

```text
____________________________________________________________
    > Okay, I'll leave you to it. I'll be here if you need another nudge!
____________________________________________________________
```

### UI-002: Add and mark a deadline

**Aim:** Verify that a deadline can be added, listed, and marked with its due time preserved.

**Setup and preconditions:** Start Nudge with an empty task list.

**Startup expected output:**

```text
____________________________________________________________
 _   _           _            
| \ | |_   _  __| | __ _  ___ 
|  \| | | | |/ _` |/ _` |/ _ \
| |\  | |_| | (_| | (_| |  __/
|_| \_|\__,_|\__,_|\__, |\___|
                   |___/
    > Hey! I'm Nudge. How can I help you today?
____________________________________________________________
```

#### Command 1

**Input:**

```text
deadline return book /by Sunday
```

**Expected output:**

```text
____________________________________________________________
    > added: [D][ ] return book (by: Sunday)
____________________________________________________________
```

#### Command 2

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[D][ ] return book (by: Sunday)
____________________________________________________________
```

#### Command 3

**Input:**

```text
mark 1
```

**Expected output:**

```text
____________________________________________________________
    > Nice! I've marked this task as done:
      [D][X] return book (by: Sunday)
____________________________________________________________
```

#### Command 4

**Input:**

```text
bye
```

**Expected output:**

```text
____________________________________________________________
    > Okay, I'll leave you to it. I'll be here if you need another nudge!
____________________________________________________________
```
