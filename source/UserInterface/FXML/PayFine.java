/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import Models.Logs.FineLog;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for PayFine. Displays all user who have outstanding fines and
 * lets the librarian select which one to pay.
 *
 * @author Martin Trifinov (965075)
 */
public class PayFine extends AnchorPane {

    private final String BAD_INPUT_MSG = "No record Found!";
    private final TaweLib master;
    private boolean selected = false;

    @FXML
    private Label badInput;
    @FXML
    private TextField input;
    @FXML
    private TableView<FineLog> table;
    private TableColumn userID;
    private TableColumn fine;

    private ObservableList<FineLog> data = FXCollections.observableArrayList();

    /**
     * Constructor for PayFine.
     *
     * @param master reference to main application.
     */
    public PayFine(TaweLib master) {
        this.master = master;
        loadFXML();
        createTables();
        loadData();
    }

    /**
     * Creates and sets up the columns for the table.
     */
    private void createTables() {
        userID = new TableColumn("User");
        fine = new TableColumn("Fine");
        userID.setPrefWidth(299.0);
        fine.setPrefWidth(299.0);
        table.getColumns().addAll(userID, fine);

        userID.setCellValueFactory(
                new PropertyValueFactory<FineLog, String>("userId"));
        fine.setCellValueFactory(
                new PropertyValueFactory<FineLog, String>("fineAmount"));
    }

    /**
     * Loads FXML file and sets controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("PayFine.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Loads data into the table.
     */
    private void loadData() {

        String sql = "select userID,sum(amount) "
                + "from FineLog group by userID having sum(amount)>0";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        ObservableList<FineLog> newData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                newData.add(new FineLog(rs.getInt("userID"), 
                        Double.parseDouble(rs.getString("sum(amount)"))));
            }
        } catch (SQLException ex) {
            System.out.print("Bad Input");
        }
        table.setItems(newData);
    }

    /**
     * Handles button action. Sends librarian back to dashboard.
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToLibrarianDashboard();
    }

    /**
     * Handles button action. Searches for specific userId and updates to show
     * only it.
     */
    @FXML
    private void Search(ActionEvent e) {
        badInput.setText("");

        int userId = 0;
        try {
            userId = Integer.parseInt(input.getText());
            input.setText("");
        } catch (NumberFormatException exp) {
            badInput.setText(BAD_INPUT_MSG);
            loadData();
            return;
        }

        String sql = "select userID,sum(amount) from FineLog where userID="
                + userId + " group by userID having sum(amount)>0";

        ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery(sql);

        FineLog finelog;
        try {
            if (rs.next()) {
                finelog = new FineLog(rs.getInt("userID"),
                        Double.valueOf(rs.getString("sum(amount)")));
            } else {
                badInput.setText(BAD_INPUT_MSG);
                loadData();
                return;
            }
        } catch (SQLException ex) {
            return;
        }

        ObservableList<FineLog> newData
                = FXCollections.observableArrayList(finelog);
        table.setItems(newData);
    }

    /**
     * Handles button action. Gets the selected fine and passes it to @master
     * Where a pop up window is made for the paying process.
     */
    @FXML
    private void Pay(ActionEvent e) {
        if (selected) {
            FineLog finelog = table.getSelectionModel().getSelectedItem();
            master.popUpPay(finelog);
        }
    }

    /**
     * Handles button action. Checks if a fine is selected.
     */
    @FXML
    private void Click(MouseEvent e) {
        selected = true;
    }
}
