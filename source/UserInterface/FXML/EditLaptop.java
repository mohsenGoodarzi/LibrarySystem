/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import Models.Resources.Resource;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for EditLaptop. Handles editing and saving changes made to a
 * Laptop.
 *
 * @author Martin Trifinov (martin.trifonov98@gmail.com)
 * @author Scott Ankin (951874)
 */
public class EditLaptop extends AnchorPane {

    private final TaweLib master;
    private ObservableList<Resource> contents
            = FXCollections.observableArrayList();

    @FXML
    private TextField uniqueID;
    @FXML
    private TextField title;
    @FXML
    private TextField year;
    @FXML
    private TextField image;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField model;
    @FXML
    private ChoiceBox installedOS;
    @FXML
    private TableView<Resource> laptopTable;

    private final int COLUMN_ONE = 1;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 3;
    private final int COLUMN_FOUR = 4;

    /**
     * Constructs a new edit laptop scene
     *
     * @param master passes an instance of TaweLib
     */
    public EditLaptop(TaweLib master) {
        this.master = master;
        loadFXML();
        fillTable();
        fillOSComboBox();
    }

    /**
     * Loads the FXML file of edit laptop
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("EditLaptop.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the table with laptops from the database
     */
    @FXML
    private void fillTable() {
        String sql = "select * from Resource "
                + "inner join Laptop on Resource.resourceId "
                + "= Laptop.laptopId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        laptopTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        resourceID.setPrefWidth(50.0);
        resourceName.setPrefWidth(470.0);
        year.setPrefWidth(100.0);

        laptopTable.getColumns().addAll(resourceID, resourceName, year);

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

        laptopTable.setItems(contents);
    }

    /**
     * Fills the operating system choice box with all the operating systems
     */
    @FXML
    public void fillOSComboBox() {
        try {
            if (master.getDatabaseManager()
                    .getDatabaseConnection().isConnected()) {

                String query = "select name from OsModel;";
                ResultSet rs = master.getDatabaseManager()
                        .getDatabaseCommand().executeQuery(query);
                while (rs.next()) {
                    installedOS.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddLaptop.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Selects the resource from the table
     *
     * @param e the action of the user clicking the column
     */
    @FXML
    private void clickedColumn(MouseEvent e) {
        try {
            //Gets the resourceID
            Resource resource = laptopTable.getSelectionModel()
                    .getSelectedItem();
            int laptopID = resource.getResourceId();
            uniqueID.setText(Integer.toString(laptopID));

            //Fills the title text box with the title
            String selectTitle = "select title from Resource "
                    + "where resourceId = " + laptopID + "";
            ResultSet titleRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectTitle);
            title.setText(titleRS.getString(1));

            //Fills the year text box with the year
            String selectYear = "select releasedYear from Resource "
                    + "where resourceId = " + laptopID + "";
            ResultSet yearRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectYear);
            year.setText(yearRS.getString(1));

            //Fills the image location text box with the image location
            String selectImage = "select ImageLocation from Resource where resourceId = " + laptopID + "";
            ResultSet imageRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectImage);
            image.setText(imageRS.getString(1));

            //Fills the text box with the author
            String selectManufacturer = "select manufacturer from Laptop where laptopId = " + laptopID + "";
            ResultSet manufacturerRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectManufacturer);
            manufacturer.setText(manufacturerRS.getString(1));

            //Fills the publisher text box with the publisher
            String selectModel = "select model from Laptop where laptopId = " + laptopID + "";
            ResultSet modelRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectModel);
            model.setText(modelRS.getString(1));

            //Fills the language choice box with the language
            String osModelQuery = "Select osId from Laptop where laptopId = '" + laptopID + "'";
            ResultSet osModelIDrs = null;
            osModelIDrs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(osModelQuery);
            int osModelID = osModelIDrs.getInt(1);
            String selectOSModel = "select name from OsModel where osId = " + osModelID + "";
            ResultSet osModelRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectOSModel);
            installedOS.setValue(osModelRS.getString(1));

        } catch (SQLException ex) {
            Logger.getLogger(EditBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Goes back to the previous scene
     *
     * @param e the action of the user clicking the button
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Updates the laptop resource in the database
     *
     * @param e the action of the user clicking the save button
     */
    @FXML
    private void Save(ActionEvent e) {
        if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {
            //Updating the resources table
            String updateResource = "update Resource set title = '" 
                    + title.getText() + "'" + ", releasedYear = " 
                    + year.getText() + ",ImageLocation = '" 
                    + image.getText() + "' where resourceId = " 
                    + uniqueID.getText() + "";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(updateResource);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            //Selecting the osID of the Operating System
            String osQuery = "Select osId from OsModel where name = '" 
                    + installedOS.getValue() + "'";
            ResultSet osIDrs = null;

            osIDrs = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(osQuery);
            int osID = 0;
            try {
                osID = osIDrs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(AddLaptop.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            //Updating the DVD in the database
            String updateLaptop = "update Laptop set manufacturer = '" 
                    + manufacturer.getText() + "', model = '" + model.getText() 
                    + "', osID = " + osID + " where laptopId = " 
                    + uniqueID.getText() + ";";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(updateLaptop);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();
            Reload();
        }
    }

    /**
     * Removes the resource from the database
     *
     * @param e the action of the user clicking the button
     */
    @FXML
    private void Remove(ActionEvent e) {
        if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {
            //Deleting the resource from the resources table
            String deleteResource = "delete from Resource "
                    + "where resourceId = " + uniqueID.getText() + "";
            
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(deleteResource);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            //Deleting the orphans from the laptop table
            String deleteLaptop = "delete from Laptop"
                    + " where laptopId = " + uniqueID.getText() + ";";
            
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(deleteLaptop);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            uniqueID.setText("");
            title.setText("");
            year.setText("");
            image.setText("");
            manufacturer.setText("");
            model.setText("");
            installedOS.setValue("");

            Reload();
        }
    }

    /**
     * Reloads the table view with the updated data
     */
    private void Reload() {
        String sql = "select * from Resource inner join Laptop "
                + "on Resource.resourceId = Laptop.laptopId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        ObservableList<Resource> newContents 
                = FXCollections.observableArrayList();

        laptopTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        laptopTable.getColumns().addAll(resourceID, resourceName, year);

        rs.toString();

        try {
            while (rs.next()) {
                currentResource = new Resource(rs.getInt(COLUMN_ONE),
                        rs.getString(COLUMN_TWO), rs.getInt(COLUMN_THREE),
                        rs.getString(COLUMN_FOUR));
                newContents.add(currentResource);
            }
        } catch (SQLException e) {
        }

        laptopTable.setItems(newContents);
    }
}
