/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Logs;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the methods and data for all logs
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class ViewLog {

    private SimpleStringProperty userName;
    private SimpleStringProperty date;
    private SimpleStringProperty logType;

    /**
     * Creates a new log
     *
     * @param userName the username of the user
     * @param date the date of the log
     * @param logType the type of log
     */
    public ViewLog(String userName, String date, String logType) {
        this.userName = new SimpleStringProperty(userName);
        this.date = new SimpleStringProperty(date);
        this.logType = new SimpleStringProperty(logType);
    }

    /**
     * Returns the username
     *
     * @return the username
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Sets the username of the log
     *
     * @param userName takes in the username of the user who interacted with the
     * resource
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    /**
     * Gets the date that the resource was interacted with
     *
     * @return the date that the resource was interacted with
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Sets the date that the resource was interacted with
     *
     * @param date the date that the resource was interacted with
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Gets the log type of the log
     *
     * @return the log type
     */
    public String getLogType() {
        return logType.get();
    }

    /**
     * Sets the log type of the log
     *
     * @param logType the log type
     */
    public void setLogType(String logType) {
        this.logType.set(logType);
    }

}
