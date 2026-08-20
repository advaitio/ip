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

### UI-007: Store more than 100 tasks

**Aim:** Verify that Nudge accepts and updates task 101 after storing 100 tasks, and continues running.

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
todo capacity task 1
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 1
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 2

**Input:**

```text
todo capacity task 2
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 2
    > You now have 2 tasks on your radar.
____________________________________________________________
```

#### Command 3

**Input:**

```text
todo capacity task 3
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 3
    > You now have 3 tasks on your radar.
____________________________________________________________
```

#### Command 4

**Input:**

```text
todo capacity task 4
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 4
    > You now have 4 tasks on your radar.
____________________________________________________________
```

#### Command 5

**Input:**

```text
todo capacity task 5
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 5
    > You now have 5 tasks on your radar.
____________________________________________________________
```

#### Command 6

**Input:**

```text
todo capacity task 6
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 6
    > You now have 6 tasks on your radar.
____________________________________________________________
```

#### Command 7

**Input:**

```text
todo capacity task 7
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 7
    > You now have 7 tasks on your radar.
____________________________________________________________
```

#### Command 8

**Input:**

```text
todo capacity task 8
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 8
    > You now have 8 tasks on your radar.
____________________________________________________________
```

#### Command 9

**Input:**

```text
todo capacity task 9
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 9
    > You now have 9 tasks on your radar.
____________________________________________________________
```

#### Command 10

**Input:**

```text
todo capacity task 10
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 10
    > You now have 10 tasks on your radar.
____________________________________________________________
```

#### Command 11

**Input:**

```text
todo capacity task 11
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 11
    > You now have 11 tasks on your radar.
____________________________________________________________
```

#### Command 12

**Input:**

```text
todo capacity task 12
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 12
    > You now have 12 tasks on your radar.
____________________________________________________________
```

#### Command 13

**Input:**

```text
todo capacity task 13
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 13
    > You now have 13 tasks on your radar.
____________________________________________________________
```

#### Command 14

**Input:**

```text
todo capacity task 14
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 14
    > You now have 14 tasks on your radar.
____________________________________________________________
```

#### Command 15

**Input:**

```text
todo capacity task 15
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 15
    > You now have 15 tasks on your radar.
____________________________________________________________
```

#### Command 16

**Input:**

```text
todo capacity task 16
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 16
    > You now have 16 tasks on your radar.
____________________________________________________________
```

#### Command 17

**Input:**

```text
todo capacity task 17
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 17
    > You now have 17 tasks on your radar.
____________________________________________________________
```

#### Command 18

**Input:**

```text
todo capacity task 18
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 18
    > You now have 18 tasks on your radar.
____________________________________________________________
```

#### Command 19

**Input:**

```text
todo capacity task 19
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 19
    > You now have 19 tasks on your radar.
____________________________________________________________
```

#### Command 20

**Input:**

```text
todo capacity task 20
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 20
    > You now have 20 tasks on your radar.
____________________________________________________________
```

#### Command 21

**Input:**

```text
todo capacity task 21
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 21
    > You now have 21 tasks on your radar.
____________________________________________________________
```

#### Command 22

**Input:**

```text
todo capacity task 22
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 22
    > You now have 22 tasks on your radar.
____________________________________________________________
```

#### Command 23

**Input:**

```text
todo capacity task 23
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 23
    > You now have 23 tasks on your radar.
____________________________________________________________
```

#### Command 24

**Input:**

```text
todo capacity task 24
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 24
    > You now have 24 tasks on your radar.
____________________________________________________________
```

#### Command 25

**Input:**

```text
todo capacity task 25
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 25
    > You now have 25 tasks on your radar.
____________________________________________________________
```

#### Command 26

**Input:**

```text
todo capacity task 26
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 26
    > You now have 26 tasks on your radar.
____________________________________________________________
```


#### Command 27

**Input:**

```text
todo capacity task 27
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 27
    > You now have 27 tasks on your radar.
____________________________________________________________
```


#### Command 28

**Input:**

```text
todo capacity task 28
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 28
    > You now have 28 tasks on your radar.
____________________________________________________________
```


#### Command 29

**Input:**

```text
todo capacity task 29
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 29
    > You now have 29 tasks on your radar.
____________________________________________________________
```


#### Command 30

**Input:**

```text
todo capacity task 30
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 30
    > You now have 30 tasks on your radar.
____________________________________________________________
```


#### Command 31

**Input:**

```text
todo capacity task 31
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 31
    > You now have 31 tasks on your radar.
____________________________________________________________
```


#### Command 32

**Input:**

```text
todo capacity task 32
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 32
    > You now have 32 tasks on your radar.
____________________________________________________________
```


#### Command 33

**Input:**

```text
todo capacity task 33
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 33
    > You now have 33 tasks on your radar.
____________________________________________________________
```


#### Command 34

**Input:**

```text
todo capacity task 34
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 34
    > You now have 34 tasks on your radar.
____________________________________________________________
```


#### Command 35

**Input:**

```text
todo capacity task 35
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 35
    > You now have 35 tasks on your radar.
____________________________________________________________
```


#### Command 36

**Input:**

```text
todo capacity task 36
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 36
    > You now have 36 tasks on your radar.
____________________________________________________________
```


#### Command 37

**Input:**

```text
todo capacity task 37
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 37
    > You now have 37 tasks on your radar.
____________________________________________________________
```


#### Command 38

**Input:**

```text
todo capacity task 38
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 38
    > You now have 38 tasks on your radar.
____________________________________________________________
```


#### Command 39

**Input:**

```text
todo capacity task 39
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 39
    > You now have 39 tasks on your radar.
____________________________________________________________
```


#### Command 40

**Input:**

```text
todo capacity task 40
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 40
    > You now have 40 tasks on your radar.
____________________________________________________________
```


#### Command 41

**Input:**

```text
todo capacity task 41
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 41
    > You now have 41 tasks on your radar.
____________________________________________________________
```


#### Command 42

**Input:**

```text
todo capacity task 42
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 42
    > You now have 42 tasks on your radar.
____________________________________________________________
```


#### Command 43

**Input:**

```text
todo capacity task 43
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 43
    > You now have 43 tasks on your radar.
____________________________________________________________
```


#### Command 44

**Input:**

```text
todo capacity task 44
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 44
    > You now have 44 tasks on your radar.
____________________________________________________________
```


#### Command 45

**Input:**

```text
todo capacity task 45
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 45
    > You now have 45 tasks on your radar.
____________________________________________________________
```


#### Command 46

**Input:**

```text
todo capacity task 46
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 46
    > You now have 46 tasks on your radar.
____________________________________________________________
```


#### Command 47

**Input:**

```text
todo capacity task 47
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 47
    > You now have 47 tasks on your radar.
____________________________________________________________
```


#### Command 48

**Input:**

```text
todo capacity task 48
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 48
    > You now have 48 tasks on your radar.
____________________________________________________________
```


#### Command 49

**Input:**

```text
todo capacity task 49
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 49
    > You now have 49 tasks on your radar.
____________________________________________________________
```


#### Command 50

**Input:**

```text
todo capacity task 50
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 50
    > You now have 50 tasks on your radar.
____________________________________________________________
```

#### Command 51

**Input:**

```text
todo capacity task 51
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 51
    > You now have 51 tasks on your radar.
____________________________________________________________
```


#### Command 52

**Input:**

```text
todo capacity task 52
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 52
    > You now have 52 tasks on your radar.
____________________________________________________________
```


#### Command 53

**Input:**

```text
todo capacity task 53
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 53
    > You now have 53 tasks on your radar.
____________________________________________________________
```


#### Command 54

**Input:**

```text
todo capacity task 54
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 54
    > You now have 54 tasks on your radar.
____________________________________________________________
```


#### Command 55

**Input:**

```text
todo capacity task 55
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 55
    > You now have 55 tasks on your radar.
____________________________________________________________
```


#### Command 56

**Input:**

```text
todo capacity task 56
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 56
    > You now have 56 tasks on your radar.
____________________________________________________________
```


#### Command 57

**Input:**

```text
todo capacity task 57
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 57
    > You now have 57 tasks on your radar.
____________________________________________________________
```


#### Command 58

**Input:**

```text
todo capacity task 58
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 58
    > You now have 58 tasks on your radar.
____________________________________________________________
```


#### Command 59

**Input:**

```text
todo capacity task 59
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 59
    > You now have 59 tasks on your radar.
____________________________________________________________
```


#### Command 60

**Input:**

```text
todo capacity task 60
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 60
    > You now have 60 tasks on your radar.
____________________________________________________________
```


#### Command 61

**Input:**

```text
todo capacity task 61
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 61
    > You now have 61 tasks on your radar.
____________________________________________________________
```


#### Command 62

**Input:**

```text
todo capacity task 62
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 62
    > You now have 62 tasks on your radar.
____________________________________________________________
```


#### Command 63

**Input:**

```text
todo capacity task 63
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 63
    > You now have 63 tasks on your radar.
____________________________________________________________
```


#### Command 64

**Input:**

```text
todo capacity task 64
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 64
    > You now have 64 tasks on your radar.
____________________________________________________________
```


#### Command 65

**Input:**

```text
todo capacity task 65
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 65
    > You now have 65 tasks on your radar.
____________________________________________________________
```


#### Command 66

**Input:**

```text
todo capacity task 66
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 66
    > You now have 66 tasks on your radar.
____________________________________________________________
```


#### Command 67

**Input:**

```text
todo capacity task 67
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 67
    > You now have 67 tasks on your radar.
____________________________________________________________
```


#### Command 68

**Input:**

```text
todo capacity task 68
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 68
    > You now have 68 tasks on your radar.
____________________________________________________________
```


#### Command 69

**Input:**

```text
todo capacity task 69
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 69
    > You now have 69 tasks on your radar.
____________________________________________________________
```


#### Command 70

**Input:**

```text
todo capacity task 70
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 70
    > You now have 70 tasks on your radar.
____________________________________________________________
```


#### Command 71

**Input:**

```text
todo capacity task 71
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 71
    > You now have 71 tasks on your radar.
____________________________________________________________
```


#### Command 72

**Input:**

```text
todo capacity task 72
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 72
    > You now have 72 tasks on your radar.
____________________________________________________________
```


#### Command 73

**Input:**

```text
todo capacity task 73
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 73
    > You now have 73 tasks on your radar.
____________________________________________________________
```


#### Command 74

**Input:**

```text
todo capacity task 74
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 74
    > You now have 74 tasks on your radar.
____________________________________________________________
```


#### Command 75

**Input:**

```text
todo capacity task 75
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 75
    > You now have 75 tasks on your radar.
____________________________________________________________
```

#### Command 76

**Input:**

```text
todo capacity task 76
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 76
    > You now have 76 tasks on your radar.
____________________________________________________________
```


#### Command 77

**Input:**

```text
todo capacity task 77
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 77
    > You now have 77 tasks on your radar.
____________________________________________________________
```


#### Command 78

**Input:**

```text
todo capacity task 78
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 78
    > You now have 78 tasks on your radar.
____________________________________________________________
```


#### Command 79

**Input:**

```text
todo capacity task 79
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 79
    > You now have 79 tasks on your radar.
____________________________________________________________
```


#### Command 80

**Input:**

```text
todo capacity task 80
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 80
    > You now have 80 tasks on your radar.
____________________________________________________________
```


#### Command 81

**Input:**

```text
todo capacity task 81
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 81
    > You now have 81 tasks on your radar.
____________________________________________________________
```


#### Command 82

**Input:**

```text
todo capacity task 82
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 82
    > You now have 82 tasks on your radar.
____________________________________________________________
```


#### Command 83

**Input:**

```text
todo capacity task 83
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 83
    > You now have 83 tasks on your radar.
____________________________________________________________
```


#### Command 84

**Input:**

```text
todo capacity task 84
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 84
    > You now have 84 tasks on your radar.
____________________________________________________________
```


#### Command 85

**Input:**

```text
todo capacity task 85
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 85
    > You now have 85 tasks on your radar.
____________________________________________________________
```


#### Command 86

**Input:**

```text
todo capacity task 86
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 86
    > You now have 86 tasks on your radar.
____________________________________________________________
```


#### Command 87

**Input:**

```text
todo capacity task 87
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 87
    > You now have 87 tasks on your radar.
____________________________________________________________
```


#### Command 88

**Input:**

```text
todo capacity task 88
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 88
    > You now have 88 tasks on your radar.
____________________________________________________________
```


#### Command 89

**Input:**

```text
todo capacity task 89
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 89
    > You now have 89 tasks on your radar.
____________________________________________________________
```


#### Command 90

**Input:**

```text
todo capacity task 90
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 90
    > You now have 90 tasks on your radar.
____________________________________________________________
```


#### Command 91

**Input:**

```text
todo capacity task 91
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 91
    > You now have 91 tasks on your radar.
____________________________________________________________
```


#### Command 92

**Input:**

```text
todo capacity task 92
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 92
    > You now have 92 tasks on your radar.
____________________________________________________________
```


#### Command 93

**Input:**

```text
todo capacity task 93
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 93
    > You now have 93 tasks on your radar.
____________________________________________________________
```


#### Command 94

**Input:**

```text
todo capacity task 94
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 94
    > You now have 94 tasks on your radar.
____________________________________________________________
```


#### Command 95

**Input:**

```text
todo capacity task 95
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 95
    > You now have 95 tasks on your radar.
____________________________________________________________
```


#### Command 96

**Input:**

```text
todo capacity task 96
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 96
    > You now have 96 tasks on your radar.
____________________________________________________________
```


#### Command 97

**Input:**

```text
todo capacity task 97
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 97
    > You now have 97 tasks on your radar.
____________________________________________________________
```


#### Command 98

**Input:**

```text
todo capacity task 98
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 98
    > You now have 98 tasks on your radar.
____________________________________________________________
```


#### Command 99

**Input:**

```text
todo capacity task 99
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 99
    > You now have 99 tasks on your radar.
____________________________________________________________
```


#### Command 100

**Input:**

```text
todo capacity task 100
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 100
    > You now have 100 tasks on your radar.
____________________________________________________________
```

#### Command 101

**Input:**

```text
todo capacity task 101
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [T][ ] capacity task 101
    > You now have 101 tasks on your radar.
____________________________________________________________
```

#### Command 102

**Input:**

```text
mark 101
```

**Expected output:**

```text
____________________________________________________________
    > Nice! I've marked this task as done:
      [T][X] capacity task 101
____________________________________________________________
```

#### Command 103

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
    > I don't recognize that command. Try: todo, deadline, event, list, mark, unmark, delete, or bye.
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

### UI-008: Delete tasks and renumber the list

**Aim:** Verify that deleting tasks reports the removed task and remaining count, while preserving the order and numbering of other tasks.

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
deadline return book /by Sunday
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [D][ ] return book (by: Sunday)
    > You now have 2 tasks on your radar.
____________________________________________________________
```

#### Command 3

**Input:**

```text
event project meeting /from Mon 2pm /to 4pm
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [E][ ] project meeting (from: Mon 2pm to: 4pm)
    > You now have 3 tasks on your radar.
____________________________________________________________
```

#### Command 4

**Input:**

```text
delete 2
```

**Expected output:**

```text
____________________________________________________________
    > Noted. I've removed this task:
      [D][ ] return book (by: Sunday)
    > You now have 2 tasks on your radar.
____________________________________________________________
```

#### Command 5

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[T][ ] borrow book
      2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
```

#### Command 6

**Input:**

```text
delete 2
```

**Expected output:**

```text
____________________________________________________________
    > Noted. I've removed this task:
      [E][ ] project meeting (from: Mon 2pm to: 4pm)
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 7

**Input:**

```text
delete 1
```

**Expected output:**

```text
____________________________________________________________
    > Noted. I've removed this task:
      [T][ ] borrow book
    > You now have 0 tasks on your radar.
____________________________________________________________
```

#### Command 8

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
____________________________________________________________
```

#### Command 9

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

### UI-009: Validate delete command indices

**Aim:** Verify that invalid delete indices produce helpful errors without removing tasks or stopping Nudge.

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
delete
```

**Expected output:**

```text
____________________________________________________________
    > `delete` needs a task number. Try: delete NUMBER
____________________________________________________________
```

#### Command 2

**Input:**

```text
delete one
```

**Expected output:**

```text
____________________________________________________________
    > The task number must be a whole number. Try: delete NUMBER
____________________________________________________________
```

#### Command 3

**Input:**

```text
delete 1
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
delete 0
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
delete 2
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
delete 1
```

**Expected output:**

```text
____________________________________________________________
    > Noted. I've removed this task:
      [T][ ] review code
    > You now have 0 tasks on your radar.
____________________________________________________________
```

#### Command 8

**Input:**

```text
delete 1
```

**Expected output:**

```text
____________________________________________________________
    > There are no tasks in your list yet.
____________________________________________________________
```

#### Command 9

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

### UI-006: Validate deadline and event input

**Aim:** Verify that malformed deadline and event commands produce specific errors without adding tasks or stopping Nudge.

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
deadline
```

**Expected output:**

```text
____________________________________________________________
    > A deadline needs a description. Try: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
```

#### Command 2

**Input:**

```text
deadline submit report
```

**Expected output:**

```text
____________________________________________________________
    > A deadline needs `/by` before its due date. Try: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
```

#### Command 3

**Input:**

```text
deadline submit report /by
```

**Expected output:**

```text
____________________________________________________________
    > A deadline needs a date or time after `/by`. Try: deadline DESCRIPTION /by DATE_OR_TIME
____________________________________________________________
```

#### Command 4

**Input:**

```text
deadline submit report /by Friday
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [D][ ] submit report (by: Friday)
    > You now have 1 task on your radar.
____________________________________________________________
```

#### Command 5

**Input:**

```text
event
```

**Expected output:**

```text
____________________________________________________________
    > An event needs a description. Try: event DESCRIPTION /from START /to END
____________________________________________________________
```

#### Command 6

**Input:**

```text
event project meeting /to 4pm
```

**Expected output:**

```text
____________________________________________________________
    > An event needs `/from` before its start time. Try: event DESCRIPTION /from START /to END
____________________________________________________________
```

#### Command 7

**Input:**

```text
event project meeting /from 2pm
```

**Expected output:**

```text
____________________________________________________________
    > An event needs `/to` before its end time. Try: event DESCRIPTION /from START /to END
____________________________________________________________
```

#### Command 8

**Input:**

```text
event project meeting /from /to 4pm
```

**Expected output:**

```text
____________________________________________________________
    > An event needs a start time after `/from`. Try: event DESCRIPTION /from START /to END
____________________________________________________________
```

#### Command 9

**Input:**

```text
event project meeting /from 2pm /to
```

**Expected output:**

```text
____________________________________________________________
    > An event needs an end time after `/to`. Try: event DESCRIPTION /from START /to END
____________________________________________________________
```

#### Command 10

**Input:**

```text
event project meeting /from 2pm /to 4pm
```

**Expected output:**

```text
____________________________________________________________
    > Nudge received! I've added:
      [E][ ] project meeting (from: 2pm to: 4pm)
    > You now have 2 tasks on your radar.
____________________________________________________________
```

#### Command 11

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
    > Here are the tasks in your list:
      1.[D][ ] submit report (by: Friday)
      2.[E][ ] project meeting (from: 2pm to: 4pm)
____________________________________________________________
```

#### Command 12

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
