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
 * FXML Controller for AddBook. Handles The creation of books.
 *
 * @author Martin Trifinov (965075)
 * @author Scott Ankin (951874)
 */
public class AddBook extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TextField uniqueID;
    @FXML
    private TextField title;
    @FXML
    private TextField year;
    @FXML
    private TextField image;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private ChoiceBox<String> genre;
    @FXML
    private TextField ISBN;
    @FXML
    private ChoiceBox<String> language;
    @FXML
    private TextField copies;

    /**
     * Constructs the add book scene
     *
     * @param master passes through an instance of TaweLibs
     */
    public AddBook(TaweLib master) {
        this.master = master;
        loadFXML();
        fillLanguageComboBox();
        fillGenreComboBox();
    }

    /**
     * Loads the FXML file for add book
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
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
     * Fills the genre choice box with all the genres
     */
    @FXML
    public void fillGenreComboBox() {
        try {

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String query = "select name from Genre;";
                ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery(query);
                while (rs.next()) {
                    genre.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName()).log(Level.SEVERE, null, ex);
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
     * Saves the book to the database
     */
    @FXML
    private void Save(ActionEvent e) {
        for (int i = 0; i < Integer.parseInt(copies.getText()); i++) {

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String addResource = "insert into Resource "
                        + "(title,releasedYear,ImageLocation)"
                        + "VALUES(" + "'" + title.getText()
                        + "'" + "," + year.getText() + ","
                        + "'" + image.getText() + "');";
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
                    Logger.getLogger(AddBook.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                System.out.println(index);

                //Selecting the languageID
                String languageQuery = "Select languageID "
                        + "from Language where name = '"
                        + language.getValue() + "'";
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
                System.out.println(languageID);

                //Selecting the genreID
                String genreQuery = "Select genreID from Genre "
                        + "where name = '" + genre.getValue() + "'";
                ResultSet genreIDrs = null;

                genreIDrs = master.getDatabaseManager()
                        .getDatabaseCommand().executeQuery(genreQuery);
                int genreID = 0;
                try {
                    genreID = genreIDrs.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(AddBook.class.getName())
                            .log(Level.SEVERE, null, ex);
                }

                //Inserting the book into the database
                String addBook = "insert into Book (bookId,author,publisher,"
                        + "genreId,ISBN,languageID)values(" + index + ",'"
                        + author.getText() + "','" + publisher.getText() + "',"
                        + genreID + "," + ISBN.getText() + "," + languageID + ");";

                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(addBook);
                master.getDatabaseManager().getDatabaseCommand()
                        .executeCommand();

                Date date = new Date();
                SimpleDateFormat simpleDateFormat
                        = new SimpleDateFormat("dd/MM/yyyy");
                String dateResult = simpleDateFormat.format(date);
                String addLog = "insert into LogResource "
                        + "(userId,resourceId,logTypeId,logDate,rating)values("
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
