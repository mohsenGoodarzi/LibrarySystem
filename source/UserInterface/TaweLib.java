package UserInterface;

import DatabaseLayout.DatabaseManager;
import Models.Logs.FineLog;
import Models.Resources.Book;
import Models.Resources.Dvd;
import Models.Resources.Laptop;
import Models.Resources.Resource;
import Models.Users.UserAccount;
import UserInterface.FXML.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main Application, which handles the change of scenes and logins. Hold the
 * currently login user as well as the main stage.
 *
 * @author Martin Trifinov (martin.trifonov98@gmail.com)
 */
public class TaweLib extends Application {

    private final int SCENE_WIDTH = 900;
    private final int SCENE_HEIGHT = 600;
    private final int DETAILS_WIDTH = 600;
    private final int DETAILS_HEIGHT = 470;

    private Stage stage;

    private DatabaseManager databaseManager;
    private UserAccount user;

    /**
     * Launches the application. Loads the stage and set it to log in.
     *
     * @param primaryStage The stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        conncectDatabase();

        stage = primaryStage;
        stage.setTitle("Tawe-Lib");
        goToLogIn();
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinHeight(SCENE_HEIGHT);
        stage.setMinWidth(SCENE_WIDTH);
        stage.show();
    }

    /**
     * Getter for the Database Manager.
     *
     * @return databaseManager gets the database.
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Setter for the Database Manager.
     *
     * @param databaseManager sets the database concept
     */
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Returns the currently logged in user.
     *
     * @return user gets the user
     */
    public UserAccount getUser() {
        return user;
    }

    /**
     * Sets the currently logged in user.
     *
     * @param user gets the user
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * Sets the currently login user to a not existing one. To be called only
     * when user logs out.
     */
    private void resetLogin() {
        user = new UserAccount(0, "", "", "", 0, "", "", "", "");
    }

    /**
     * Changes the scene to the Login and resets the logged in user.
     */
    public void goToLogIn() {
        resetLogin();
        Login login = new Login(this);
        stage.setScene(new Scene(login, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to LibrarianDashboard.
     */
    public void goToLibrarianDashboard() {
        LibrarianDashboard librarianDashboard = new LibrarianDashboard(this);
        stage.setScene(new Scene(librarianDashboard, SCENE_WIDTH,
                SCENE_HEIGHT));
    }

    /**
     * Changes the scene to LendResource.
     */
    public void goToLendResource() {
        LendResource lendResource = new LendResource(this);
        stage.setScene(new Scene(new LendResource(this), SCENE_WIDTH,
                SCENE_HEIGHT));
    }

    /**
     * Changes the scene to UserDashboard.
     */
    public void goToUserDashboard() {
        UserDashboard userDashboard = new UserDashboard(this);
        stage.setScene(new Scene(userDashboard, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ReturnResources.
     */
    public void goToReturnResource() {
        ReturnResource returnResource = new ReturnResource(this);
        stage.setScene(new Scene(new ReturnResource(this), SCENE_WIDTH,
                SCENE_HEIGHT));
    }

    /**
     * Changes the scene to PayFines.
     */
    public void goToPayFines() {
        PayFine payFine = new PayFine(this);
        stage.setScene(new Scene(payFine, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to NewAccount.
     */
    public void goToNewAccount() {
        NewAccount newAccount = new NewAccount(this);
        stage.setScene(new Scene(newAccount, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ResourceManager.
     */
    public void goToResourceManager() {
        ResourceManager resourceManager = new ResourceManager(this);
        stage.setScene(new Scene(resourceManager, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to AddBook.
     */
    public void goToAddBook() {
        AddBook addBook = new AddBook(this);
        stage.setScene(new Scene(addBook, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to AddDVD.
     */
    public void goToAddDVD() {
        AddDVD addDVD = new AddDVD(this);
        stage.setScene(new Scene(addDVD, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to AddLaptop.
     */
    public void goToAddLaptop() {
        AddLaptop addLaptop = new AddLaptop(this);
        stage.setScene(new Scene(addLaptop, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to EditBook.
     */
    public void goToEditBook() {
        EditBook editBook = new EditBook(this);
        stage.setScene(new Scene(editBook, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to EditDVD.
     */
    public void goToEditDVD() {
        EditDVD editDVD = new EditDVD(this);
        stage.setScene(new Scene(editDVD, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to EditLaptop.
     */
    public void goToEditLaptop() {
        EditLaptop editLaptop = new EditLaptop(this);
        stage.setScene(new Scene(editLaptop, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewOverdue.
     */
    public void goToViewOverdue() {
        ViewOverdue viewOverdue = new ViewOverdue(this);
        stage.setScene(new Scene(viewOverdue, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewHistory.
     */
    public void goToViewHistory() {
        ViewHistory viewHistory = new ViewHistory(this);
        stage.setScene(new Scene(viewHistory, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to SearchResource.
     */
    public void goToSearchResource() {
        SearchResource searchResource = new SearchResource(this);
        stage.setScene(new Scene(searchResource, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewRequested.
     */
    public void goToViewRequested() {
        ViewRequested viewRequested = new ViewRequested(this);
        stage.setScene(new Scene(viewRequested, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewReserved.
     */
    public void goToViewReserved() {
        ViewReserved viewReserved = new ViewReserved(this);
        stage.setScene(new Scene(viewReserved, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewBorrowed.
     */
    public void goToViewBorrowed() {
        ViewBorrowed viewBorrowed = new ViewBorrowed(this);
        stage.setScene(new Scene(viewBorrowed, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to EditAccount.
     */
    public void goToEditAccount() {
        EditAccount editAccount = new EditAccount(this);
        stage.setScene(new Scene(editAccount, SCENE_WIDTH, SCENE_HEIGHT));

    }

    /**
     * Launches a pop-up window with Custom Avatar Drawing Scene.
     */
    public void goToAvatarDrawer() {
        Drawing drawAvatar = new Drawing(this);
        Stage drawer = new Stage();
        drawer.setTitle("Avatar Drawer");
        drawer.setScene(new Scene(drawAvatar, drawAvatar.getWidth(),
                drawAvatar.getHeight()));
        //drawer.setMaximized(true);
        drawer.initModality(Modality.WINDOW_MODAL);
        drawer.initOwner(stage);
        drawer.show();

    }

    /**
     * Launches a pop-up window with details for a specific Book.
     *
     * @param book passes the selected book
     * @param status passes the status of the book
     */
    public void goToDetailsOfBook(Book book, String status) {

        DetailsOfBook detailsOfBook = new DetailsOfBook(this, book, status);

        stage.setScene(new Scene(detailsOfBook, DETAILS_WIDTH, DETAILS_HEIGHT));
    }

    /**
     * Launches a pop-up window with details for a specific DVD.
     *
     * @param dvd the specific DVD.
     * @param status passes the status of the book
     */
    public void goToDetailsOfDVD(Dvd dvd, String status) {

        DetailsOfDVD detailsOfDvd = new DetailsOfDVD(this, dvd, status);

        stage.setScene(new Scene(detailsOfDvd, DETAILS_WIDTH, DETAILS_HEIGHT));
    }

    /**
     * Launches a pop-up window with details for a specific Laptop.
     *
     * @param laptop the specific Laptop.
     * @param status passes the status of the book
     */
    public void goToDetailsOfLaptop(Laptop laptop, String status) {
        DetailsOfLaptop detailsOfLaptop
                = new DetailsOfLaptop(this, laptop, status);
        stage.setScene(new Scene(detailsOfLaptop, DETAILS_WIDTH,
                DETAILS_HEIGHT));
    }

    /**
     * Launches a pop-up window to Pay the specific fine.
     *
     * @param finelog The specific fine for user.
     */
    public void popUpPay(FineLog finelog) {
        PayPopUp payPopUp = new PayPopUp(this, finelog);
        Stage popUp = new Stage();
        popUp.setScene(new Scene(payPopUp, 400, 200));
        popUp.setTitle("Pay Fine");
        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(stage);
        popUp.show();
    }

    /**
     * Changes the scene to HistoryView.
     *
     * @param resource specific fine.
     */
    public void goToHistoryView(Resource resource) {
        HistoryView historyView = new HistoryView(this, resource);
        stage.setScene(new Scene(historyView, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Changes the scene to ViewTransactions.
     */
    public void goToViewTransactions() {
        ViewTransactions viewTransactions = new ViewTransactions(this);
        stage.setScene(new Scene(viewTransactions, SCENE_WIDTH, SCENE_HEIGHT));

    }

    /**
     * Sets up the database to the default file.
     */
    private void conncectDatabase() {
        databaseManager = new DatabaseManager("Database.db");
    }

}
