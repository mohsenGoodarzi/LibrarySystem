package Models.Logs;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class stores logs of fines for late resources.
 *
 * @author Daniel Griffiths
 * @version 1.0
 */
public class FineLog {

    private SimpleIntegerProperty UserId;
    private SimpleDoubleProperty fineAmount;

    /**
     * Create a new log for a fine.
     *
     * @param UserId userId
     * @param fineAmount amount of fine.
     */
    public FineLog(int UserId, double fineAmount) {
        this.UserId = new SimpleIntegerProperty(UserId);
        this.fineAmount = new SimpleDoubleProperty(fineAmount);
    }

    /**
     * Gets the ID of the user who has been fined.
     *
     * @return The ID of the user
     */
    public int getUserId() {
        return UserId.get();
    }

    /**
     * Sets the ID of the user who has been fined.
     *
     * @param userId The ID of the user
     */
    public void setUserId(int userId) {
        this.UserId.set(userId);
    }

    /**
     * Gets the amount of the fine.
     *
     * @return The amount of the fine
     */
    public double getFineAmount() {
        return fineAmount.get();
    }

    /**
     * Sets the amount of the fine.
     *
     * @param fineAmount The amount of the fine
     */
    public void setFineAmount(int fineAmount) {
        this.fineAmount.set(fineAmount);
    }
}
