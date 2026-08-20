import java.util.Scanner;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final int MAX_TASKS = 100;
    private static final String INDENTATION = "    > ";
    private static final String SEPARATOR = "_".repeat(60);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];

        String banner = " _   _           _            \n"
                + "| \\ | |_   _  __| | __ _  ___ \n"
                + "|  \\| | | | |/ _` |/ _` |/ _ \\\n"
                + "| |\\  | |_| | (_| | (_| |  __/\n"
                + "|_| \\_|\\__,_|\\__,_|\\__, |\\___|\n"
                + "                   |___/\n";

        System.out.println(SEPARATOR);
        System.out.print(banner);
        System.out.println(INDENTATION + "Hey! I'm Nudge. How can I help you today?");
        System.out.println(SEPARATOR);

        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if ("bye".equalsIgnoreCase(command)) {
                break;
            }
            if ("list".equalsIgnoreCase(command)) {
                System.out.println(SEPARATOR);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(SEPARATOR);
                continue;
            }
            printNudgeMessage("added: " + command);
            tasks[taskCount] = command;
            taskCount++;
        }

        printNudgeMessage("Okay, I'll leave you to it. I'll be here if you need another nudge!");
    }

    /**
     * Prints a message from Nudge between separator lines.
     *
     * @param message message to display
     */
    private static void printNudgeMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(INDENTATION + message);
        System.out.println(SEPARATOR);
    }
}
