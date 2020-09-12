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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for ViewHistory.
 *
 * @author Martin Trifinov (martin.trifonov98@gmail.com)
 * @author Scott Ankin (951874)
 *
 */
public class ViewHistory extends AnchorPane {

    private final TaweLib master;
    private ObservableList<Resource> contents = FXCollections.observableArrayList();

    private final int COLUMN_ONE = 1;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 3;
    private final int COLUMN_FOUR = 4;

    @FXML
    private TableView<Resource> resourceTable;

    /**
     * Creates a new view history scene
     *
     * @param master passes an instance of TaweLib
     */
    public ViewHistory(TaweLib master) {
        this.master = master;
        loadFXML();
        fillTable();
    }

    /**
     * Loads the view history fxml file
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ViewHistory.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the table with all the resources
     */
    @FXML
    private void fillTable() {
        String sql = "select * from Resource;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        resourceTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        resourceID.setPrefWidth(140.0);
        resourceName.setPrefWidth(600);
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
     * Selects the resource from the table to view it's history
     */
    @FXML
    private void columnClicked(MouseEvent e) {
        Resource resource = resourceTable.getSelectionModel().getSelectedItem();
        master.goToHistoryView(resource);
    }

    /**
     * Goes back to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }
}
