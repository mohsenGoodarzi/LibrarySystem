package UserInterface.FXML;

import Models.Logs.FineLog;
import Models.ViewModels.TableElement;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for ViewTransactions. Handles the transaction history of the
 * user.
 *
 * @author Martin Trifinov (965075)
 */
public class ViewTransactions extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TableView<TableElement> table;

    ObservableList<TableElement> contents = FXCollections.observableArrayList();

    /**
     * Constructor for ViewTransactions.
     *
     * @param master
     */
    public ViewTransactions(TaweLib master) {
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
                    .getResource("ViewTransactions.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Loads Data into the scene. Loads all transactions of the currently logged
     * in user.
     */
    private void loadData() {
        String sql = "select amount from FineLog where userId = "
                + master.getUser().getUserId();
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);
        TableColumn fine = new TableColumn("Transactions");
        fine.setPrefWidth(598.0);
        String transactionType;

        table.setEditable(true);
        table.getColumns().addAll(fine);

        try {
            while (rs.next()) {
                if (rs.getDouble(1) < 0) {
                    contents.add(
                            new TableElement("Fined at" + (-1 * rs.getDouble(1))));
                } else {
                    contents.add(
                            new TableElement("Payed at" + rs.getDouble(1)));
                }
            }
        } catch (SQLException e) {

        }

        fine.setCellValueFactory(
                new PropertyValueFactory<FineLog, String>("transaction")
        );
        table.setItems(contents);
    }

    /**
     * Handles button action. Goes back to the dashboard.
     */
    @FXML
    private void Back() {
        master.goToUserDashboard();
    }

}
