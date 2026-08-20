package nudge;

/**
 * Represents the command types recognized by Nudge.
 */
public enum CommandType {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    UNKNOWN("");

    private final String commandWord;

    CommandType(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Identifies the type of the specified command.
     *
     * @param command full user command.
     * @return matching command type, or {@link #UNKNOWN} if the command is not recognized.
     */
    public static CommandType from(String command) {
        if (BYE.commandWord.equalsIgnoreCase(command)) {
            return BYE;
        }
        if (LIST.commandWord.equalsIgnoreCase(command)) {
            return LIST;
        }

        for (CommandType commandType : values()) {
            if (commandType == BYE || commandType == LIST || commandType == UNKNOWN) {
                continue;
            }
            if (commandType.matches(command)) {
                return commandType;
            }
        }
        return UNKNOWN;
    }

    /**
     * Returns whether the specified input starts with this command's word.
     *
     * @param command full user command.
     * @return true if the input contains this command word, followed by optional arguments.
     */
    private boolean matches(String command) {
        return commandWord.equals(command) || command.startsWith(commandWord + " ");
    }
}
