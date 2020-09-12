package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * EditAccount is the controller for EditAccount.fxml. Window allows the
 * currently login user to edit his personal details, change his avatar or draw
 * a custom one.
 *
 * @author Martin Trifinov (965075)
 */
public class EditAccount extends AnchorPane {

    private final String NO_DRAWING = "No user Drawing!";
    private final String BAD_USERNAME = "Username already exist!";

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
    private TextField adress;
    @FXML
    private TextField adress2;
    @FXML
    private TextField postCode;

    private boolean needsCustomAvatar = false;

    @FXML
    private CheckBox customAvatar;
    @FXML
    private BorderPane img1;
    @FXML
    private BorderPane img2;
    @FXML
    private BorderPane img3;
    @FXML
    private BorderPane img4;
    @FXML
    private BorderPane img5;
    @FXML
    private BorderPane img6;

    @FXML
    private Label error;
    private int defAvatar = 0;

    /**
     * Constructor for Edit Account window and loads the currently login user
     * data.
     *
     * @param master sets a reference to the main application.
     */
    public EditAccount(TaweLib master) {
        this.master = master;
        loadFXML();
        loadUser();
    }

    /**
     * Loads the FXML file and sets the controller to the current Pane.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("EditAccount.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Loads the data of the current user login inside the fields.
     */
    private void loadUser() {

        String sqlCommand = "select * from UserAccount where userId = "
                + master.getUser().getUserId();
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        try {
            while (rs.next()) {
                username.setText(rs.getString("userName"));
                firstName.setText(rs.getString("firstName"));
                lastName.setText(rs.getString("lastName"));
                mobile.setText(rs.getString("mobilePhoneNumber"));
                adress.setText(rs.getString("addressLineOne"));
                adress2.setText(rs.getString("addressLineTwo"));
                postCode.setText(rs.getString("postCode"));
                // String avatar = rs.getString("profileImageLocation");
            }
        } catch (SQLException ex) {

        }

    }

    /**
     * Checks if the specific username is available.
     *
     * @return if name is free.
     */
    private boolean isAvailable(String name) {

        if (master.getUser().getUserName().equals(name)) {
            return true;
        }
        String sqlCommand = "select * from UserAccount where userName = '"
                + name + "'";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        try {
            if (rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    /**
     * Handles the action of the Back button.
     */
    @FXML
    private void Back(ActionEvent event) {
        if (master.getUser().getClass().getName().contains("Librarian")) {
            master.goToLibrarianDashboard();
        } else {
            master.goToUserDashboard();
        }

    }

    /**
     * Handles the action of the Save button.
     */
    @FXML
    private void Save(ActionEvent event) {
        if (!isAvailable(username.getText())) {
            error.setText(BAD_USERNAME);
            if (needsCustomAvatar) {
                error.setText(error.getText() + "\n" + NO_DRAWING);
            }

        } else {
            if (needsCustomAvatar) {
                error.setText(NO_DRAWING);
            } else {
                String sql = "update UserAccount set userName='" 
                        + username.getText()
                        + "', firstName='" + firstName.getText()
                        + "', lastName='" + lastName.getText()
                        + "', mobilePhoneNumber=" 
                        + Long.parseLong(mobile.getText())
                        + ", addressLineOne='" + adress.getText()
                        + "', addressLineTwo='" + adress2.getText()
                        + "', postCode='" + postCode.getText() + "'";

                if (defAvatar > 0 && !customAvatar.isSelected()) {
                    sql += ", profileImageLocation='def" + defAvatar 
                            + ".png'";
                } else if (customAvatar.isSelected()) {
                    sql += ", profileImageLocation='"
                            + String.valueOf(master.getUser().getUserId()) 
                            + ".png'";

                }
                sql += " where userId = " + master.getUser().getUserId() 
                        + " ;";

                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(sql);
                master.getDatabaseManager().getDatabaseCommand()
                        .executeCommand();
                master.goToLibrarianDashboard();
            }
        }
    }

    /**
     * Handles the action of the DrawAvatar button.
     */
    @FXML
    private void DrawAvatar(ActionEvent event) {
        master.goToAvatarDrawer();
        needsCustomAvatar = false;
    }

    /**
     * Resets the selected Default Avatar.
     */
    private void resetClicks() {
        img1.setStyle("-fx-border-color: white");
        img2.setStyle("-fx-border-color: white");
        img3.setStyle("-fx-border-color: white");
        img4.setStyle("-fx-border-color: white");
        img5.setStyle("-fx-border-color: white");
        img6.setStyle("-fx-border-color: white");
    }

    /**
     * Checks if the user has created a custom avatar.
     */
    @FXML
    private void CustomClicked(MouseEvent event) {
        if (customAvatar.isSelected()) {
            defAvatar = 0;
            needsCustomAvatar = true;
            resetClicks();
        }
    }

    /**
     * Handles selection of def avatar 1.
     */
    @FXML
    private void Clicked1(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 1;
            resetClicks();
            img1.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Handles selection of def avatar 2.
     */
    @FXML
    private void Clicked2(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 2;
            resetClicks();
            img2.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Handles selection of def avatar 3.
     */
    @FXML
    private void Clicked3(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 3;
            resetClicks();
            img3.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Handles selection of def avatar 4.
     */
    @FXML
    private void Clicked4(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 4;
            resetClicks();
            img4.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Handles selection of def avatar 5.
     */
    @FXML
    private void Clicked5(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 5;
            resetClicks();
            img5.setStyle("-fx-border-color: red");
        }
    }

    /**
     * Handles selection of def avatar 6.
     */
    @FXML
    private void Clicked6(MouseEvent event) {
        if (!customAvatar.isSelected()) {
            defAvatar = 6;
            resetClicks();
            img6.setStyle("-fx-border-color: red");
        }
    }
}
