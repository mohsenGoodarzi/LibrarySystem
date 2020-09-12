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
 * FXML controller for EditBook. Handles editing a book and saving the changes
 * made.
 *
 * @author Martin Trifinov (965075)
 * @author Scott Ankin (951874)
 */
public class EditBook extends AnchorPane {

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
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private ChoiceBox genre;
    @FXML
    private TextField ISBN;
    @FXML
    private ChoiceBox language;
    @FXML
    private TableView<Resource> bookTable;

    private final int COLUMN_ONE = 1;
    private final int COLUMN_TWO = 2;
    private final int COLUMN_THREE = 3;
    private final int COLUMN_FOUR = 4;

    /**
     * Constructs the edit book scene
     *
     * @param master passes through an instance of TaweLib
     */
    public EditBook(TaweLib master) {
        this.master = master;
        loadFXML();
        fillGenreComboBox();
        fillLanguageComboBox();
        fillTable();

    }

    /**
     * Loads the edit book FXML file
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("EditBook.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the genre choice box with genres
     */
    @FXML
    public void fillGenreComboBox() {
        try {

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String query = "select name from Genre;";
                ResultSet rs = master.getDatabaseManager()
                        .getDatabaseCommand().executeQuery(query);
                while (rs.next()) {
                    genre.getItems().add(rs.getString("name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBook.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fills the language choice box with languages
     */
    @FXML
    public void fillLanguageComboBox() {
        try {

            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

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
     * Fills the table with all the books which can be edited
     */
    @FXML
    private void fillTable() {
        String sql = "select * from Resource inner "
                + "join Book on Resource.resourceId = Book.bookId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        bookTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        resourceID.setPrefWidth(50.0);
        resourceName.setPrefWidth(470.0);
        year.setPrefWidth(100.0);

        bookTable.getColumns().addAll(resourceID, resourceName, year);

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

        bookTable.setItems(contents);
    }

    /**
     * Returns to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Selects the book from the table to be edited
     */
    @FXML
    private void clickedColumn(MouseEvent e) {
        try {
            //Gets the resourceID
            Resource resource = bookTable.getSelectionModel().getSelectedItem();
            int bookID = resource.getResourceId();
            uniqueID.setText(Integer.toString(bookID));

            //Fills the title text box with the title
            String selectTitle = "select title from Resource "
                    + "where resourceId = " + bookID + "";
            ResultSet titleRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectTitle);
            title.setText(titleRS.getString(1));

            //Fills the year text box with the year
            String selectYear = "select releasedYear from Resource "
                    + "where resourceId = " + bookID + "";
            ResultSet yearRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectYear);
            year.setText(yearRS.getString(1));

            //Fills the image location text box with the image location
            String selectImage = "select ImageLocation from Resource "
                    + "where resourceId = " + bookID + "";
            ResultSet imageRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectImage);
            image.setText(imageRS.getString(1));

            //Fills the text box with the author
            String selectAuthor = "select author from Book where bookId = " + bookID + "";
            ResultSet authorRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectAuthor);
            author.setText(authorRS.getString(1));

            //Fills the publisher text box with the publisher
            String selectPublisher = "select publisher from Book where bookId = " + bookID + "";
            ResultSet publisherRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectPublisher);
            publisher.setText(publisherRS.getString(1));

            //Fills the language choice box with the language
            String languageQuery = "Select languageID from Book where bookId = '" + bookID + "'";
            ResultSet languageIDrs = null;
            languageIDrs = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(languageQuery);
            int languageID = languageIDrs.getInt(1);
            String selectLanguage = "select name from Language where languageId = " + languageID + "";
            ResultSet languageRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectLanguage);
            language.setValue(languageRS.getString(1));

            //Gills the genre choice box with the genre
            String genreQuery = "Select genreID from Book where bookId = '" + bookID + "'";
            ResultSet genreIDrs = null;
            genreIDrs = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(genreQuery);
            int genreID = genreIDrs.getInt(1);
            String selectGenre = "select name from Genre where genreId = " + genreID + "";
            ResultSet genreRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectGenre);
            genre.setValue(genreRS.getString(1));

            //Fills the ISBN text box with the ISBN number
            String selectISBN = "select isbn from Book where bookId = " + bookID + "";
            ResultSet isbnRS = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(selectISBN);
            ISBN.setText(isbnRS.getString(1));

        } catch (SQLException ex) {
            Logger.getLogger(EditBook.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Saves the edits of the book to the database
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

            //Selecting the genreID
            String genreQuery = "Select genreID from Genre where name = '"
                    + genre.getValue() + "'";
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

            //Selecting the languageID
            String languageQuery = "Select languageID from Language "
                    + "where name = '" + language.getValue() + "'";
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

            //Inserting the book into the database
            String updateBook = "update Book set author = '"
                    + author.getText() + "', publisher = '"
                    + publisher.getText() + "', genreId = "
                    + genreID + ", ISBN = " + ISBN.getText()
                    + ", languageID = " + languageID
                    + " where bookID = " + uniqueID.getText() + ";";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(updateBook);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();
            Reload();
        }
    }

    /**
     * Removes the selected book from the database
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

            //Deleting the orphans from the book table
            String deleteBook = "delete from Book"
                    + " where bookId = " + uniqueID.getText() + ";";
            master.getDatabaseManager().getDatabaseCommand()
                    .setCommandString(deleteBook);
            master.getDatabaseManager().getDatabaseCommand()
                    .executeCommand();

            uniqueID.setText("");
            title.setText("");
            year.setText("");
            image.setText("");
            author.setText("");
            publisher.setText("");
            genre.setValue("");
            ISBN.setText("");
            language.setValue("");

            Reload();
        }
    }

    /**
     * Refreshes the table view with the new resources
     */
    private void Reload() {
        String sql = "select * from Resource inner join Book on"
                + " Resource.resourceId = Book.bookId;";
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sql);

        Resource currentResource;

        ObservableList<Resource> newContents
                = FXCollections.observableArrayList();

        bookTable.setEditable(true);

        TableColumn resourceID = new TableColumn("ID");
        TableColumn resourceName = new TableColumn("Title");
        TableColumn year = new TableColumn("Year");

        bookTable.getColumns().addAll(resourceID, resourceName, year);

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

        bookTable.setItems(newContents);
    }

}
