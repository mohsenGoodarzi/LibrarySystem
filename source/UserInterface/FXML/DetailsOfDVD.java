package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import Models.Resources.Dvd;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Details of DVD is the controller for DetailsOfDVD.fxml. Window allows the
 * currently login user to select a resource from search resource to view in
 * more details, Information is displayed about resource and user can request it
 *
 * @author Thomas_Poultney
 */
public class DetailsOfDVD extends AnchorPane {

    private final String IMAGE_LOCATION 
            = "file:src/UserInterface/FXML/ResourceImages/";
    private final int AVAILABLE_TYPE = 1;
    private final int REQUESTED_TYPE = 2;
    private final int RESERVED_TYPE = 3;
    private final int BORROWED_TYPE = 4;
    private final int LOAD_DURATION = 7;

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ImageView resourceImage;
    @FXML
    private TextField ID;
    @FXML
    private TextField name;
    @FXML
    private TextField director;
    @FXML
    private TextField runtime;
    @FXML
    private TextField language;
    @FXML
    private TextField subtitles;
    @FXML
    private Button close;
    @FXML
    private Button request;

    private Dvd dvdToDisplay;
    private TaweLib master;
    private String status;
    private SimpleDateFormat today = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor for DetailsOfDvd.
     *
     * @param master reference to main application.
     * @param dvd reference to dvd you open more details about
     * @param status reference log type of book
     */
    public DetailsOfDVD(TaweLib master, Dvd dvd, String status) {
        this.dvdToDisplay = dvd;
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
                    .getResource("DetailsOfDVD.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);

        }

    }

    /**
     * When request is pressed, creates new log for resource if user is not
     * fined or has over due copies.
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
                 * set to reserved.
                 */
                if ("Available".equals(status)) {
                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + dvdToDisplay.getDvdId()
                            + "," + RESERVED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }
                /**
                 * if selected resource is requested a new log is created and is
                 * set to requested(added to queue of Requests).
                 */
                if ("Requested".equals(status)) {

                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + dvdToDisplay.getDvdId() + ","
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
                 * set to requested(added to queue of Requests).
                 */
                if ("Reserved".equals(status)) {
                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + dvdToDisplay.getDvdId() + "," + REQUESTED_TYPE
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
                    String sqlCommand = "Insert into logResource(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + dvdToDisplay.getDvdId() + ","
                            + REQUESTED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();

                    sqlCommand = "Select min(LogResource.logId),logResource.logDate,"
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
                    //Incrementing the due date by loan duration day
                    c.add(Calendar.DAY_OF_MONTH, LOAD_DURATION);
                    String dueDate = sdf.format(c.getTime());

                    String insertCommand = "Insert into dueResource"
                            + "(logId,dueDate) Values ("
                            + logID + ",'" + dueDate + "')";
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
            ID.setText(Integer.toString(dvdToDisplay.getDvdId()));
            name.setText(dvdToDisplay.getTitle());
            director.setText(dvdToDisplay.getDirector());
            runtime.setText(Integer.toString(dvdToDisplay.getRuntime()));
            String sqlCommand = "Select name from Language "
                    + "where languageId = " + dvdToDisplay.getLanguageId();

            ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(sqlCommand);
            language.setText(rs.getString(1));
            sqlCommand = "Select name from Language where languageId = "
                    + dvdToDisplay.getSubtitleId();
            rs = master.getDatabaseManager().getDatabaseCommand()
                    .executeQuery(sqlCommand);
            subtitles.setText(rs.getString(1));
        } catch (SQLException ex) {
            Logger.getLogger(DetailsOfDVD.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Loads resource image into image view.
     */
    private void loadData() {

        Image image = new Image(IMAGE_LOCATION + dvdToDisplay.getImageLocation());
        resourceImage.setImage(image);
    }

    /**
     * Returns user to search resource.
     */
    @FXML
    private void Close(ActionEvent e) {
        master.goToSearchResource();

    }

    /**
     * Checks if given userID has any fines.
     */
    private boolean checkIfFined(int userID) throws SQLException {
        String sqlCommand = "select sum(amount) from FineLog "
                + "group by userId having userId = "
                + master.getUser().getUserId();
        ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery(sqlCommand);
        if (rs.next()) {
            return Double.parseDouble(rs.getString(1)) > 0.01;
        } else {
            return false;
        }

    }

    /**
     * Checks if given userID has overdue items.
     */
    private boolean checkIfOverdue(int userID) throws SQLException {
        String sqlCommand = "select LogResource.resourceId ,"
                + " max(LogResource.logId) ,"
                + "DueResource.dueDate , "
                + "LogResource.logDate "
                + "from DueResource "
                + "inner join LogResource on DueResource.logId "
                + "=LogResource.logId GROUP by resourceId "
                + "having LogResource.logTypeId=2;";

        ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery(sqlCommand);
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
