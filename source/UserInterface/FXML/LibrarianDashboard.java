package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for LibrarianDashboard. Main hub for librarians.
 *
 * @author Martin Trifonov(965075)
 */
public class LibrarianDashboard extends AnchorPane {

    private final String AVATAR_LOCATION 
            = "file:src/UserInterface/FXML/Avatar/";
    private final String MOTD = "Welcome, ";

    private final TaweLib master;

    @FXML
    private ImageView avatar;
    @FXML
    private Label motd;

    /**
     * Constructor for Librarian Dashboard. Loads FXML and the loads user Data.
     *
     * @param master reference to the main application.
     */
    public LibrarianDashboard(TaweLib master) {
        this.master = master;
        loadFXML();
        loadData();
    }

    /**
     * Loads FXML and sets the controller to this Class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("LibrarianDashboard.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Loads the user Data into the scene.
     */
    private void loadData() {
        Image image = new Image(AVATAR_LOCATION + master.getUser().
                getProfileImageLocation());
        avatar.setImage(image);
        motd.setText(MOTD + master.getUser().getFirstName());
    }

    /**
     * Handles button action. Changes scene to LendResource.
     */
    @FXML
    private void LendResource(ActionEvent e) {
        master.goToLendResource();
    }

    /**
     * Handles button action. Changes scene to ReturnResource.
     */
    @FXML
    private void ReturnResource(ActionEvent e) {
        master.goToReturnResource();
    }

    /**
     * Handles button action. Changes scene to PayFines.
     */
    @FXML
    private void PayFines(ActionEvent e) {
        master.goToPayFines();
    }

    /**
     * Handles button action. Changes scene to NewAccount.
     */
    @FXML
    private void NewAccount(ActionEvent e) {
        master.goToNewAccount();
    }

    /**
     * Handles button action. Changes scene to resourceManager.
     */
    @FXML
    private void resourceManager(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Handles button action. Changes scene to editAccount.
     */
    @FXML
    private void editAccount(ActionEvent e) {
        master.goToEditAccount();
    }

    /**
     * Handles button action. Changes scene to LogIn.
     */
    @FXML
    private void LogOut(ActionEvent e) {
        master.goToLogIn();
    }

}
