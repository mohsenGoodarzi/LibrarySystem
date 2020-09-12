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
 * FXML Controller for EditDVD. Handles editing and saving changes to DVDs.
 *
 * @author Martin Trifinov (martin.trifonov98@gmail.com)
 * @author Scott Ankin (951874)
 */
public class EditDVD extends AnchorPane {

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
    private TextField director;
    @FXML
    private TextField runtime;
    @FXML
    private ChoiceBox language;
    @FXML
    private ChoiceBox subtitleLanguage;
    @FXML
    private TableView<Resource> dvdTable;

    private final int COLUMN_ONE = 1;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 3;
    private final int COLUMN_FOUR = 4;

    /**
     * Constructs the edit dvd scene
     *
     * @param master Passes through an instance of TaweLib
     */
    public EditDVD(TaweLib master) {
        this.master = master;
        loadFXML();
        fillLanguageComboBox();
        fillSubtitleLanguageComboBox();
        fillTable();

    }

    /**
     * Loads the Edit DVD FXML file
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("EditDVD.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the language choice box with all the languages
     */
    @FXML
    public void fillLanguageComboBox() {
        try {
            if (master.getDatabaseManager()
                    .getDatabaseConnection().isConnected()) {

                String query = "select name from Language;";
                ResultSet rs = master.getDatabaseManager()
                        .getDatabaseCommand().executeQuery(query);
                while (rs.next()) {
                    language.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Fills the subtitle choice box with all the languages
     */
    @FXML
    public void fillSubtitleLanguageComboBox() {
        try {
            if (master.getDatabaseManager()
                    .getDatabaseConnection().isConnected()) {

                String query = "select name from Language;";
                ResultSet rs = master.getDatabaseManager()
                        .getDatabaseCommand().executeQuery(query);
                while (rs.next()) {
                    subtitleLanguage.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddDVD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Fills the table with all DVD resources
     */
    @FXML
    private void fillTable() {
        String sql = "select * from Resource inner join Dvd "
                + "on Resource.resourceId = Dvd.dvdId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        dvdTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        resourceID.setPrefWidth(50.0);
        resourceName.setPrefWidth(470.0);
        year.setPrefWidth(100.0);

        dvdTable.getColumns().addAll(resourceID, resourceName, year);

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

        dvdTable.setItems(contents);
    }

    /**
     * Goes back to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Selects the item from the table
     */
    @FXML
    private void clickedColumn(MouseEvent e) {
        try {
            //Gets the resourceID
            Resource resource = dvdTable.getSelectionModel().getSelectedItem();
            int dvdID = resource.getResourceId();
            uniqueID.setText(Integer.toString(dvdID));

            //Fills the title text box with the title
            String selectTitle = "select title from Resource "
                    + "where resourceId = " + dvdID + "";
            ResultSet titleRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectTitle);
            title.setText(titleRS.getString(1));

            //Fills the year text box with the year
            String selectYear = "select releasedYear "
                    + "from Resource where resourceId = " + dvdID + "";
            ResultSet yearRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectYear);
            year.setText(yearRS.getString(1));

            //Fills the image location text box with the image location
            String selectImage = "select ImageLocation "
                    + "from Resource where resourceId = " + dvdID + "";
            ResultSet imageRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectImage);
            image.setText(imageRS.getString(1));

            //Fills the text box with the author
            String selectDirector = "select director "
                    + "from Dvd where dvdId = " + dvdID + "";
            ResultSet directorRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectDirector);
            director.setText(directorRS.getString(1));

            //Fills the publisher text box with the publisher
            String selectRuntime = "select runtime "
                    + "from Dvd where dvdId = " + dvdID + "";
            ResultSet runtimeRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectRuntime);
            runtime.setText(runtimeRS.getString(1));

            //Fills the language choice box with the language
            String languageQuery = "Select languageID "
                    + "from Dvd where dvdId = '" + dvdID + "'";
            ResultSet languageIDrs = null;
            languageIDrs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(languageQuery);
            int languageID = languageIDrs.getInt(1);
            String selectLanguage = "select name "
                    + "from Language where languageId = " + languageID + "";
            ResultSet languageRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectLanguage);
            language.setValue(languageRS.getString(1));

            //Gills the genre choice box with the genre
            String subtitleQuery = "Select subtitleID "
                    + "from Dvd where dvdId = '" + dvdID + "'";
            ResultSet subtitleIDrs = null;
            subtitleIDrs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(subtitleQuery);
            int subtitleID = subtitleIDrs.getInt(1);
            String selectSubtitle = "select name "
                    + "from Language where languageId = " + subtitleID + "";
            ResultSet subtitleRS = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(selectSubtitle);
            subtitleLanguage.setValue(subtitleRS.getString(1));

        } catch (SQLException ex) {
            Logger.getLogger(EditBook.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Updates the record in the database
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

            //Selecting the languageID
            String languageQuery = "Select languageID "
                    + "from Language where name = '" + language.getValue() + "'";
            ResultSet languageIDrs = null;

            languageIDrs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(languageQuery);
            int languageID = 0;
            try {
                languageID = languageIDrs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(AddBook.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            //Selecting the languageID of the subtitles
            String subtitleQuery = "Select languageID "
                    + "from Language where name = '"
                    + subtitleLanguage.getValue() + "'";
            ResultSet subtitleIDrs = null;

            subtitleIDrs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(subtitleQuery);
            int subtitleID = 0;
            try {
                subtitleID = subtitleIDrs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(AddDVD.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            //Updating the DVD in the database
            String updateDvd = "update Dvd set director = '"
                    + director.getText() + "', runtime = '"
                    + runtime.getText() + "', languageID = "
                    + languageID + ", subtitleID = " + subtitleID
                    + " where dvdId = " + uniqueID.getText() + ";";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(updateDvd);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            Reload();
        }
    }

    /**
     * Removes the record from the database
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

            //Deleting the orphans from the DVD table
            String deleteDvd = "delete from Dvd "
                    + "where dvdId = " + uniqueID.getText() + ";";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(deleteDvd);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            uniqueID.setText("");
            title.setText("");
            year.setText("");
            image.setText("");
            director.setText("");
            runtime.setText("");
            language.setValue("");
            subtitleLanguage.setValue("");

            Reload();
        }
    }

    /**
     * Reloads the table view with new resources
     */
    private void Reload() {
        String sql = "select * from Resource inner join Dvd on "
                + "Resource.resourceId = Dvd.dvdId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        ObservableList<Resource> newContents
                = FXCollections.observableArrayList();

        dvdTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        dvdTable.getColumns().addAll(resourceID, resourceName, year);

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

        dvdTable.setItems(newContents);
    }
}
