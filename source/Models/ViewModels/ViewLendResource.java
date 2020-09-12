package Models.ViewModels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * View Element for Javafx TableView. Displays resource which is reserved and to
 * be lend.
 */
public class ViewLendResource {

    private SimpleIntegerProperty resourceId;
    private SimpleStringProperty resourceName;
    private SimpleStringProperty logDate;
    private SimpleIntegerProperty userId;
    private SimpleStringProperty userName;

    /**
     * Creates the specific resource.
     *
     * @param resourceId
     * @param resourceName
     * @param logDate
     * @param userId
     * @param userName
     */
    public ViewLendResource(int resourceId, String resourceName, Date logDate, int userId, String userName) {
        this.resourceId = new SimpleIntegerProperty(resourceId);
        this.resourceName = new SimpleStringProperty(resourceName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(logDate.clone());
        this.logDate = new SimpleStringProperty(result);
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
    }

    /**
     * Getter for the resourceId.
     *
     * @return
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
     * Getter for resource name.
     *
     * @return
     */
    public String getResourceName() {
        return resourceName.get();
    }

    /**
     * Setter for resource name.
     *
     * @param resourceName
     */
    public void setResourceName(String resourceName) {
        this.resourceName.set(resourceName);
    }

    /**
     * Getter for logDate.
     *
     * @return
     */
    public Date getLogDate() {
        Date result = new Date();
        try {

            result = new SimpleDateFormat("dd/MM/yyyy").parse(logDate.get());
        } catch (ParseException e) {
            System.out.println("the log date of LogResource cannot be returnned.");
        }
        return result;

    }

    /**
     * Setter for logDate.
     *
     * @param logDate
     */
    public void setLogDate(Date logDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(logDate.clone());
        this.logDate.set(result);
    }

    /**
     * Getter for user id.
     *
     * @return
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Setter for user id.
     *
     * @param userId
     */
    public void setUserId(SimpleIntegerProperty userId) {
        this.userId = userId;
    }

    /**
     * Getter for userName.
     *
     * @return
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Setter for user name.
     *
     * @param userName
     */
    public void setUserName(SimpleStringProperty userName) {
        this.userName = userName;
    }

}
