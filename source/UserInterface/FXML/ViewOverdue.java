/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import Models.ViewModels.TableElement;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * This class allows librarians to view a list of overdue books.
 *
 * @author Daniel Griffiths, Martin Trifinov
 * @version 1.1
 */
public class ViewOverdue extends AnchorPane {

    private final TaweLib master;
    private final int FIRST_COLUMN = 1;
    private final int SECOND_COLUMN = 2;
    private final int THIRD_COLUMN = 3;

    @FXML
    private TableView table;
    private ObservableList<TableElement> contents = FXCollections.observableArrayList();

    /**
     * Open the ViewOverdue scene.
     *
     * @param master A reference to the main application.
     */
    public ViewOverdue(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * Load the FXML file.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("ViewOverdue.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
        fillTable();
    }

    /**
     * Fills the table in the scene with overdue books and their corresponding
     * User ID, Date Borrowed and Due Date.
     */
    @FXML
    private void fillTable() {
        ResultSet titleRs;
        ResultSet userRs;
        ResultSet dateRs;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sqlCompatibleDate = dateFormat.format(date);
        ResultSet dueRs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery("select dueDate, dueResourceId, logId "
                        + "from dueResource where dueDate >= \' "
                        + sqlCompatibleDate + " \'");
        TableElement currentResource;

        table.setEditable(true);

        TableColumn titleCol = new TableColumn("Title");
        TableColumn userCol = new TableColumn("User ID");
        TableColumn dateCol = new TableColumn("Date Borrowed");
        TableColumn dueCol = new TableColumn("Due Date");
        titleCol.setPrefWidth(304.0);
        userCol.setPrefWidth(110.0);
        dateCol.setPrefWidth(92.0);
        dueCol.setPrefWidth(92.0);

        table.getColumns().addAll(titleCol, userCol, dateCol, dueCol);

        try {
            while (dueRs.next()) {
                titleRs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select title from Resource, "
                                + "logResource where logResource.logId = "
                                + dueRs.getString(THIRD_COLUMN));

                userRs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select userId, resourceId from "
                                + "logResource where logId = "
                                + dueRs.getString(THIRD_COLUMN));

                dateRs = master.getDatabaseManager().getDatabaseCommand()
                        .executeQuery("select logDate from logResource"
                                + " where logTypeId = 4 and resourceId = "
                                + userRs.getString(SECOND_COLUMN));

                currentResource
                        = new TableElement(titleRs.getString(FIRST_COLUMN),
                                userRs.getString(FIRST_COLUMN),
                                dateRs.getString(FIRST_COLUMN),
                                dueRs.getString(FIRST_COLUMN));
                contents.add(currentResource);
            }
        } catch (SQLException e) {
            System.out.println("admin help" + e.getMessage());
        }

        titleCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("title")
        );
        userCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("userId")
        );
        dateCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("date")
        );
        dueCol.setCellValueFactory(
                new PropertyValueFactory<TableElement, String>("due")
        );

        table.setItems(contents);
    }

    /**
     * Go back to the ResourceManager scene.
     */
    @FXML
    private void Back() {
        master.goToResourceManager();
    }
}
