/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for NewAccount. Handles the creation of new Accounts and
 * saving them.
 *
 * @author Martin Trifinov(UI),
 * @author Thomas Poultney(functionality)
 *
 */
public class NewAccount extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField mobile;
    @FXML
    private TextField ADL1;
    @FXML
    private TextField ADL2;
    @FXML
    private TextField postcode;
    @FXML
    private CheckBox staff;

    /**
     * Constructor for NewAccount.
     *
     * @param master reference to the main application.
     */
    public NewAccount(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * Loads FXML file and sets the controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("NewAccount.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Handles button action. Returns librarian to the dashboard.Doesn't create
     * the user.
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToLibrarianDashboard();
    }

    /**
     * Handles button action. Creates new user with the specified input.
     * Username is set to firstName plus lastName.
     */
    @FXML
    private void Create(ActionEvent e) throws SQLException {
        String userNameString = firstName.getText() + "_" + lastName.getText();
        username.setText(userNameString);
        String sqlCommand
                = "Insert into userAccount(userName,firstName,lastName,"
                + "mobilePhoneNumber,addressLineOne,addressLineTwo"
                + ",postCode,profileImageLocation) VALUES("
                + "'" + username.getText() + "','" + firstName.getText()
                + "','"
                + lastName.getText() + "'," + Long.parseLong(mobile.getText())
                + ",'" + ADL1.getText()
                + "','" + ADL2.getText() + "','" + postcode.getText() + "','"
                + "def1.png" + "')";
        master.getDatabaseManager().getDatabaseCommand()
                .setCommandString(sqlCommand);
        master.getDatabaseManager().getDatabaseCommand()
                .executeCommand();

        // if staff member check box is selected, 
        //user is also added to librarian table using ID
        if (staff.isSelected()) {

            sqlCommand = "SELECT max(userId) from UserAccount;";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(sqlCommand);
            ResultSet rs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(sqlCommand);

            int userID = rs.getInt(1);

            Date date = new Date();
            date.getDate();
            sqlCommand = "Insert into LibrarianAccount(staffNumber,"
                    + "employmentDate) Values('" + userID + "','" + date + "')";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(sqlCommand);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();
        }
    }

}
