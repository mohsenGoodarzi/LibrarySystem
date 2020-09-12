package DatabaseLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 * @param <rs> the SQLite command
 */
public class SqlLiteCommand<rs> implements DatabaseCommand {

    private String CommandString;
    private DatabaseConnection databaseConnection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * Creates the SQLiteCommand
     *
     * @param CommandString the SQL command as a string
     * @param databaseConnection The connection to the database
     */
    public SqlLiteCommand(String CommandString,
            DatabaseConnection databaseConnection) {
        this.setCommandString(CommandString);
        this.databaseConnection = databaseConnection;
        statement = null;
        resultSet = null;
    }

    /**
     * Executes the SQL command
     */
    public void executeCommand() {
        if (databaseConnection.isConnected()) {
            try (Statement statement
                    = databaseConnection.getConnection().createStatement()) {
                // create a new table
                statement.execute(getCommandString());
            } catch (SQLException e) {
                System.out.println("The given command cannot be executed"
                        + e.getMessage());
            }
        }
    }

    /**
     * Executes multiple SQL commands
     *
     * @param sqlCommands the SQL commands
     */
    public void executeCommands(String[] sqlCommands) {
        if (databaseConnection.isConnected()) {
            try (Statement statement
                    = databaseConnection.getConnection().createStatement()) {

                for (int i = 0; i < sqlCommands.length; i++) {
                    statement.execute(sqlCommands[i]);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Executes the SQL query
     *
     * @param query the SQL query
     * @return The results of the query
     */
    public ResultSet executeQuery(String query) {

        if (databaseConnection.isConnected()) {
            try {
                this.statement
                        = databaseConnection.getConnection().createStatement();
                resultSet = statement.executeQuery(query);
                return resultSet;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Gets the string of the command
     *
     * @return the string of the command
     */
    public String getCommandString() {
        return CommandString;
    }

    /**
     * Sets the command string
     *
     * @param commandString the SQL command as a string
     */
    public void setCommandString(String commandString) {
        CommandString = commandString;
    }

    /**
     * Gets the database connection
     *
     * @return the database connection
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
}
