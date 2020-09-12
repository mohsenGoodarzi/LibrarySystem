package UserInterface.FXML;

import Models.Logs.ViewLog;
import Models.Resources.Resource;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * Shows the borrow history of a specific copy.
 *
 * @author Scott Ankin
 */
public class HistoryView extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TableView<ViewLog> historyTable;

    private ObservableList<ViewLog> contents
            = FXCollections.observableArrayList();
    private Resource resource;

    private final int COLUMN_ONE = 4;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 5;

    /**
     * Creates a new history view scene
     *
     * @param master passes through an instance of TaweLab
     * @param resource passes through an instance of an object
     */
    public HistoryView(TaweLib master, Resource resource) {
        this.master = master;
        this.resource = resource;
        loadFXML();
        fillTable();

    }

    /**
     * Loads the FXML file of history view
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("HistoryView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the table with the history of the selected resource
     */
    @FXML
    private void fillTable() {
        String sql = "select LogResource.logId,LogResource.logDate,"
                + "LogResource.userId,UserAccount.userName,"
                + "LogType.logTypeName from LogResource \n"
                + "inner JOIN  UserAccount on "
                + "LogResource.userId=UserAccount.userId \n"
                + "INNER JOIN LogType on "
                + "LogResource.logTypeId=LogType.logTypeId  "
                + "where LogResource.resourceId="
                + resource.getResourceId() + "";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        ViewLog currentResource;

        historyTable.setEditable(true);

        TableColumn userName = new TableColumn("User Name");
        TableColumn logDate = new TableColumn("logDate");
        TableColumn logType = new TableColumn("Log Type");

        userName.setPrefWidth(500.0);
        logDate.setPrefWidth(152);
        logType.setPrefWidth(100.0);

        historyTable.getColumns().addAll(userName, logDate, logType);

        rs.toString();

        try {
            while (rs.next()) {
                currentResource = new ViewLog(rs.getString(COLUMN_ONE),
                        rs.getString(COLUMN_TWO), rs.getString(COLUMN_THREE));
                contents.add(currentResource);
            }
        } catch (SQLException e) {
        }

        userName.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("userName"));
        logDate.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("date"));
        logType.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("logType"));

        historyTable.setItems(contents);
    }

    /**
     * Goes back to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToViewHistory();
    }
}
