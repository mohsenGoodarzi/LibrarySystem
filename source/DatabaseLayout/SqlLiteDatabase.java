package DatabaseLayout;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public class SqlLiteDatabase implements Database {

    private File file;
    private String databaseName;
    private String connectionType;

    /**
     * Creates a new SQLite Database
     *
     * @param databaseName the name of the SQLite databse
     */
    public SqlLiteDatabase(String databaseName) {

        connectionType = "jdbc:sqlite:";

        this.databaseName = databaseName;

        file = new File(databaseName);
        if (!file.exists()) {
            createDatabase();
        }
    }

    /**
     * Creates the database
     */
    public void createDatabase() {

        String connectionString = connectionType
                + new File(databaseName).getAbsolutePath();

        try (Connection connection
                = DriverManager.getConnection(connectionString)) {
            if (connection != null) {

            }

        } catch (SQLException e) {
            System.out.println("Database cannot be created.");
            System.out.println("more details:" + e.getMessage());
        }
    }

    /**
     * Removes the database
     */
    public void removeDatabase() {

        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Gets the database name
     *
     * @return the name of the database
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Gets the type of the connection
     *
     * @return the connection type
     */
    public String getConnectionType() {
        return connectionType;
    }
}
