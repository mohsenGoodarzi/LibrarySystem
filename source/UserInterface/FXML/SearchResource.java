package UserInterface.FXML;

import Models.Resources.*;
import Models.ViewModels.ViewResource;
import UserInterface.TaweLib;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class populates the table with a collection of resources. The user can
 * filter results depending on their needs.
 *
 * @author Kieran Hodgson,Thomas Poultney
 * @version 1.0
 * @since 2018-12-08
 *
 */
public class SearchResource extends AnchorPane {

    private final int FIRST_COLUMN = 1;
    private final int SECOND_COLUMN = 2;
    private final int FOURTH_COLUMN = 3;

    private final TaweLib master;
    private String status;

    private ObservableList<ViewResource> contents
            = FXCollections.observableArrayList();

    @FXML
    private TableView<ViewResource> table;
    @FXML
    private TableColumn<ViewResource, Number> id;
    @FXML
    private TableColumn<ViewResource, String> title;
    @FXML
    private TableColumn<ViewResource, String> type;
    @FXML
    private TableColumn<ViewResource, Number> rating;
    @FXML
    private TextField nameFilter;
    @FXML
    private ComboBox<String> typeFilter;

    private String sqlStatement = "";
    private String typeChanger = "";

    TableColumn idCol = new TableColumn("ID");
    TableColumn titleCol = new TableColumn("Title");
    TableColumn typeCol = new TableColumn("Type");
    TableColumn ratingCol = new TableColumn("Rating");
    TableColumn numberCol = new TableColumn("Number of Copies");
    TableColumn logCol = new TableColumn("Availability");

    /**
     * master is the is the main controller for the data in the project
     *
     * @param master master is the reference to the main application loadFXML
     * loads the FXML file for SearchResource
     */
    public SearchResource(TaweLib master) {
        this.master = master;
        loadFXML();
    }

    /**
     * loadFXML loads the FXML file for SearchResource
     *
     * @throws if the SearchResource.fxml file doesn't load throws IOException
     */
    private void loadFXML() {
        //try's to load the SearchResource.fxml

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("SearchResource.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (IOException ex) {
            //prints the exception and closes the program
            System.out.println(ex);
            System.exit(0);
        }
        /**
         * calls setTypeFilter setSqlCommand searchData typeFilterListener
         */
        setTypeFilter();
        typeFilterListener();
        setSqlCommand();
        searchData();

        table.setRowFactory(tv -> {
            TableRow<ViewResource> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ViewResource rowData = row.getItem();
                    status = row.getItem().getTypeOfLog();
                    switch (rowData.getType()) {
                        case "DVD": {
                            //if selected row is a dvd, 
                            //creates a new dvd,and passes status to
                            //dvdDetails
                            master.goToDetailsOfDVD(createDvd(
                                    rowData.getResourceId()), status);

                        }
                        break;

                        case "Book": {
                            //if selected row is a dvd, 
                            //creates a new book,and passes status to
                            //bookDetails
                            master.goToDetailsOfBook(createBook(
                                    rowData.getResourceId()), status);
                        }

                        break;
                        case "Laptop": {
                            //if selected row is a laptop, 
                            //creates a new laptop,and passes status to
                            //laptopDetails

                            master.goToDetailsOfLaptop(createLaptop(
                                    rowData.getResourceId()), status);
                        }

                    }
                    System.out.println(rowData);

                }
            });
            return row;
        });
    }

    /**
     * typeFilterListener listens to the typeFilter
     */
    private void typeFilterListener() {
        // when typeFilter is triggered with 
        //an action it completes the onSetAction
        typeFilter.setOnAction((event) -> {
            //calls setSqlCommand for when the user 
            //triggers by changing the comboBox
            setSqlCommand();
            //calls searchData for when the user 
            //triggers by changing the comboBox
            searchData();
        });

    }

    /**
     * setTypeFilter sets the elements of typeFilter
     */
    private void setTypeFilter() {
        // sets all the elements in the typeFilter ComboBox
        typeFilter.getItems().setAll("Book", "DVD", "Laptop");
        // selects the first element in the typeFilter ComboBox
        typeFilter.getSelectionModel().selectFirst();
    }

    /**
     * searchData adds data to the table
     *
     */
    @FXML
    public void searchData() {
        //sets the sql query result to a result set
        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlStatement);
        //sets the currentResource to null as so when the table is changed,
        //it doesn't print out the current values again and again
        ViewResource currentResource = null;
        //sets the tables headings
        setTable(idCol, titleCol, typeCol, ratingCol, numberCol, logCol);
        //adds the content from the database to the current resource
        addContent(rs, currentResource);
        //sets the cells to their respective columns
        setCells(idCol, titleCol, typeCol, ratingCol, numberCol, logCol);
        //creates a filtered list of the contents
        FilteredList<ViewResource> filteredData
                = new FilteredList<>(contents, p -> true);
        //Filters the filtered list in filteredData
        textFiltering(filteredData);
        //creates a sortedList of the filteredData
        SortedList<ViewResource> sortedData 
                = new SortedList<>(filteredData);
        //sorts the filtered list in filteredData
        tableSorted(sortedData);
        //adds the completed data to the table
        table.setItems(sortedData);

    }

    /**
     * setTable set the table up to the specified details
     *
     * @param idCol the idColumn
     * @param titleCol the title Column
     * @param typeCol the Type column
     * @param ratingCol the Rating column
     */
    private void setTable(TableColumn idCol, TableColumn titleCol, 
            TableColumn typeCol, TableColumn ratingCol, TableColumn numberCol,
            TableColumn logCol) {
        //this is used so when the user changes something about 
        //the table then the data seen isn't duplicates
        contents.clear();
        //allows us to add data to the table
        table.setEditable(true);
        table.getColumns().clear();
        idCol.setPrefWidth(50.0);
        titleCol.setPrefWidth(270.0);
        typeCol.setPrefWidth(100.0);
        ratingCol.setPrefWidth(50.0);
        numberCol.setPrefWidth(50.0);
        table.getColumns().addAll(idCol, titleCol, typeCol,
                ratingCol, numberCol, logCol);
    }

    /**
     * @param rs result set from the sql query
     * @param currentResource the contents of what is going to be added to the
     * tableview
     */
    private void addContent(ResultSet rs, ViewResource currentResource) {
        //trys add each of the resource elements gained from the 
        //database to the currentResource list
        try {
            while (rs.next()) {
                currentResource = new ViewResource(rs.getInt(FIRST_COLUMN),
                        rs.getString(SECOND_COLUMN), typeChanger,
                        rs.getInt(FOURTH_COLUMN), rs.getInt(4), rs.getString(3));

                contents.add(currentResource);
            }
        } catch (SQLException e) {
            //changes the default text on the table if there is no data found
            table.setPlaceholder(new Label("No Resources Found"));
        }
    }

    /**
     * setCells sets the cell value to specific elements from the database
     *
     * @param idCol the idColumn
     * @param titleCol the title Column
     * @param typeCol the Type column
     * @param ratingCol the Rating column
     */
    private void setCells(TableColumn idCol, TableColumn titleCol, 
            TableColumn typeCol, TableColumn ratingCol, TableColumn numberCol,
            TableColumn logCol) {
        //sets the data from resourceId to idCol
        idCol.setCellValueFactory(
                new PropertyValueFactory<Resource, Number>("resourceId")
        );
        //sets the data from title to titleCol
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("title")
        );
        //sets the data from type to typeCol
        typeCol.setCellValueFactory(
                new PropertyValueFactory<Resource, String>("type")
        );
        //sets the data from rating to ratingCol
        ratingCol.setCellValueFactory(
                new PropertyValueFactory<Resource, Number>("rating")
        );
        //sets the data from numberOfcopies to numberCol
        numberCol.setCellValueFactory(
                new PropertyValueFactory<Resource, Number>("numberOfCopies")
        );
        //sets the data from typeOfLog to logCol
        logCol.setCellValueFactory(
                new PropertyValueFactory<Resource, Number>("typeOfLog")
        );
    }

    /**
     * tableSorted sorts the data provided
     *
     * @param sortedData the data to be sorted
     */
    private void tableSorted(SortedList<ViewResource> sortedData) {
        //Sorts the data provided in sortedData
        sortedData.comparatorProperty().bind(table.comparatorProperty());

    }

    /**
     * textFiltering filters the data respective to title
     *
     * @param filteredData filters the titles in relation to the input
     *
     */
    private void textFiltering(FilteredList<ViewResource> filteredData) {
        /*	
		 * listens to the text field nameFilter for when it changes
		 * filters them by setting all the title to lower case as so there
		 * isn't any discrepancies in the wording
         */
        nameFilter.textProperty().addListener((observable, 
                oldValue, newValue) -> {
            filteredData.setPredicate(viewResource -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (viewResource.getTitle().toLowerCase()
                        .contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    /**
     * setSqlCommand is used to filter the sql statement that is summoned and
     * sets sql to different statements depending on the value of typeFilter.
     */
    private void setSqlCommand() {
        //sets type to be the value of typeFilter
        nameFilter.clear();
        String type = typeFilter.getValue().toString();
        //switches between the type gained by typeFilter
        switch (type) {
            //case "DVD" for typeFilter equals "DVD"
            case "DVD": {
                sqlDVD();
            }
            break;
            //case "Book" for typeFilter equals "Book"
            case "Book": {
                sqlBook();
            }
            break;
            //case "Laptop" for typeFilter equals "Laptop
            case "Laptop": {
                sqlLaptop();
            }
            break;
            //case "All" for typeFilter equals "All"
            case "All": {
                sqlAll();

            }
            break;
        }
    }

    /**
     * sqlAll sets the sqlStatement to give result for all
     *
     * @deprecated
     */
    private void sqlAll() {
        //sets the sqlStatement to the specified query
        sqlStatement = "select Resource.resourceId, Resource.title,"
                + "LogType.logTypeName,COUNT(Resource.resourceId) "
                + "FROM LogResource inner join Resource on "
                + "Resource.resourceId = LogResource.resourceId "
                + "inner JOIN LogType on LogResource.logTypeId= "
                + "LogType.logTypeId  "
                + "INNER Join Book on Book.bookId = "
                + "Resource.resourceId "
                + "GROUP by Resource.title, LogResource.logTypeId ";

        //sets type changer so the type that is printed is the correct one
        typeChanger = "All";
    }

    /**
     * sqlDVD sets the sqlStatement to give result for DVD
     */
    private void sqlDVD() {
        //sets the sqlStatement to the specified query
        sqlStatement = "select Resource.resourceId, Resource.title,"
                + "LogType.logTypeName,COUNT(Resource.resourceId) "
                + "FROM LogResource inner join Resource on "
                + "Resource.resourceId = LogResource.resourceId "
                + "inner JOIN LogType on LogResource.logTypeId= "
                + "LogType.logTypeId  "
                + "INNER Join Dvd on Dvd.dvdId = Resource.resourceId "
                + "GROUP by Resource.title, LogResource.logTypeId ";
        //sets type changer so the type that is printed is the correct one
        typeChanger = "DVD";

    }

    /**
     * sqlBook sets the sqlStatement to give result for Book
     */
    private void sqlBook() {
        //sets the sqlStatement to the specified query
        sqlStatement = "select Resource.resourceId, Resource.title,"
                + "LogType.logTypeName,COUNT(Resource.resourceId) "
                + "FROM LogResource inner join Resource on "
                + "Resource.resourceId = LogResource.resourceId "
                + "inner JOIN LogType on "
                + "LogResource.logTypeId= LogType.logTypeId  "
                + "INNER Join Book on Book.bookId ="
                + " Resource.resourceId "
                + "GROUP by Resource.title, LogResource.logTypeId ";
        //sets type changer so the type that is printed is the correct one
        typeChanger = "Book";

    }

    /**
     * sqlLaptop sets the sqlStatement to give result for Laptop
     */
    private void sqlLaptop() {
        //sets the sqlStatement to the specified query
        sqlStatement = "select Resource.resourceId, Resource.title,"
                + "LogType.logTypeName,COUNT(Resource.resourceId) "
                + "FROM LogResource inner join Resource on "
                + "Resource.resourceId = LogResource.resourceId "
                + "inner JOIN LogType on "
                + "LogResource.logTypeId= LogType.logTypeId  "
                + "INNER Join Laptop on "
                + "Laptop.laptopId = Resource.resourceId "
                + "GROUP by Resource.title, LogResource.logTypeId ";
        //sets type changer so the type that is printed is the correct one
        typeChanger = "Laptop";

    }

    /**
     * lets the user go back to the UserDashboard
     *
     * @param e is an event made by Back button being pressed
     */
    @FXML
    private void Back(ActionEvent e) {
        //goes back to the userDashboard
        master.goToUserDashboard();
    }

    /**
     * creates a new dvd object from the selected value
     *
     * @param resourceId the resource id of the object you want to create
     */
    private Dvd createDvd(int resourceId) {
        String sqlCommand = "Select * from Resource "
                + "inner join Dvd where ResourceId = " + resourceId;

        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        Dvd dvd = new Dvd(0, "", 0, "", "", 0, 0, 0);
        try {
            dvd = new Dvd(rs.getInt(1), rs.getString(2), rs.getInt(3), 
                    rs.getString(4), rs.getString(7), rs.getInt(8),
                    rs.getInt(9), rs.getInt(10));
            return dvd;
        } catch (SQLException ex) {
            System.out.print("Dvd failed to create");
        }
        return dvd;

    }

    /**
     * creates a new book object from the selected value
     *
     * @param resourceId the resource id of the object you want to create
     */
    private Book createBook(int resourceId) {
        String sqlCommand = "Select * from Resource "
                + "inner join Book where ResourceId = " + resourceId;

        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        Book book = new Book(0, "", 0, "", "", "", 0, 0, 0);
        try {
            book = new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),
                    rs.getString(4), rs.getString(7), rs.getString(8),
                    rs.getInt(9), rs.getLong(10), rs.getInt(11));
            return book;
        } catch (SQLException ex) {
            System.out.println("Book Failed to create");
        }
        return book;
    }

    /**
     * creates a laptop object from the selected value
     *
     * @param resourceId of the object you want to create
     */
    private Laptop createLaptop(int resourceId) {
        String sqlCommand = "Select * from Resource "
                + "inner join Laptop where ResourceId = " + resourceId;

        ResultSet rs = master.getDatabaseManager()
                .getDatabaseCommand().executeQuery(sqlCommand);
        Laptop laptop = new Laptop(0, "", 0, "", "", "", 0);
        try {
            laptop = new Laptop(rs.getInt(1), rs.getString(2), rs.getInt(3),
                    rs.getString(4), rs.getString(7),
                    rs.getString(9), rs.getInt(8));
            return laptop;
        } catch (SQLException ex) {
            System.out.println("laptop Failed to create");
        }
        return laptop;
    }
}
