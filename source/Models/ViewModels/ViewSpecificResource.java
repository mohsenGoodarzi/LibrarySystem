package Models.ViewModels;

import javafx.beans.property.SimpleStringProperty;

/**
 * Displaying resource for TableView.
 *
 */
public class ViewSpecificResource {

    private SimpleStringProperty title;
    private SimpleStringProperty borrowed;
    private SimpleStringProperty due;
    private SimpleStringProperty requested;

    /**
     * Creates the specific view.
     *
     * @param title
     * @param borrowed
     * @param due
     */
    public ViewSpecificResource(String title, String borrowed, String due) {
        this.title = new SimpleStringProperty(title);
        this.borrowed = new SimpleStringProperty(borrowed);
        this.due = new SimpleStringProperty(due);
    }

    /**
     * Creates the specific view.
     *
     * @param title
     * @param requested
     */
    public ViewSpecificResource(String title, String requested) {
        this.title = new SimpleStringProperty(title);
        this.requested = new SimpleStringProperty(requested);
    }

    /**
     * Getter for title.
     *
     * @return
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Setter for title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Getter for Borrowed.
     *
     * @return
     */
    public String getBorrowed() {
        return borrowed.get();
    }

    /**
     * Setter for Borrowed.
     *
     * @param borrowed
     */
    public void setBorrowed(String borrowed) {
        this.borrowed.set(borrowed);
    }

    /**
     * Getter for Due.
     *
     * @return
     */
    public String getDue() {
        return due.get();
    }

    /**
     * Setter for Due.
     *
     * @param due
     */
    public void setDue(String due) {
        this.due.set(due);
    }

    /**
     * Getter for Requested.
     *
     * @return
     */
    public String getRequested() {
        return requested.get();
    }

    /**
     * Setter for Requested.
     *
     * @param requested
     */
    public void setRequested(String requested) {
        this.requested.set(requested);
    }
}
