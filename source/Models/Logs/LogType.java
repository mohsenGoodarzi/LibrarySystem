package Models.Logs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class specifies whether a Resource is either
 *
 * @author Daniel Griffiths
 * @version 1.0
 */
public class LogType {

    private SimpleIntegerProperty logTypeId;
    private SimpleStringProperty logTypeName;

    /**
     * Create a new log type.
     *
     * @param logTypeId
     * @param logTypeName
     */
    public LogType(int logTypeId, String logTypeName) {
        this.logTypeId = new SimpleIntegerProperty(logTypeId);
        this.logTypeName = new SimpleStringProperty(logTypeName);
    }

    /**
     * Gets the ID of the state of a resource.
     *
     * @return The ID of the state of a resource
     */
    public int getLogTypeId() {
        return logTypeId.get();
    }

    /**
     * Sets the ID of the state of a resource.
     *
     * @param logTypeId The ID of the state of a resource
     */
    public void setLogTypeId(int logTypeId) {
        this.logTypeId.set(logTypeId);
    }

    /**
     * Gets the state of a resource. Either
     *
     *
     * @return The sate of a resource
     */
    public String getLogTypeName() {
        return logTypeName.get();
    }

    /**
     * Sets the state of a resource. Either
     *
     *
     * @param logTypeName Sets the state of a resource
     */
    public void setLogTypeName(String logTypeName) {
        this.logTypeName.set(logTypeName);
    }
}
