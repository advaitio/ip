import java.util.Scanner;

/**
 * Starts the Nudge chatbot application.
 */
public class Nudge {
    private static final String INDENTATION = "    > ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String banner = " _   _           _            \n"
                + "| \\ | |_   _  __| | __ _  ___ \n"
                + "|  \\| | | | |/ _` |/ _` |/ _ \\\n"
                + "| |\\  | |_| | (_| | (_| |  __/\n"
                + "|_| \\_|\\__,_|\\__,_|\\__, |\\___|\n"
                + "                   |___/\n";

        String separator = "_".repeat(60);

        System.out.println(separator);
        System.out.print(banner);
        System.out.println(INDENTATION + "Hey! I'm Nudge. How can I help you today?");
        System.out.println(separator);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if ("bye".equalsIgnoreCase(command)) {
                break;
            }
            System.out.println(separator);
            System.out.println(INDENTATION + command);
            System.out.println(separator);
        }

        System.out.println(separator);
        System.out.println(INDENTATION
                + "Okay, I'll leave you to it. I'll be here if you need another nudge!");
        System.out.println(separator);
    }
}
