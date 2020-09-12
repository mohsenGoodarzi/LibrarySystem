package UserInterface.FXML;

import Models.Resources.Resource;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML controller for Resource Manager. Has a table displaying all current
 * resources in the library.
 *
 * @author Martin Trifinov (965075)
 * @author Scott Ankin (951874)
 */
public class ResourceManager extends AnchorPane {

    private final TaweLib master;
    private ObservableList<Resource> contents
            = FXCollections.observableArrayList();

    @FXML
    private TableView<Resource> resourceTable;

    private final int COLUMN_ONE = 1;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 3;
    private final int COLUMN_FOUR = 4;

    /**
     * Constructs a new resource manager.
     *
     * @param master passes through an reference of TaweLib
     */
    public ResourceManager(TaweLib master) {
        this.master = master;
        loadFXML();
        fillTable();
    }

    /**
     * Loads the FXML file of resource manager
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ResourceManager.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the table with all resources
     */
    @FXML
    private void fillTable() {
        String sql = "select * from Resource "
                + "group by title order by resourceId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        resourceTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        resourceID.setPrefWidth(50.0);
        resourceName.setPrefWidth(448.4);
        year.setPrefWidth(100.0);

        resourceTable.getColumns().addAll(resourceID, resourceName, year);

        rs.toString();

        try {
            while (rs.next()) {
                currentResource = new Resource(rs.getInt(COLUMN_ONE),
                        rs.getString(COLUMN_TWO), rs.getInt(COLUMN_THREE),
                        rs.getString(COLUMN_FOUR));
                contents.add(currentResource);
            }
        } catch (Exception e) {
        }

        resourceID.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("resourceId"));
        resourceName.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("title"));
        year.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("releasedYear"));

        resourceTable.setItems(contents);
    }

    /**
     * Returns to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToLibrarianDashboard();
    }

    /*
    * Goes to add book
     */
    @FXML
    private void AddBook(ActionEvent e) {
        master.goToAddBook();
    }

    /*
    * Goes to add DVD
     */
    @FXML
    private void AddDVD(ActionEvent e) {
        master.goToAddDVD();
    }

    /*
    * Goes to add laptop
     */
    @FXML
    private void AddLaptop(ActionEvent e) {
        master.goToAddLaptop();
    }

    /*
    * Goes to edit book
     */
    @FXML
    private void EditBook(ActionEvent e) {
        master.goToEditBook();
    }

    /*
    * Goes to edit DVD
     */
    @FXML
    private void EditDVD(ActionEvent e) {
        master.goToEditDVD();
    }

    /*
    * Goes to edit laptop
     */
    @FXML
    private void EditLaptop(ActionEvent e) {
        master.goToEditLaptop();
    }

    /*
    * Goes to view overdue
     */
    @FXML
    private void ViewOverdue(ActionEvent e) {
        master.goToViewOverdue();
    }

    /*
    * Goes to add view history
     */
    @FXML
    private void ViewHistory(ActionEvent e) {
        master.goToViewHistory();
    }

}
