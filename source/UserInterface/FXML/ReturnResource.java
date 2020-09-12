/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FXML;

import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Models.Resources.ResourceType;
import Models.ViewModels.ViewReturnResource;
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
 * FXML Controller for ReturnResource. Scene handles the returning process .
 *
 * @author Martin Trifinov (965075)
 * @author Mohsen Goodarzi
 */
public class ReturnResource extends AnchorPane {

    private final TaweLib master;

    @FXML
    private TableView<ViewReturnResource> table;
    private ObservableList<ViewReturnResource> contents = FXCollections.observableArrayList();

    public ReturnResource(TaweLib master) {
        this.master = master;
        loadFXML();
        initialTable();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnResource.fxml"));
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
    private void returnBtnClicked(ActionEvent e) {
        // master.goToLibrarianDashboard();
    }

    @SuppressWarnings("unchecked")
    private void initialTable() {
        table.setEditable(false);
        TableColumn<ViewReturnResource, String> userId = new TableColumn<ViewReturnResource, String>("User ID");
        TableColumn<ViewReturnResource, String> userName = new TableColumn<ViewReturnResource, String>("User Name");
        TableColumn<ViewReturnResource, String> resourceId = new TableColumn<ViewReturnResource, String>("Resource ID");
        TableColumn<ViewReturnResource, String> resourceName = new TableColumn<ViewReturnResource, String>("Resource Name");
        TableColumn<ViewReturnResource, String> overDueDays = new TableColumn<ViewReturnResource, String>("Over Due days");
        TableColumn<ViewReturnResource, String> calculatedFine = new TableColumn<ViewReturnResource, String>("Fine");
        resourceId.setMinWidth(50.0);
        resourceName.setMinWidth(150.0);
        userId.setMinWidth(50.0);
        userName.setMinWidth(150);
        ViewReturnResource currentResource;
        table.getColumns().addAll(userId, userName, resourceId, resourceName, overDueDays, calculatedFine);

        String BorrowedResourcesSql = "select LogResource.userId,UserAccount.userName, Resource.resourceId,Resource.title,LogResource.logDate,MAX(LogResource.logId)  from Resource "
                + "INNER join LogResource on Resource.resourceId=LogResource.resourceId INNER join UserAccount on LogResource.userId=UserAccount.userId "
                + "GROUP by Resource.resourceId HAVING LogResource.logTypeId=4 and LogResource.logId=MAX(LogResource.logId); ";
        ResultSet BorrowedResources = master.getDatabaseManager().getDatabaseCommand().executeQuery(BorrowedResourcesSql);

        Date dueDate = null;

        try {
            while (BorrowedResources.next()) {

                String typeofResourceSql = "select Resource.resourceId,'DVD' as types from Resource "
                        + "INNER join Dvd on Resource.resourceId=Dvd.dvdId UNION "
                        + "select  Resource.resourceId,'BOOK' as types from Resource "
                        + "INNER join Book on Resource.resourceId=Book.bookId UNION "
                        + "select  Resource.resourceId,'LAPTOP' as types from Resource "
                        + "INNER join Laptop on Resource.resourceId=Laptop.laptopId "
                        + "where Resource.resourceId = " + BorrowedResources.getInt(3) + ";";

                ResultSet typeofResource = master.getDatabaseManager().getDatabaseCommand().executeQuery(typeofResourceSql);
                String duDateSql = "select DueResource.dueDate from DueResource "
                        + "INNER JOIN LogResource on DueResource.logId = LogResource.logId "
                        + "where LogResource.resourceId=" + BorrowedResources.getInt(3) + " and "
                        + "LogResource.userId=" + BorrowedResources.getInt(1) + ";";

                ResultSet dueDateResult = master.getDatabaseManager().getDatabaseCommand().executeQuery(duDateSql);

                dueDate = new Date();
                try {

                    dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDateResult.getString(1));
                } catch (ParseException e) {
                    System.out.println("the log date of LogResource cannot be returnned.");
                }

                currentResource = new ViewReturnResource(
                        BorrowedResources.getInt(1),
                        BorrowedResources.getString(2),
                        BorrowedResources.getInt(3),
                        BorrowedResources.getString(4),
                        ResourceType.valueOf(typeofResource.getString(2)),
                        dueDate);
                // currentResource = new ViewReturnResource (1,"a",2,"d",ResourceType.valueOf("DVD"),dueDate);
                System.out.println(currentResource.getCalculatedFine() + "   " + currentResource.getOverDueDays());
                contents.add(currentResource);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        userId.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("userId"));
        userName.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("userName"));
        resourceId.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("resourceId"));
        resourceName.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("resourceName"));
        overDueDays.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("overDueDays"));
        calculatedFine.setCellValueFactory(new PropertyValueFactory<ViewReturnResource, String>("calculatedFine"));

        table.setItems(contents);
        table.setRowFactory(table -> {
            TableRow<ViewReturnResource> SelectedRow = new TableRow<>();
            SelectedRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!SelectedRow.isEmpty())) {
                    ViewReturnResource row = SelectedRow.getItem();

                    Date currentDate = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String result = simpleDateFormat.format(currentDate.clone());

                    String logCommandString = "insert into LogResource (userId,resourceId,logDate,logTypeId)values("
                            + row.getUserId() + "," + row.getResourceId() + ",'" + result + "'," + 1 + ");";

                    master.getDatabaseManager().getDatabaseCommand().setCommandString(logCommandString);
                    master.getDatabaseManager().getDatabaseCommand().executeCommand();

                    String fineCommandString = "insert into FineLog (userId,amount)values("
                            + row.getUserId() + "," + row.getCalculatedFine() + ");";

                    master.getDatabaseManager().getDatabaseCommand().setCommandString(fineCommandString);
                    master.getDatabaseManager().getDatabaseCommand().executeCommand();

                }
            });
            return SelectedRow;
        });

    }
}
