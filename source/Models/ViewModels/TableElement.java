package Models.ViewModels;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds data to be displayed in the rows of a table. Used in classes
 * where the Model of a Resource can't contain all the data that needs to be
 * displayed.
 *
 * @author Daniel Griffiths
 * @version 1.3
 */
public class TableElement {

    private SimpleStringProperty title;
    private SimpleStringProperty borrowed;
    private SimpleStringProperty due;
    private SimpleStringProperty requested;
    private SimpleStringProperty date;
    private SimpleStringProperty userId;
    private SimpleStringProperty transaction;

    /**
     * Create a row for a table that holds four pieces of data.
     *
     * @param title The title of the resource.
     * @param userId The ID of a user.
     * @param date The date at which a change to a resource has occurred.
     * @param due The due date of a resource.
     */
    public TableElement(String title, String userId, String date, String due) {
        this.title = new SimpleStringProperty(title);
        this.userId = new SimpleStringProperty(userId);
        this.date = new SimpleStringProperty(date);
        this.due = new SimpleStringProperty(due);
    }

    /**
     * Create a row for a table that holds two pieces of data.
     *
     * @param title The title of a resource.
     * @param requested The date the resource was requested.
     */
    public TableElement(String title, String requested) {
        this.title = new SimpleStringProperty(title);
        this.requested = new SimpleStringProperty(requested);
    }

    /**
     * Create a row for a table that holds three pieces of data.
     *
     * @param title The title of a resource.
     * @param borrowed The borrow date of a resource.
     * @param due The due date of a resource.
     */
    public TableElement(String title, String borrowed, String due) {
        this.title = new SimpleStringProperty(title);
        this.borrowed = new SimpleStringProperty(borrowed);
        this.due = new SimpleStringProperty(due);
    }

    /**
     * Create a row for a table that holds only a transaction.
     *
     * @param transaction A transaction.
     */
    public TableElement(String transaction) {
        this.transaction = new SimpleStringProperty(transaction);
    }

    /**
     * Get the title.
     *
     * @return The title.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Set the title.
     *
     * @param title The title.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Get the borrow date.
     *
     * @return The Borrow date.
     */
    public String getBorrowed() {
        return borrowed.get();
    }

    /**
     * Set the borrow date.
     *
     * @param borrowed The Borrow date.
     */
    public void setBorrowed(String borrowed) {
        this.borrowed.set(borrowed);
    }

    /**
     * Get the due date
     *
     * @return The due date.
     */
    public String getDue() {
        return due.get();
    }

    /**
     * Set the due date
     *
     * @param due The due date.
     */
    public void setDue(String due) {
        this.due.set(due);
    }

    /**
     * Get the request date.
     *
     * @return The request date.
     */
    public String getRequested() {
        return requested.get();
    }

    /**
     * Set the request date.
     *
     * @param requested The request date.
     */
    public void setRequested(String requested) {
        this.requested.set(requested);
    }

    /**
     * Get the user ID.
     *
     * @return The user ID.
     */
    public String getUserId() {
        return userId.get();
    }

    /**
     * Set the user ID.
     *
     * @param userId The user ID.
     */
    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    /**
     * Get the change date.
     *
     * @return The change date.
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Set the change date.
     *
     * @param date The change date.
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Get the transaction.
     *
     * @return The transaction.
     */
    public String getTransaction() {
        return this.transaction.get();
    }

    /**
     * Set the transaction.
     *
     * @param transaction The transaction.
     */
    public void setTransaction(String transaction) {
        this.transaction.set(transaction);
    }

}
