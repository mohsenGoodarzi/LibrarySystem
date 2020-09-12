package DatabaseLayout;

/**
 *
 * @author Mohsen Goodarzi, Thomas Poultney
 */
public interface Database {

    /**
     * Gets the database name
     *
     * @return the name of the database
     */
    public String getDatabaseName();

    /**
     * Gets the type of connection
     *
     * @return the type of connection
     */
    public String getConnectionType();

    /**
     * Creates the database
     */
    public void createDatabase();

    /**
     * Removes the database
     */
    public void removeDatabase();
}
