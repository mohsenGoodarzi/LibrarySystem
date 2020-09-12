package Models.ViewModels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Models.Resources.ResourceType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * View Element for JavaFX TableView. Displays a resource which is to be
 * returned.
 */
public class ViewReturnResource {

    private final double DVD_OR_BOOK_FINE_RATE = 2.00;
    private final double DVD_OR_BOOK_MAXIMUM_FINE = 25.00;
    private final double LAPTOP_FINE_RATE = 10.00;
    private final double LAPTOP_MAXIMUM_FINE = 100.00;

    private ResourceType typeOfResource;
    private SimpleIntegerProperty userId;
    private SimpleStringProperty userName;
    private SimpleIntegerProperty resourceId;
    private SimpleStringProperty resourceName;
    private SimpleStringProperty borrowedDate;
    private SimpleIntegerProperty overDueDays;
    private SimpleDoubleProperty calculatedFine;

    /**
     * Creates the specific return.
     *
     * @param userId
     * @param userName
     * @param resourceId
     * @param resourceName
     * @param typeOfResource
     * @param borrowedDate
     */
    public ViewReturnResource(int userId, String userName, int resourceId, String resourceName,
            ResourceType typeOfResource, Date borrowedDate) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.resourceId = new SimpleIntegerProperty(resourceId);
        this.resourceName = new SimpleStringProperty(resourceName);
        this.overDueDays = new SimpleIntegerProperty(0);
        this.calculatedFine = new SimpleDoubleProperty(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(borrowedDate.clone());
        this.borrowedDate = new SimpleStringProperty(result);
        this.typeOfResource = typeOfResource;
    }

    /**
     * Getters for userId.
     *
     * @return int as the id.
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Setter for userId.
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    /**
     * Getter for userName.
     *
     * @return String as the name.
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Setter for userName.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    /**
     * Getter for resourceId.
     *
     * @return int as the id.
     */
    public int getResourceId() {
        return resourceId.get();
    }

    /**
     * Setter for resourceId.
     *
     * @param resourceId
     */
    public void setResourceId(int resourceId) {
        this.resourceId.set(resourceId);
    }

    /**
     * Getter for getResourceName.
     *
     * @return String as the name.
     */
    public String getResourceName() {
        return resourceName.get();
    }

    /**
     * Setter for ResourceName.
     *
     * @param resourceName
     */
    public void setResourceName(String resourceName) {
        this.resourceName.set(resourceName);
    }

    /**
     * Returns the Days overdue as an int.
     *
     * @return int as the days overdue.
     */
    public int getOverDueDays() {
        overDueDays.set((int) calculateDueDays(this.getBorrowedDate()));
        return overDueDays.get();
    }

    /**
     * Returns the fine.
     *
     * @return double as the fine.
     */
    public double getCalculatedFine() {
        calculatedFine.set(calculateFine(this.typeOfResource));
        return calculatedFine.get();
    }

    /**
     * Returns the BorrowedDate
     *
     * @return Date
     */
    public Date getBorrowedDate() {
        Date tempDate = new Date();

        try {

            tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(borrowedDate.get());
        } catch (ParseException e) {
            System.out.println("the borrowed Date cannot be Calculated.");
        }
        return tempDate;
    }

    /**
     * Setter for BorrowedDate
     *
     * @param borrowedDate
     */
    public void setBorrowedDate(Date borrowedDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(borrowedDate.clone());
        this.borrowedDate.set(result);
    }

    /**
     * Calculates the due days.
     */
    private long calculateDueDays(Date borrowedDate) {
        Date currentdate = new Date();
        long diff = currentdate.getTime() - borrowedDate.getTime();
        long lateDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return lateDays;
    }

    /**
     * Calculates the due days.
     */
    private double calculateFine(ResourceType typeOfResource) {
        double result = 0;
        if (typeOfResource == ResourceType.DVD || typeOfResource == ResourceType.BOOK) {
            result = this.getOverDueDays() * DVD_OR_BOOK_FINE_RATE;
            if (result > DVD_OR_BOOK_MAXIMUM_FINE) {
                result = DVD_OR_BOOK_MAXIMUM_FINE;
            }

        } else if (typeOfResource == ResourceType.LAPTOP) {
            result = this.getOverDueDays() * LAPTOP_FINE_RATE;
            if (result > LAPTOP_MAXIMUM_FINE) {
                result = LAPTOP_MAXIMUM_FINE;
            }
        }

        return result;
    }
}
