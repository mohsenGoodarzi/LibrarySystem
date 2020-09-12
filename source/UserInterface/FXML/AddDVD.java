package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for AddDVD. Handles the creation of new DVDs.
 *
 * @author Martin Trifinov (965075)
 * @author Scott Ankin (951874)
 */
public class AddDVD extends AnchorPane {

    private final TaweLib master;

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
    private ChoiceBox<String> language;
    @FXML
    private ChoiceBox<String> subtitleLanguage;
    @FXML
    private TextField copies;

    /**
     * Constructs the add DVD scene
     *
     * @param master Passes through an instance of TaweLib
     */
    public AddDVD(TaweLib master) {
        this.master = master;
        loadFXML();
        fillLanguageComboBox();
        fillSubtitleLanguageComboBox();
    }

    /**
     * Loads the FXML file of add DVD
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddDVD.fxml"));
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

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String query = "select name from Language;";
                ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery(query);
                while (rs.next()) {
                    language.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Fills the subtitle choice box with all the languages
     */
    @FXML
    public void fillSubtitleLanguageComboBox() {
        try {

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String query = "select name from Language;";
                ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery(query);
                while (rs.next()) {
                    subtitleLanguage.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddDVD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Saves the DVD to the database
     */
    @FXML
    private void Save(ActionEvent e) {
        for (int i = 0; i < Integer.parseInt(copies.getText()); i++) {
            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                //Adds the new dvd to the resource table
                String addResource = "insert into Resource (title,releasedYear,"
                        + "ImageLocation)VALUES(" + "'" + title.getText() + "'"
                        + "," + year.getText() + "," + "'" + image.getText() + "');";
                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(addResource);
                master.getDatabaseManager().getDatabaseCommand()
                        .executeCommand();
                ResultSet result = null;

                //Getting the index of the resource
                result = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select max(resourceId) from resource;");
                int index = 0;
                try {
                    if (result.next()) {
                        index = result.getInt(1);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AddDVD.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                System.out.println(index);

                //Selecting the languageID of the language of the DVD
                String languageQuery = "Select languageID "
                        + "from Language where name = '"
                        + language.getValue() + "'";
                ResultSet languageIDrs = null;

                languageIDrs = master.getDatabaseManager()
                        .getDatabaseCommand()
                        .executeQuery(languageQuery);
                int languageID = 0;
                try {
                    languageID = languageIDrs.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(AddDVD.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                System.out.println(languageID);

                //Selecting the languageID of the subtitles
                String subtitleQuery = "Select languageID "
                        + "from Language where name = "
                        + "'" + subtitleLanguage.getValue() + "'";
                ResultSet subtitleIDrs = null;

                subtitleIDrs = master.getDatabaseManager()
                        .getDatabaseCommand()
                        .executeQuery(subtitleQuery);
                int subtitleID = 0;
                try {
                    subtitleID = subtitleIDrs.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(AddDVD.class.getName())
                            .log(Level.SEVERE, null, ex);
                }

                //Inserting the DVD into the database
                String addDvd = "insert into Dvd (dvdID,director ,"
                        + "runtime ,languageID " + ",subtitleID)values("
                        + index + ",'" + director.getText() + "',"
                        + runtime.getText() + "," + languageID + ","
                        + subtitleID + ");";

                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(addDvd);
                master.getDatabaseManager().getDatabaseCommand()
                        .executeCommand();

                Date date = new Date();
                SimpleDateFormat simpleDateFormat
                        = new SimpleDateFormat("dd/MM/yyyy");
                String dateResult = simpleDateFormat.format(date);
                String addLog = "insert into LogResource (userId,resourceId,"
                        + "logTypeId,logDate,rating)values("
                        + master.getUser().getUserId() + "," + index + ","
                        + 1 + ",'" + dateResult + "'," + 0 + ");";
                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(addLog);
                master.getDatabaseManager().getDatabaseCommand()
                        .executeCommand();
            }
        }
    }
}
