package DatabaseLayout;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public class BatchCommand {

    private int id;
    private CommandType commandType;
    private String Command = "";

    /**
     * Returns the ID
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID
     *
     * @param id the ID of the object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of the command
     *
     * @return the type of the command
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * Sets the type of the command
     *
     * @param commandType the type of the command
     */
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * Gets the command
     *
     * @return the command
     */
    public String getCommand() {
        return Command;
    }

    /**
     * Sets the command
     *
     * @param command the command which is set
     */
    public void setCommand(String command) {
        Command = command;
    }
}
