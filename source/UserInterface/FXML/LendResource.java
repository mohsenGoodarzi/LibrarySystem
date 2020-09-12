/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Models.ViewModels.ViewLendResource;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller for LendResource. Scene handles the lending process .
 *
 * @author Martin Trifinov (965075)
 * @author Mohsen Goodarzi
 */
public class LendResource extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TableView<ViewLendResource> viewLendResourceTable;

    private ObservableList<ViewLendResource> contents
            = FXCollections.observableArrayList();

    public LendResource(TaweLib master) {
        this.master = master;
        loadFXML();
        initialTable();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("LendResource.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }

    @FXML
    private void backBtnClicked(ActionEvent e) {
        master.goToLibrarianDashboard();
    }

    @FXML
    private void lendBtnClicked(ActionEvent e) {
        //master.goToLibrarianDashboard();
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void initialTable() {

        String sql = "select MAX(LogResource.logId), Resource.resourceId,"
                + "Resource.title,LogResource.logDate,LogResource.userId,"
                + "UserAccount.userName  from Resource "
                + "INNER join LogResource on Resource.resourceId="
                + "LogResource.resourceId INNER join UserAccount on "
                + "LogResource.userId=UserAccount.userId "
                + "GROUP by Resource.resourceId HAVING LogResource.logTypeId=3 "
                + "and LogResource.logId=MAX(LogResource.logId); ";

        ResultSet rs = master.getDatabaseManager().getDatabaseCommand()
                .executeQuery(sql);

        viewLendResourceTable.setEditable(true);
        ViewLendResource currentResource;
        TableColumn<ViewLendResource, String> resourceId
                = new TableColumn<ViewLendResource, String>("Resource ID");
        TableColumn<ViewLendResource, String> resourceName
                = new TableColumn<ViewLendResource, String>("Title");
        TableColumn<ViewLendResource, String> logDate
                = new TableColumn<ViewLendResource, String>("Date");
        TableColumn<ViewLendResource, String> userId
                = new TableColumn<ViewLendResource, String>("User ID");
        TableColumn<ViewLendResource, String> userName
                = new TableColumn<ViewLendResource, String>("User Name");

        resourceId.setMinWidth(50.0);

        resourceName.setMinWidth(150.0);

        logDate.setMinWidth(50);

        userId.setMinWidth(50.0);

        userName.setMinWidth(150);
        Date date;
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(currentDate.clone());
        viewLendResourceTable.getColumns().addAll(resourceId, resourceName,
                logDate, userId, userName);

        try {
            while (rs.next()) {
                date = new Date();
                try {

                    date = new SimpleDateFormat("dd/MM/yyyy")
                            .parse(rs.getString(4));

                } catch (ParseException e) {
                    System.out.println("the log date cannot be returnned."
                            + e.getMessage());
                }
                currentResource = new ViewLendResource(rs.getInt(2),
                        rs.getString(3), date, rs.getInt(5), rs.getString(6));
                contents.add(currentResource);
            }
        } catch (SQLException e) {
            System.out.println("errror");
        }

        resourceId.setCellValueFactory(
                new PropertyValueFactory<ViewLendResource, String>("resourceId"));
        resourceName.setCellValueFactory(
                new PropertyValueFactory<ViewLendResource, String>("resourceName"));
        logDate.setCellValueFactory(
                new PropertyValueFactory<ViewLendResource, String>("logDate"));
        userId.setCellValueFactory(
                new PropertyValueFactory<ViewLendResource, String>("userId"));
        userName.setCellValueFactory(
                new PropertyValueFactory<ViewLendResource, String>("userName"));

        viewLendResourceTable.setItems(contents);

        viewLendResourceTable.setRowFactory(table -> {
            TableRow<ViewLendResource> SelectedRow = new TableRow<>();
            SelectedRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!SelectedRow.isEmpty())) {
                    ViewLendResource row = SelectedRow.getItem();

                    String commandString = "insert into LogResource "
                            + "(userId,resourceId,logDate,logTypeId)values("
                            + row.getUserId() + "," + row.getResourceId()
                            + ",'" + result + "'," + 4
                            + ");";
                    System.out.println(commandString);
                    master.getDatabaseManager().getDatabaseCommand()
                            .setCommandString(commandString);
                    master.getDatabaseManager().getDatabaseCommand()
                            .executeCommand();
                }
            });
            return SelectedRow;
        });

    }
}
