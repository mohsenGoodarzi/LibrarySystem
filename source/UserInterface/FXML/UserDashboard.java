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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for User Dashboard.
 *
 * @author Martin Trifinov (martin.trifonov98@gmail.com)
 */
public class UserDashboard extends AnchorPane {

    private final String AVATAR_LOCATION 
            = "file:src/UserInterface/FXML/Avatar/";
    private final String MOTD = "Welcome, ";

    private TaweLib master;

    @FXML
    private ImageView avatar;
    @FXML
    private Label motd;
    @FXML
    private Label fines;

    /**
     * Constructor for UserDasboard.
     *
     * @param master
     */
    public UserDashboard(TaweLib master) {
        this.master = master;
        loadFXML();
        loadData();
    }

    /**
     * Loads the FXML file and sets the controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("UserDashboard.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Loads data into the dashboard. Loads the profile picture, a welcoming
     * massage and the current fines.
     */
    private void loadData() {
        Image image = new Image(AVATAR_LOCATION + master.getUser()
                .getProfileImageLocation());
        avatar.setImage(image);
        motd.setText(MOTD + master.getUser().getFirstName());

        String sql = "select sum(amount) from FineLog where userId= "
                + master.getUser().getUserId() + " group by userId";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);
        try {
            if (rs.next()) {
                String fine = rs.getString(1);
                if (!fine.contains("0.0")) {
                    fines.setText(fines.getText() + "-" + rs.getString(1));
                } else {
                    fines.setText(fines.getText() + "0.00");
                }
            } else {
                fines.setText(fines.getText() + "0.00");
            }
        } catch (SQLException ex) {

        }
    }

    /**
     * Handles button action. Goes to Search resource.
     */
    @FXML
    private void Search(ActionEvent event) {
        master.goToSearchResource();
    }

    /**
     * Handles button action. Goes to View Requested.
     */
    @FXML
    private void ViewRequested(ActionEvent event) {
        master.goToViewRequested();
    }

    /**
     * Handles button action. Goes to ViewReserved.
     */
    @FXML
    private void ViewReserved(ActionEvent event) {
        master.goToViewReserved();
    }

    /**
     * Handles button action. Goes to ViewBorrowed.
     */
    @FXML
    private void ViewBorrowed(ActionEvent event) {
        master.goToViewBorrowed();
    }

    /**
     * Handles button action. Goes to EditAccount.
     */
    @FXML
    private void EditAccount(ActionEvent event) {
        master.goToEditAccount();
    }

    /**
     * Handles button action. Goes to ViewTransactions.
     */
    @FXML
    private void ViewTransactions(ActionEvent event) {
        master.goToViewTransactions();
    }

    /**
     * Handles button action. Logs out of the system.
     */
    @FXML
    private void LogOut(ActionEvent event) {
        master.goToLogIn();
    }
}
