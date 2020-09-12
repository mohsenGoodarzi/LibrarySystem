package DatabaseLayout;

import java.sql.Connection;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public interface DatabaseConnection {

    /**
     * Gets the SQL connection
     *
     * @return the connection to the database
     */
    public java.sql.Connection getConnection();

    /**
     * Sets the connection to the database
     *
     * @param connection the connection to the database
     */
    public void setConnection(Connection connection);

    /**
     * Checks to see if the program is connected to the database
     *
     * @return whether the program is connected
     */
    public boolean isConnected();

    /**
     * Closes the connection to the database
     */
    public void closeConnection();

    /**
     * Gets the connection as a string
     *
     * @return the connection as a string
     */
    public String getConnectionString();

}
