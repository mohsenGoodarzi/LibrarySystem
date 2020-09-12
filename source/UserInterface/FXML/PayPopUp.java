package UserInterface.FXML;

import Models.Logs.FineLog;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class for PayPopUp which handles paying a specific fine.
 * Librarian inputs the value payed by the user and saves it.
 *
 * @author Martin Trifinov (965075)
 */
public class PayPopUp extends AnchorPane {

    private final String OVERPAY = "User cannot pay more than he is fined!";
    private final double MINIMAL_CHARGE = 0.01;
    private final String UNDERPAY = "User cannot pay less than " 
            + MINIMAL_CHARGE + "!";

    @FXML
    private TextField user;
    @FXML
    private TextField fines;
    @FXML
    private TextField toPay;
    @FXML
    private Label error;

    private final TaweLib master;
    private FineLog finelog;

    /**
     * Controller class for PayPopUp which handles paying a specific fine.
     * Librarian inputs the value payed by the user and saves it.
     *
     * @param master reference to the main application,
     * @param finelog reference to the current log.
     */
    public PayPopUp(TaweLib master, FineLog finelog) {
        this.master = master;
        this.finelog = finelog;
        loadFXML();
        loadData();
    }

    /**
     * Loads data into the text fields.
     */
    private void loadData() {
        String sql = "select userName from UserAccount where userID=" 
                + finelog.getUserId();
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        try {
            user.setText(rs.getString(1));
        } catch (SQLException ex) {
            Logger.getLogger(PayPopUp.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        fines.setText(Double.toString(finelog.getFineAmount()));

    }

    /**
     * Loads FXML and sets the controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("PayPopUp.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Handles button action. Cancels the payment and returns.
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) super.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles button action. Process the payment. If the input is too large or
     * too small ask for new input.
     */
    @FXML
    private void pay(ActionEvent event) {
        double value = Double.valueOf(toPay.getText()) * -1;
        if (checkInput(value)) {

            String sql = "insert into FineLog (userId,amount) values ("
                    + finelog.getUserId() + "," + value + ")";

            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(sql);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();
            cancel(new ActionEvent());
            master.goToPayFines();
        }
    }

    /**
     * Checks if its a valid payment value.
     */
    private boolean checkInput(double value) {
        value *= -1;
        System.out.println(value);
        System.out.println(Double.parseDouble(fines.getText()));

        if (value > Double.parseDouble(fines.getText())) {
            error.setText(OVERPAY);
            return false;
        } else {
            if (value < MINIMAL_CHARGE) {
                error.setText(UNDERPAY);
                return false;
            } else {
                return true;

            }
        }
    }
}
