package DatabaseLayout;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public class DatabaseManager {

    private Database database;
    private DatabaseConnection databaseConnection;
    private DatabaseCommand databaseCommand;
    private static DatabaseCommand queryCommand;

    /**
     * The name of the database
     *
     * @param dataseName the name of the database
     */
    public DatabaseManager(String dataseName) {

        database = new SqlLiteDatabase(dataseName);
        String connectionString = database.getConnectionType()
                + database.getDatabaseName();

        SqlLiteConnection sqlLiteConnection
                = new SqlLiteConnection(connectionString);
        this.databaseConnection = sqlLiteConnection;

        databaseCommand
                = new SqlLiteCommand("", this.getDatabaseConnection());
        this.setDatabaseCommand(databaseCommand);
        queryCommand
                = new SqlLiteCommand("", this.getDatabaseConnection());
    }

    /**
     * Gets the database
     *
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Gets the database connection
     *
     * @return the connection to the database
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    /**
     * Gets the command of the database
     *
     * @return gets the database command
     */
    public DatabaseCommand getDatabaseCommand() {
        return databaseCommand;
    }

    /**
     * Sets the database command
     *
     * @param databaseCommand the database command
     */
    public void setDatabaseCommand(DatabaseCommand databaseCommand) {
        this.databaseCommand = databaseCommand;
    }

    /**
     * Gets the query
     *
     * @return The query to the database
     */
    public static DatabaseCommand getQueryCommand() {
        return queryCommand;
    }
}
