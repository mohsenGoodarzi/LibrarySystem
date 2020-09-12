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
 * FXML Controller for AddLaptop. Handles the creating of new laptops.
 *
 * @author Martin Trifinov (965075)
 * @author Scott Ankin (951874)
 *
 */
public class AddLaptop extends AnchorPane {

    private final TaweLib master;

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
    private ChoiceBox<String> installedOS;
    @FXML
    private TextField copies;

    /**
     * Constructs the add laptop scene
     *
     * @param master passes an instance of TaweLib
     */
    public AddLaptop(TaweLib master) {
        this.master = master;
        loadFXML();
        fillOSComboBox();
    }

    /**
     * Loads the FXML file
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("AddLaptop.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    /**
     * Fills the choice box of the the operating systems
     */
    @FXML
    public void fillOSComboBox() {
        try {

            if (master.getDatabaseManager().getDatabaseConnection()
                    .isConnected()) {

                String query = "select name from OsModel;";
                ResultSet rs = master.getDatabaseManager().
                        getDatabaseCommand().executeQuery(query);
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
     * Goes back to the previous scene
     */
    @FXML
    private void Back(ActionEvent e) {
        master.goToResourceManager();
    }

    /**
     * Saves the new laptop resource
     */
    @FXML
    private void Save(ActionEvent e) {
        for (int i = 0; i < Integer.parseInt(copies.getText()); i++) {
            if (master.getDatabaseManager().getDatabaseConnection().isConnected()) {

                String addResource = "insert into Resource (title,releasedYear,"
                        + "ImageLocation)VALUES(" + "'" + title.getText()
                        + "'" + "," + year.getText() + "," + "'"
                        + image.getText() + "');";
                master.getDatabaseManager().getDatabaseCommand().
                        setCommandString(addResource);
                master.getDatabaseManager().getDatabaseCommand().executeCommand();
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
                    Logger.getLogger(AddLaptop.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                System.out.println(index);

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
                System.out.println(osID);

                //Inserting the Laptop into the database
                String addLaptop = "INSERT INTO Laptop (laptopId,manufacturer,"
                        + "model,osId)VALUES(" + index + ",'"
                        + manufacturer.getText() + "','" + model.getText()
                        + "'," + osID + ");";
                System.out.println(addLaptop);
                master.getDatabaseManager().getDatabaseCommand()
                        .setCommandString(addLaptop);
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
