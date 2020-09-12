package Models.Resources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for the OSModel.
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class OsModel {

    private SimpleIntegerProperty osId;
    private SimpleStringProperty name;

    /**
     * Creates a new OsModel
     *
     * @param osId is the operating system ID
     * @param name is the name of the operating system
     */
    public OsModel(int osId, String name) {
        this.osId = new SimpleIntegerProperty(osId);
        this.name = new SimpleStringProperty(name);

    }

    /**
     * Returns the ID of the operating system
     *
     * @return the Operating system ID
     */
    public int getOsId() {
        return osId.get();
    }

    /**
     * Sets the name of the operating system ID
     *
     * @param osId is the ID of the operating system
     */
    public void setOsId(int osId) {
        this.osId.set(osId);
    }

    /**
     * Returns the name of the operating system
     *
     * @return the osName
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the name of the operating system
     *
     * @param name is the name of the operating systemW
     */
    public void setName(String name) {
        this.name.set(name);
    }

}
