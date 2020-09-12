package UserInterface.FXML;

import Models.Resources.Laptop;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Details of Laptop is the controller for DetailsOfLaptop.fxml. Window allows
 * the currently login user to select a resource from search resource to view in
 * more details, Information is displayed about resource and user can request it
 *
 * @author Thomas_Poultney(963541)
 */
public class DetailsOfLaptop extends AnchorPane {

    //Stores image path to resource images
    private final String IMAGE_LOCATION 
            = "file:src/UserInterface/FXML/ResourceImages/";
    private final int AVAILABLE_TYPE = 1;
    private final int REQUESTED_TYPE = 2;  //logTypes of resource
    private final int RESERVED_TYPE = 3;
    private final int BORROWED_TYPE = 4;

    @FXML
    private ImageView resourceImage;
    @FXML
    private TextField ID;
    @FXML
    private TextField manufactorer;
    @FXML
    private TextField model;
    @FXML
    private Button close;
    @FXML
    private Button request;
    private final TaweLib master;
    private Laptop laptopToDisplay;
    private final int LOAD_DURATION = 7;  //loadDuration of laptops.
    //status of selected resource
    private final String status;
    private SimpleDateFormat today = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor for Details Of Laptop.
     *
     * @param master reference to main application.
     * @param laptop reference to laptop you open more details about
     * @param status reference log type of book
     */
    public DetailsOfLaptop(TaweLib master, Laptop laptop, String status) {
        laptopToDisplay = laptop;
        this.master = master;
        this.status = status;
        loadFXML();
        loadData();
        try {
            fillTextFields();
        } catch (SQLException ex) {
            Logger.getLogger(DetailsOfLaptop.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Loads FXML file and sets controller to this class.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("DetailsOfLaptop.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);

        }

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
                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + laptopToDisplay.getLaptopId() + ","
                            + RESERVED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }

                if ("Requested".equals(status)) {

                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + laptopToDisplay.getLaptopId() + ","
                            + REQUESTED_TYPE + ",'"
                            + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();

                }

                if ("Reserved".equals(status)) {
                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + laptopToDisplay.getLaptopId() + ","
                            + REQUESTED_TYPE
                            + ",'" + today.format(new Date()) + "'," + 0 + ")";
                    System.out.print(sqlCommand);

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }

                if ("Borrowed".equals(status)) {
                    String sqlCommand = "Insert into logResource"
                            + "(userId,resourceId,logTypeId,logDate,rating)"
                            + " Values(" + master.getUser().getUserId()
                            + ", " + laptopToDisplay.getLaptopId() + ","
                            + REQUESTED_TYPE + ",'" + today.format(new Date())
                            + "'," + 0 + ")";

                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(sqlCommand);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();

                    sqlCommand = "Select min(LogResource.logId),"
                            + "logResource.logDate, LogResource.resourceId "
                            + "from LogResource INNER JOIN Resource ON "
                            + "LogResource.resourceId=Resource.resourceId "
                            + "group by Resource.title,LogResource.logId "
                            + "having LogResource.logTypeId = 4";

                    ResultSet rs = master.getDatabaseManager()
                            .getDatabaseCommand().executeQuery(sqlCommand);
                    try {
                        int logID = rs.getInt(1);
                    } catch (SQLException ex) {
                        Logger.getLogger(DetailsOfLaptop.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
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

                    rs = master.getDatabaseManager().getDatabaseCommand()
                            .executeQuery(sqlCommand);
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
     * loads resource image into image view
     */
    private void loadData() {
        Image image = new Image(IMAGE_LOCATION
                + laptopToDisplay.getImageLocation());

        resourceImage.setImage(image);
    }

    /**
     * returns user to search resource
     */
    @FXML
    private void Close(ActionEvent e) {
        Stage stage = (Stage) super.getScene().getWindow();
        stage.close();

    }

    /**
     * fills text field with relevant data from selectedResource
     */
    @FXML
    private void fillTextFields() throws SQLException {
        ID.setText(Integer.toString(laptopToDisplay.getLaptopId()));
        manufactorer.setText(laptopToDisplay.getManufacturer());
        String sqlCommand = "select name from OsModel where osId = "
                + laptopToDisplay.getModelId();
        System.out.println(sqlCommand);

        ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery(sqlCommand);
        model.setText(rs.getString(1));

    }

    /**
     * checks if given userID has any fines.
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
     * checks if given userID has overdue items
     */
    private boolean checkIfOverdue(int userID) throws SQLException {
        String sqlCommand = "select LogResource.resourceId ,"
                + " max(LogResource.logId) ,"
                + "DueResource.dueDate , "
                + "LogResource.logDate "
                + "from DueResource "
                + "inner join LogResource on DueResource.logId ="
                + "LogResource.logId GROUP by resourceId "
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
