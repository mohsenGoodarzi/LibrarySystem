package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;

import Models.ViewModels.TableElement;
import Models.Resources.Resource;
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
 * This class allows a user to view their reserved books.
 *
 * @author Daniel Grffiths, Martin Trifinov
 * @version 1.1
 */
public class ViewReserved extends AnchorPane {

    private final TaweLib master;
    private final int FIRST_COLUMN = 1;
    private final int SECOND_COLUMN = 2;

    @FXML
    private TableView table;
    private ObservableList<TableElement> contents
            = FXCollections.observableArrayList();

    /**
     * Open the ViewReserved scene.
     *
     * @param master A reference to the main application.
     */
    public ViewReserved(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * Load the FXML file.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ViewReserved.fxml"));
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
     * Fills the table in the scene with the Reserved books of a user and the
     * corresponding Date Reserved.
     */
    @FXML
    private void fillTable() {
        ResultSet itemRs;
        ResultSet reservedRs = master.getDatabaseManager().getDatabaseCommand().
                executeQuery("select logDate , resourceId  , "
                        + "max(LogResource.logID) "
                        + "from LogResource "
                        + "group by LogResource.resourceId,userId "
                        + "having  logTypeId=3 and userId="
                        + master.getUser().getUserId()
                );
        TableElement currentResource;

        table.setEditable(true);

        TableColumn titleCol = new TableColumn("Title");
        TableColumn reservedCol = new TableColumn("Date Reserved");
        titleCol.setPrefWidth(388.0);
        reservedCol.setPrefWidth(210.0);

        table.getColumns().addAll(titleCol, reservedCol);

        try {
            while (reservedRs.next()) {
                itemRs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select title from Resource "
                                + "where resourceId = "
                                + reservedRs.getString(SECOND_COLUMN));
                currentResource
                        = new TableElement(itemRs.getString(FIRST_COLUMN),
                                reservedRs.getString(FIRST_COLUMN));
                contents.add(currentResource);
            }
        } catch (SQLException e) {
        }

        titleCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("title")
        );
        reservedCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("requested")
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
