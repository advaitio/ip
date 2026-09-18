# Nudge

Nudge is a friendly task assistant that helps you keep track of todos, deadlines, and
events. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. Run `./gradlew run` in the terminal to open Nudge's graphical interface. Enter commands in the text field and press `Enter` or click `Send`. You can add tasks, display them using `list`, and exit using `bye`.

The original console interface remains available for automated testing. To run it directly from IntelliJ, locate `src/main/java/nudge/Nudge.java`, right-click it, and run the `nudge.Nudge` main class. Its interaction looks like this:
   ```
   ____________________________________________________________
    _   _           _
   | \ | |_   _  __| | __ _  ___
   |  \| | | | |/ _` |/ _` |/ _ \
   | |\  | |_| | (_| | (_| |  __/
   |_| \_|\__,_|\__,_|\__, |\___|
                      |___/
       > Hi, I'm Nudge. Ready when you are—what should we keep on your radar?
   ____________________________________________________________
   todo read book
   ____________________________________________________________
       > On your radar—I've added:
         [T][ ] read book
       > You now have 1 task on your radar.
   ____________________________________________________________
   list
   ____________________________________________________________
       > Here's what's on your radar:
         1.[T][ ] read book
   ____________________________________________________________
   bye
   ____________________________________________________________
       > Okay, I'll leave you to it. I'll be here if you need another nudge!
   ____________________________________________________________
   ```

## Level-10 GUI

The graphical interface uses FXML for its responsive layout and CSS for its appearance.
Nudge's command and task-management logic remains separate from the JavaFX view so the
same behavior can still be tested through the console interface.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Acknowledgements

- OpenAI Codex was used to assist with the implementation, testing, debugging, and
  documentation of this project. All suggestions were reviewed, adapted, and tested
  by the author.
