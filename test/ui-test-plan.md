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
    > Nudge received! I've added:
      [T][ ] borrow book
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 2

**Input:**

```text
todo return book
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] return book
    > You now have 2 tasks on your radar.
____________________________________________________________
```

#### Command 3

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[T][ ] borrow book
      2.[T][ ] return book
____________________________________________________________
```

#### Command 4

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

#### Command 5

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

#### Command 6

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
    > Nudge received! I've added:
      [D][ ] return book (by: Sunday)
    > You now have 1 task on your radar.
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

### UI-003: Add and mark an event

**Aim:** Verify that an event can be added, listed, and marked with its start and end times preserved.

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
event project meeting /from Mon 2pm /to 4pm
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [E][ ] project meeting (from: Mon 2pm to: 4pm)
    > You now have 1 task on your radar.
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
      1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
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
      [E][X] project meeting (from: Mon 2pm to: 4pm)
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

### UI-004: Recover from basic input errors

**Aim:** Verify that empty todos and unknown commands produce helpful errors without adding tasks or stopping Nudge.

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
todo
```

**Expected output:**

```text
____________________________________________________________
    > A todo needs a description. Try: todo DESCRIPTION
____________________________________________________________
```

#### Command 2

**Input:**

```text
blah
```

**Expected output:**

```text
____________________________________________________________
    > I don't recognize that command. Try: todo, deadline, event, list, mark, unmark, or bye.
____________________________________________________________
```

#### Command 3

**Input:**

```text
todo revise exceptions
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] revise exceptions
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 4

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[T][ ] revise exceptions
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

### UI-005: Validate task status command indices

**Aim:** Verify that invalid mark and unmark indices produce helpful errors without changing task state or stopping Nudge.

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
mark
```

**Expected output:**

```text
____________________________________________________________
    > `mark` needs a task number. Try: mark NUMBER
____________________________________________________________
```

#### Command 2

**Input:**

```text
unmark one
```

**Expected output:**

```text
____________________________________________________________
    > The task number must be a whole number. Try: unmark NUMBER
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
    > There are no tasks in your list yet.
____________________________________________________________
```

#### Command 4

**Input:**

```text
todo review code
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] review code
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 5

**Input:**

```text
mark 0
```

**Expected output:**

```text
____________________________________________________________
    > Choose task number 1.
____________________________________________________________
```

#### Command 6

**Input:**

```text
unmark 2
```

**Expected output:**

```text
____________________________________________________________
    > Choose task number 1.
____________________________________________________________
```

#### Command 7

**Input:**

```text
mark 1
```

**Expected output:**

```text
____________________________________________________________
    > Nice! I've marked this task as done:
      [T][X] review code
____________________________________________________________
```

#### Command 8

**Input:**

```text
unmark 1
```

**Expected output:**

```text
____________________________________________________________
    > OK, I've marked this task as not done yet:
      [T][ ] review code
____________________________________________________________
```

#### Command 9

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[T][ ] review code
____________________________________________________________
```

#### Command 10

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
