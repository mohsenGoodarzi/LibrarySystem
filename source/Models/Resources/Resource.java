package Models.Resources;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class holds the data and methods for the resources.
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Resource {

    private SimpleIntegerProperty resourceId;
    private SimpleStringProperty title;
    private SimpleIntegerProperty releasedYear;
    private SimpleStringProperty imageLocation;

    /**
     * Create a resource.
     *
     * @param resourceId
     * @param title
     * @param releasedYear
     * @param imageLocation
     */
    public Resource(int resourceId, String title, int releasedYear, String imageLocation) {

        this.resourceId = new SimpleIntegerProperty(resourceId);
        this.title = new SimpleStringProperty(title);
        this.releasedYear = new SimpleIntegerProperty(releasedYear);
        this.imageLocation = new SimpleStringProperty(imageLocation);
    }

    /**
     * Returns the ID of the resource.
     *
     * @return the ID of the resource.
     */
    public int getResourceId() {
        return resourceId.get();
    }

    /**
     * Sets the id of the resource.
     *
     * @param id
     */
    public void setResourceId(int id) {
        this.resourceId.set(0);
    }

    /**
     * Returns the title of the resource.
     *
     * @return the title of the resource.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the title of the resource.
     *
     * @param title is the title of the resource.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Returns the year of the resource.
     *
     * @return the year of the resource.
     */
    public int getReleasedYear() {
        return releasedYear.get();
    }

    /**
     * Sets the year of the resource.
     *
     * @param releasedYear is the year of the resource.
     */
    public void setReleasedYear(int releasedYear) {
        this.releasedYear.set(releasedYear);
    }

    /**
     * Returns the image location of the image of the resource.
     *
     * @return the image location of the image of the resource.
     */
    public String getImageLocation() {
        return imageLocation.get();
    }

    /**
     * Sets the image location of the image of the resource.
     *
     * @param imageLocation is the image location of the image of the resource
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation.set(imageLocation);
    }
}
