/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import DatabaseLayout.DatabaseManager;
import Models.Resources.Book;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Details of book is the controller for DetailsOfBook.fxml. Window allows the
 * currently login user to select a resource from search resource to view in
 * more details, Information is displayed about resource and user can request it
 *
 * @author Thomas_Poultney
 */
public class DetailsOfBook extends AnchorPane {

    private final String IMAGE_LOCATION
            = "file:src/UserInterface/FXML/ResourceImages/";
    private final int AVAILABLE_TYPE = 1;
    private final int REQUESTED_TYPE = 2;
    private final int RESERVED_TYPE = 3;
    private final int BORROWED_TYPE = 4;

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ImageView resourceImage;
    @FXML
    private TextField bookID;
    @FXML
    private TextField name;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private TextField genre;
    @FXML
    private TextField language;
    @FXML
    private TextField ISBN;
    @FXML
    private Button close;
    @FXML
    private Button request;
    private final TaweLib master;
    private Book bookToDisplay;  //the selected book Object
    private final int LOAD_DURATION = 7; //load duration of books

    private final String status;
    private SimpleDateFormat today = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor for DetailsOfBook
     *
     * @param master reference to main application.
     * @param book reference to book you open more details about
     * @param status reference log type of book
     */
    public DetailsOfBook(TaweLib master, Book book, String status) {
        bookToDisplay = book;
        this.master = master;
        this.status = status;
        loadFXML();
        loadData();
        fillTextFields();

    }

    /**
     * Loads FXML file and sets controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("DetailsOfBook.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * returns user to search resource
     */
    @FXML
    private void Close(ActionEvent e) {
        master.goToSearchResource();

    }

    /**
     * when request is pressed, creates new log for resource if user is not
     * fined or has over due copies
     */
    @FXML
    private void request(ActionEvent request) {
        System.out.println(this.status);

        try {
            //checks if user is fined or has an overdue item and outputs error message
            if (checkIfFined(master.getUser().getUserId())
                    || (checkIfOverdue(master.getUser().getUserId()) == true)) {
                System.out.println("You are fined or overdue");
            } else {
                /**
                 * if selected resource is available a new log is created and is
                 * set to reserved
                 */
                if ("Available".equals(status)) {
                    String sqlCommand = "Insert into "
                            + "logResource(userId,resourceId,"
                            + "logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + bookToDisplay.getBookId() + ","
                            + RESERVED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }
                /**
                 * if selected resource is requested a new log is created and is
                 * set to requested(added to queue of Requests)
                 */
                if ("Requested".equals(status)) {

                    String sqlCommand = "Insert into logResource(userId,"
                            + "resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + bookToDisplay.getBookId() + ","
                            + REQUESTED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();

                }
                /**
                 * if selected resource is reserved a new log is created and is
                 * set to requested(added to queue of Requests)
                 */
                if ("Reserved".equals(status)) {
                    String sqlCommand = "Insert into logResource(userId,"
                            + "resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + bookToDisplay.getBookId() + "," + REQUESTED_TYPE
                            + ",'" + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }
                /**
                 * if selected resource is currently borrowed a new log is
                 * created and is set to requested(added to queue of Requests).
                 * and a due date is set for the first user that borrowed the
                 * book
                 */
                if ("Borrowed".equals(status)) {
                    String sqlCommand = "Insert into logResource(userId,"
                            + "resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + bookToDisplay.getBookId() + ","
                            + REQUESTED_TYPE + ",'" + today.format(new Date())
                            + "'," + 0 + ")";

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();

                    sqlCommand = "Select min(LogResource.logId),"
                            + "logResource.logDate,"
                            + " LogResource.resourceId from LogResource "
                            + "INNER JOIN Resource ON "
                            + "LogResource.resourceId=Resource.resourceId "
                            + "group by Resource.title,LogResource.logId "
                            + "having LogResource.logTypeId = 4";

                    ResultSet rs = master.getDatabaseManager()
                            .getDatabaseCommand().executeQuery(sqlCommand);
                    int logID = rs.getInt(1);

                    String logDate = rs.getString(2);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(logDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Incrementing the date by 1 day
                    c.add(Calendar.DAY_OF_MONTH, 7);
                    String dueDate = sdf.format(c.getTime());

                    String insertCommand = "Insert into dueResource(logId,dueDate) "
                            + "Values (" + logID + ",'" + dueDate + "')";
                    System.out.println(insertCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(insertCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailsOfBook.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * fills text field with relevant data from selectedResource
     */
    @FXML
    private void fillTextFields() {
        try {
            bookID.setText(Integer.toString(bookToDisplay.getBookId()));
            name.setText(bookToDisplay.getTitle());
            author.setText(bookToDisplay.getAuthor());
            publisher.setText(bookToDisplay.getPublisher());
            String sqlCommand = "select name from genre "
                    + "where genreID = " + bookToDisplay.getGenreId();
            ResultSet rs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(sqlCommand);
            genre.setText(rs.getString(1));
            ISBN.setText(Long.toString(bookToDisplay.getIsbn()));
            sqlCommand = "Select name from Language "
                    + "where languageId = " + bookToDisplay.getLanguageId();
            rs = master.getDatabaseManager()
                    .getDatabaseCommand().executeQuery(sqlCommand);
            language.setText(rs.getString(1));
        } catch (SQLException ex) {
            System.out.println("error");
        }
    }

    /**
     * loads image
     */
    private void loadData() {
        Image image = new Image(IMAGE_LOCATION
                + bookToDisplay.getImageLocation());

        resourceImage.setImage(image);
    }

    private static class resourceImage {

        public resourceImage() {
        }
    }

    /**
     * checks if given userID has any fines.
     */
    private boolean checkIfFined(int userID) throws SQLException {
        String sqlCommand = "select sum(amount) "
                + "from FineLog group by userId having "
                + "userId = " + master.getUser().getUserId();
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        if (rs.next()) {
            return Double.parseDouble(rs.getString(1)) > 0.01;
        } else {
            return false;
        }

    }

    /**
     * checks if given userID has overdue items
     */
    private boolean checkIfOverdue(int userID) throws SQLException {
        String sqlCommand = "select LogResource.resourceId ,"
                + " max(LogResource.logId) ,"
                + "DueResource.dueDate , "
                + "LogResource.logDate "
                + "from DueResource "
                + "inner join LogResource on "
                + "DueResource.logId =LogResource.logId GROUP by resourceId "
                + "having LogResource.logTypeId=2;";

        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        Date dueDate = new Date();
        if (rs.next()) {
            try {
                dueDate = new SimpleDateFormat("dd/MM/yyyy")
                        .parse(rs.getString(3));
            } catch (ParseException ex) {
                System.out.println("incorrect Date");
            }
            Date todayDate = new Date();

            Long result = todayDate.getTime() - dueDate.getTime();
            long days = TimeUnit.DAYS.convert(result, TimeUnit.MILLISECONDS);
            System.out.println(days);
            return days > 0;
        } else {
            return false;
        }

    }
}
