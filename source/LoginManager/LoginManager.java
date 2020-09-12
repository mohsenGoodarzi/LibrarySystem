package LoginManager;

import DatabaseLayout.DatabaseManager;
import Models.Users.LibrarianAccount;
import Models.Users.UserAccount;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles Loging in the system.
 * @author Thomas Poultney(963541)
 */
public class LoginManager {

    private DatabaseManager databaseManager;
    private UserAccount userAccount;
    private boolean loggedIn;
    private boolean isLibrarian;

    /**
     * Constructs a new login manager
     */
    public LoginManager() {
        databaseManager = new DatabaseManager("Database.db");
        userAccount = null;
        loggedIn = false;
        isLibrarian = false;
    }

    /**
     * This checks to see if the user who has logged in exists
     *
     * @param userName The username of the user who has logged in
     * @throws SQLException
     */
    public void logIn(String userName) throws SQLException {

        if (idExist(userName) == 0) {
            System.out.println("user not found");
        } else if (idExist(userName) == 1) {
            this.isLibrarian = true;
            this.loggedIn = true;
        } else {
            this.isLibrarian = false;
            this.loggedIn = true;
        }
    }

    /**
     * Returns whether the user is logged in
     *
     * @return If the user is logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Returns whether the user is a librarian
     *
     * @return returns whether the user is a librarian
     */
    public boolean isLibrarian() {
        return isLibrarian;
    }

    /**
     * Logs the user out
     */
    public void logOut() {
        this.userAccount = null;
        this.loggedIn = false;
    }

    /**
     * Returns the user account
     *
     * @return the user account
     */
    public UserAccount getUser() {
        return this.userAccount;
    }

    /**
     * Sets the user as logged in
     *
     * @param user passes through an instance of UserAccount
     */
    public void setUserLoggedIn(UserAccount user) {
        this.userAccount = user;
    }

    /**
     * Checks to see if the user ID exists
     *
     * @param input the input from the text field of the username
     * @return whether the user ID exists or not
     * @throws SQLException
     */
    private int idExist(String input) throws SQLException {
        String sqlCommand = "select * from userAccount"
                + " inner join librarianAccount on staffnumber = userId"
                + " where username = '" + input + "'";
        ResultSet rs
                = databaseManager.getDatabaseCommand().executeQuery(sqlCommand);

        int typeOfUser = 0; //1 if librarian, 2 if normal user, 0 if not found
        if (rs.next() == false) {
            sqlCommand = "select * from userAccount "
                    + "where username = '" + input + "'";
            rs = databaseManager.getDatabaseCommand().executeQuery(sqlCommand);

            if (rs.next() == false) {
                return 0; //user is not found
            } else {
                UserAccount userLoggedIn = new UserAccount(rs.getInt(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getLong(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9));
                setUserLoggedIn(userLoggedIn);
                return 2; // user is a normal user
            }
        } else {
            UserAccount userLoggedIn = new LibrarianAccount(rs.getInt(1),
                    rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getLong(5), rs.getString(6), rs.getString(7),
                    rs.getString(8), rs.getString(9), rs.getDate(10));
            setUserLoggedIn(userLoggedIn);
            return 1; //user is a librarian
        }
    }

    /**
     * Closes the Database Connection.
     */
    public void dispose() {
        databaseManager.getDatabaseConnection().closeConnection();
        databaseManager = null;
    }
}
