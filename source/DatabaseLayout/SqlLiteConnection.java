package DatabaseLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public class SqlLiteConnection implements DatabaseConnection {

    private Connection connection;
    private String connectionString;

    /**
     * Creates a connection to the database
     *
     * @param connectionString the connection to the database
     */
    public SqlLiteConnection(String connectionString) {
        this.connectionString = connectionString;
        setConnection(null);
        try {
            setConnection(DriverManager.getConnection(connectionString));
        } catch (SQLException e) {
            System.out.println("connection cannot be stablished");
        }
    }

    /**
     * Gets the connection
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the connection
     *
     * @param connection the connection to the database
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Checks to see if the database is connected
     *
     * @return whether the database is connected
     */
    @Override
    public boolean isConnected() {

        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the connection as a string
     *
     * @return the connection as a string
     */
    @Override
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * Closes the connection to the database
     */
    @Override
    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection cannot be closed");
            e.printStackTrace();
        }
    }
}
