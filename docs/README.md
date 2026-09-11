# Nudge User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Sorting deadlines

Use `sort` to arrange deadlines from the earliest due date to the latest:

```text
sort
```

Nudge sorts deadlines only among positions already occupied by deadlines. Todos and
events stay in their existing positions. Deadlines with the same due date keep their
relative order.

For example, given this task list:

```text
1.[D][ ] submit report (by: Oct 20 2026)
2.[T][ ] read textbook
3.[D][ ] return book (by: Sep 15 2026)
```

`sort` produces:

```text
Here are your tasks, with deadlines sorted by date:
1.[D][ ] return book (by: Sep 15 2026)
2.[T][ ] read textbook
3.[D][ ] submit report (by: Oct 20 2026)
```

The new order is saved and determines the task numbers used by commands such as
`mark`, `unmark`, and `delete`. Tasks added later are appended normally; run `sort`
again when you want to reorder the deadlines.

`sort` does not accept arguments. For example, `sort asc` produces:

```text
`sort` does not take any arguments. Try: sort
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
