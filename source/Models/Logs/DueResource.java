package Models.Logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class is used to calculate due dates and fines for late resources.
 *
 * @author Daniel Griffiths
 * @version 1.0
 */
public class DueResource {

    private SimpleIntegerProperty dueResourceId;
    private SimpleIntegerProperty logId;
    private SimpleStringProperty dueDate;

    /**
     * Create a new due resource.
     *
     * @param dueResourceId
     * @param logId
     * @param dueDate
     */
    public DueResource(int dueResourceId, int logId, Date dueDate) {
        this.dueResourceId = new SimpleIntegerProperty(dueResourceId);
        this.logId = new SimpleIntegerProperty(logId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(dueDate.clone());
        this.dueDate = new SimpleStringProperty(result);
    }

    /**
     * Gets the ID of the due resource.
     *
     * @return ID of the due resource
     */
    public int getDueResourceId() {
        return dueResourceId.get();
    }

    /**
     * Sets the ID of the due resource.
     *
     * @param dueResourceId
     */
    public void setDueResourceId(int dueResourceId) {
        this.dueResourceId.set(dueResourceId);
    }

    /**
     * Gets the ID of the log corresponding to a due resource.
     *
     * @return The ID of the log
     */
    public int getLogId() {
        return logId.get();
    }

    /**
     * Sets the ID of the log corresponding to a due resource.
     *
     * @param logId The ID of the log
     */
    public void setLogId(int logId) {
        this.logId.set(logId);
    }

    /**
     * Gets the due date of a resource.
     *
     * @return The due date
     */
    public Date getDueDate() {
        Date result = new Date();
        try {
            System.out.println("internal is " + dueDate.get());
            result = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate.get());
        } catch (ParseException e) {
            System.out.println("the due date from DueResource cannot be returnned.");
        }
        return result;
    }

    /**
     * Sets the due date of a resource.
     *
     * @param dueDate The due date
     */
    public void setDueDate(Date dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String result = sdf.format(dueDate.clone());
        this.dueDate.set(result);
    }
}
