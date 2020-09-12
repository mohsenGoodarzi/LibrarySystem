package UserInterface.FXML;

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
 * This class allows a user to view their requested books.
 *
 * @author Daniel Griffiths, Martin Trifinov
 * @version 1.1
 */
public class ViewRequested extends AnchorPane {

    private final TaweLib master;
    private final int FIRST_COLUMN = 1;
    private final int SECOND_COLUMN = 2;

    @FXML
    private TableView<TableElement> table;
    private ObservableList<TableElement> contents 
            = FXCollections.observableArrayList();

    /**
     * Open the ViewRequested scene.
     *
     * @param master A reference to the main application.
     */
    public ViewRequested(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * Load the FXML file.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ViewRequested.fxml"));
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
     * Fills the table in the scene with the Requested books of a user and the
     * corresponding Date Requested.
     */
    @FXML
    private void fillTable() {
        ResultSet titleRS;
//        ResultSet requestedRS = master.getDatabaseManager()
//                .getDatabaseCommand().executeQuery("select logDate, "
//                        + "resourceId from LogResource where "
//                        + "logTypeId = 2 and userId = "
//                        + master.getUser().getUserId());

        ResultSet requestedRS = master.getDatabaseManager().getDatabaseCommand().
                executeQuery("select logDate , resourceId  ,"
                        + " max(LogResource.logID) "
                        + "from LogResource "
                        + "group by LogResource.resourceId,userId "
                        + "having  logTypeId=2 and userId="
                        + master.getUser().getUserId());

        TableElement currentResource;

        table.setEditable(true);

        TableColumn<TableElement, String> titleCol
                = new TableColumn<TableElement, String>("Title");
        TableColumn<TableElement, String> reqCol
                = new TableColumn<TableElement, String>("Date Requested");
        titleCol.setPrefWidth(481.0);
        reqCol.setPrefWidth(117.0);

        table.getColumns().addAll(titleCol, reqCol);

        try {
            while (requestedRS.next()) {
                titleRS = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select title from Resource "
                                + "where resourceId = "
                                + requestedRS.getInt(SECOND_COLUMN));
                currentResource
                        = new TableElement(titleRS.getString(FIRST_COLUMN),
                                requestedRS.getString(FIRST_COLUMN));
                contents.add(currentResource);
            }
        } catch (SQLException e) {
        }

        titleCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("title")
        );
        reqCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("requested")
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
