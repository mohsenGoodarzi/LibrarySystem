package Models.Resources;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class holds the data and methods of laptop
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Laptop extends Resource {

    private final double FINERATE = 10.00;
    private final double MAXIMUMFINE = 100.00;

    private SimpleIntegerProperty laptopId;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty model;
    private SimpleIntegerProperty osModelId;

    /**
     * This creates a new laptop object
     *
     * @param laptopId
     * @param title
     * @param releasedYear
     * @param imageLocation
     * @param manufacturer
     * @param model
     * @param osModelId
     */
    public Laptop(int laptopId, String title, int releasedYear, String imageLocation, String manufacturer, String model, int osModelId) {
        super(laptopId, title, releasedYear, imageLocation);
        this.laptopId = new SimpleIntegerProperty(laptopId);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.model = new SimpleStringProperty(model);
        this.osModelId = new SimpleIntegerProperty(osModelId);
    }

    /**
     * Gets the laptop ID of the laptop
     *
     * @return returns the laptopID
     */
    public int getLaptopId() {
        return laptopId.get();
    }

    /**
     * Sets the ID for the Laptop resource
     *
     * @param laptopId is the Laptop's ID
     */
    public void setLaptopId(int laptopId) {
        this.laptopId.set(laptopId);
    }

    /**
     * Gets the manufacturer of the laptop.
     *
     * @return String name.
     */
    public String getManufacturer() {
        return manufacturer.get();
    }

    /**
     * Sets the manufacturer for the Laptop resource
     *
     * @param manufacturer is the name of manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    /**
     * Gets the model of the laptop
     *
     * @return returns the model of the laptop
     */
    public String getModelId() {
        return model.get();
    }

    /**
     * Sets the model name for the Laptop resource
     *
     * @param model is the model name
     */
    public void setModel(String model) {
        this.model.set(model);
    }

    /**
     * Gets the operating system of the laptop
     *
     * @return returns the operating system model
     */
    public int getOsModel() {
        return osModelId.get();
    }

    /**
     * Sets the operating system name for the Laptop resource
     *
     * @param osModelId is the operating system name
     */
    public void setOsModel(int osModelId) {
        this.osModelId.set(osModelId);
    }

    /**
     * Returns the fine rate of the book
     *
     * @return the fine rate
     */
    public double getFineRate() {
        return FINERATE;
    }

    /**
     * Returns the maximum fine a laptop can have
     *
     * @return the maximum fine
     */
    public double getMaximumFine() {
        return MAXIMUMFINE;
    }
}
