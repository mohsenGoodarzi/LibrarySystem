package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.SQLException;

import LoginManager.LoginManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for Login. Handles the user input and login functions.
 *
 * @author Thomas Poultney(963541)
 */
public class Login extends AnchorPane {

    private final String BAD_INPUT = "No account found. Try again!";
    private int loggedInUserId;

    @FXML
    private TextField userId;
    @FXML
    private Label badInput;
    private TaweLib master;

    /**
     * Creates the scene.
     *
     * @param master reference to the main application.
     */
    public Login(TaweLib master) {
        this.master = master;
        loadFXML();

    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("Login.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    @FXML
    private void LogIn(ActionEvent e) throws SQLException {
        LoginManager login = new LoginManager();

        String input = userId.getText();
        userId.setText("");

        login.logIn(input);
        if (login.isLoggedIn() == true) {
            if (login.isLibrarian() == true) {
                master.setUser(login.getUser());
                login.dispose();
                this.master.goToLibrarianDashboard();
            } else {
                master.setUser(login.getUser());
                login.dispose();
                this.master.goToUserDashboard();
            }
        } else {
            badInput.setText(BAD_INPUT);
        }
    }

    @FXML
    private void Enter(KeyEvent e) throws SQLException {
        if (e.getCode().equals(KeyCode.ENTER)) {
            LogIn(new ActionEvent());
        }
    }

}
