package UserInterface.FXML;

import DatabaseLayout.DatabaseCommand;
import DatabaseLayout.DatabaseManager;
import DatabaseLayout.SqlLiteCommand;
import Models.ViewModels.TableElement;
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
 * This class allows a user to view their borrowing history.
 *
 * @author Daniel Griffiths, Martin Trifinov
 * @version 1.2
 */
public class ViewBorrowed extends AnchorPane {

    private TaweLib master;
    private final int FIRST_COLUMN = 1;
    private final int SECOND_COLUMN = 2;
    private final int THIRD_COLUMN = 3;
    @FXML
    private TableView<TableElement> table;
    private ObservableList<TableElement> contents 
            = FXCollections.observableArrayList();

    /**
     * Open the ViewBorrowed scene.
     *
     * @param master A reference to the main application.
     */
    public ViewBorrowed(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * Load the FXML file.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ViewBorrowed.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
        fillTable();
    }

    /**
     * Fills the table in the scene with the Borrowed books of a user and the
     * corresponding Date Borrowed and Date Due.
     */
    @FXML
    private void fillTable() {
        ResultSet titleRS;
        ResultSet dueRS;

        TableElement currentResource;

        table.setEditable(true);

        TableColumn titleCol = new TableColumn("Title");
        TableColumn takenCol = new TableColumn("Date Borrowed");
        TableColumn dueCol = new TableColumn("Due Date");
        titleCol.setPrefWidth(388.0);
        takenCol.setPrefWidth(105.0);
        dueCol.setPrefWidth(105.0);
        String isDue;

        table.getColumns().addAll(titleCol, takenCol, dueCol);

        ResultSet borrowedRS = master.getDatabaseManager().getDatabaseCommand().
                executeQuery("select logDate , resourceId  ,"
                        + " logId, max(LogResource.logId) "
                        + "from LogResource group by resourceId,userId "
                        + "having logTypeId=4 and userId="
                        + master.getUser().getUserId());

        try {
            while (borrowedRS.next()) {
                String logDate = borrowedRS.getString(FIRST_COLUMN);
                String resourceId = borrowedRS.getString(SECOND_COLUMN);
                String logId = borrowedRS.getString(THIRD_COLUMN);

                titleRS = master.getDatabaseManager().getDatabaseCommand().
                        executeQuery("select title "
                                + "from Resource where resourceId = "
                                + resourceId);
                dueRS = master.getDatabaseManager().getDatabaseCommand().
                        executeQuery("select dueDate"
                                + " from dueResource where logId = "
                                + logId);

                if (dueRS.next()) {
                    isDue = dueRS.getString(FIRST_COLUMN);
                } else {
                    isDue = "Not Due";
                }
                currentResource 
                        = new TableElement(titleRS.getString(FIRST_COLUMN),
                        logDate, isDue);
                contents.add(currentResource);
            }
        } catch (SQLException e) {
            System.out.println("Error::" + e.getMessage());
        }

        titleCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("title")
        );
        takenCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("borrowed")
        );
        dueCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("due")
        );

        table.setItems(contents);
    }

    /**
     * Go back to the user dashboard scene.
     */
    @FXML
    private void Back() {
        master.goToUserDashboard();
    }
}
