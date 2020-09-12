/*
 * Author: Mohsen Goodarzi
 * */
package Models.ViewModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * View Element for JavaFX TableView. Shows the Resource.
 */
public class ViewResource {

    private SimpleIntegerProperty resourceId;
    private SimpleStringProperty title;
    private SimpleStringProperty type;
    private SimpleIntegerProperty rating;
    private SimpleIntegerProperty numberOfCopies;
    private SimpleStringProperty typeOfLog;

    /**
     * Creates the specified resource.
     *
     * @param resourceId
     * @param title
     * @param type
     * @param rating
     * @param numberOfCopies
     * @param typeOfLog
     */
    public ViewResource(int resourceId, String title, String type, int rating, int numberOfCopies, String typeOfLog) {
        this.resourceId = new SimpleIntegerProperty(resourceId);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.rating = new SimpleIntegerProperty(rating);
        this.numberOfCopies = new SimpleIntegerProperty(numberOfCopies);
        this.typeOfLog = new SimpleStringProperty(typeOfLog);
    }

    /**
     * Getter the number of copies.
     *
     * @return
     */
    public int getNumberOfCopies() {
        return this.numberOfCopies.get();
    }

    /**
     * Sets the number of copies.
     *
     * @param numberOfCopies
     */
    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies.set(numberOfCopies);
    }

    /**
     * Gets the type of the log.
     *
     * @return
     */
    public String getTypeOfLog() {
        return typeOfLog.get();
    }

    /**
     * Sets the type of the log.
     *
     * @param typeOfLog
     */
    public void setTypeOfLog(String typeOfLog) {
        this.typeOfLog.set(typeOfLog);

    }

    /**
     * Gets the resource id.
     *
     * @return
     */
    public int getResourceId() {
        return resourceId.get();
    }

    /**
     * Gets the resource id.
     *
     * @param resourceId
     */
    public void setResourceId(int resourceId) {
        this.resourceId.set(resourceId);
    }

    /**
     * Gets the title.
     *
     * @return
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Gets the type.
     *
     * @return
     */
    public String getType() {
        return type.get();
    }

    /**
     * Sets the type.
     *
     * @param type
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Gets the rating.
     *
     * @return
     */
    public int getRating() {
        return rating.get();
    }

    /**
     * Sets the rating.
     *
     * @param rating
     */
    public void setRating(int rating) {
        this.rating.set(rating);
    }

}
