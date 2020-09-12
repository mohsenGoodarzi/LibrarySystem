package DatabaseLayout;

import java.sql.ResultSet;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public interface DatabaseCommand {

    /**
     * Gets the database connection
     *
     * @return The database connection
     */
    public DatabaseConnection getDatabaseConnection();

    /**
     * Executes the SQL command
     */
    public void executeCommand();

    /**
     * Executes multiple SQL commands
     *
     * @param sqlCommands the SQL commands
     */
    public void executeCommands(String[] sqlCommands);

    /**
     * Gets the SQL command as a string
     *
     * @return The SQL command as a string
     */
    public String getCommandString();

    /**
     * Sets the SQL command as a string
     *
     * @param commandString the SQL command as a string
     */
    public void setCommandString(String commandString);

    /**
     *
     * @param query The SQL query to be executed
     * @return the results of the executed query
     */
    public ResultSet executeQuery(String query);

}
