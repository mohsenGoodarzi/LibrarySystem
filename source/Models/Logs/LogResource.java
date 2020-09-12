package Models.Logs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds all information needed for a log. A log is created whenever
 * something happens to a resource.
 *
 * @author Daniel Griffiths
 * @version 1.0
 */
public class LogResource {

    private SimpleIntegerProperty logId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty resourceId;
    private SimpleStringProperty logDate;
    private SimpleIntegerProperty logTypeId;

    /**
     * Create a new log.
     *
     * @param logId
     * @param userId
     * @param resourceId
     * @param logDate
     * @param logTypeId
     */
    public LogResource(int logId, int userId, int resourceId, Date logDate, int logTypeId) {
        this.logId = new SimpleIntegerProperty(logId);
        this.userId = new SimpleIntegerProperty(userId);
        this.resourceId = new SimpleIntegerProperty(resourceId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String result = sdf.format(logDate.clone());
        this.logDate = new SimpleStringProperty(result);
        this.logTypeId = new SimpleIntegerProperty(logTypeId);
    }

    /**
     * Get the ID of the log.
     *
     * @return The ID of the log
     */
    public int getLogId() {
        return logId.get();
    }

    /**
     * Set the ID of the log.
     *
     * @param logId The ID of the log
     */
    public void setLogId(int logId) {
        this.logId.set(logId);
    }

    /**
     * Get the ID of the log.
     *
     * @return The id of the log
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Set the ID of the user corresponding to this log.
     *
     * @param userId The ID of the user
     */
    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    /**
     * Gets the ID of the resource corresponding to the log.
     *
     * @return The ID of the resource
     */
    public int getResourceId() {
        return resourceId.get();
    }

    /**
     * Sets the ID of the resource corresponding to the log.
     *
     * @param resourceId The ID of the resource
     */
    public void setResourceId(int resourceId) {
        this.resourceId.set(resourceId);
    }

    /**
     * Sets the date of when the log was created.
     *
     * @return The date
     */
    public Date getLogDate() {
        Date result = new Date();
        try {

            result = new SimpleDateFormat("dd/MM/yyyy").parse(logDate.get());
        } catch (ParseException e) {
            System.out.println("the log date of LogResource "
                    + "cannot be returnned.");
        }
        return result;

    }

    /**
     * Gets the date of when the log was created.
     *
     * @param logDate The date
     */
    public void setLogDate(Date logDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(logDate.clone());
        this.logDate.set(result);
    }

    /**
     * Gets the log type associated with this log. The log type holds the state
     * of a resource.
     *
     * @return The log type
     */
    public int getLogTypeId() {
        return logTypeId.get();
    }

    /**
     * Gets the log type associated with this log. The log type holds the state
     * of a resource.
     *
     * @param logTypeId The log type
     */
    public void setLogTypeId(int logTypeId) {
        this.logTypeId.set(logTypeId);
    }
}
