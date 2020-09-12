package Models.Users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class holds additional data and methods for users who are librarians.
 *
 * @author Daniel Griffiths
 * @version 1.0
 */
public class LibrarianAccount extends UserAccount {

    private SimpleIntegerProperty staffNumber;
    private SimpleStringProperty employmentDate;

    /**
     * Create an account for a librarian.
     *
     * @param userId
     * @param userName
     * @param firstName
     * @param lastName
     * @param mobilePhoneNumber
     * @param addressLineOne
     * @param addressLineTwo
     * @param postCode
     * @param profileImageLocation
     * @param employmentDate
     */
    public LibrarianAccount(int userId, String userName, String firstName, String lastName, long mobilePhoneNumber, String addressLineOne, String addressLineTwo, String postCode, String profileImageLocation, Date employmentDate) {
        super(userId, userName, firstName, lastName, mobilePhoneNumber, addressLineOne, addressLineTwo, postCode, profileImageLocation);
        this.staffNumber = new SimpleIntegerProperty(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(employmentDate.clone());
        this.employmentDate = new SimpleStringProperty(result);
    }

    /**
     * Gets the staff number associated with a librarian.
     *
     * @return The staff number
     */
    public int getStaffNumber() {
        return staffNumber.get();
    }

    /**
     * Sets the staff number associated with a librarian.
     *
     * @param staffNumber The staff number
     */
    public void setStaffNumber(int staffNumber) {
        this.staffNumber.set(staffNumber);
    }

    /**
     * Gets the date on which the librarian was employed.
     *
     * @return The date
     */
    public Date getEmploymentDate() {
        Date result = new Date();
        try {
            result = new SimpleDateFormat("dd/MM/yyyy").parse(employmentDate.get());
        } catch (ParseException e) {
            System.out.println("the Employment Date of LibrarianAccount cannot be returnned.");
        }
        return result;
    }

    /**
     * Gets the date on which the librarian was employed.
     *
     * @param employmentDate The date
     */
    public void setEmploymentDate(Date employmentDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String result = simpleDateFormat.format(employmentDate.clone());
        this.employmentDate.set(result);
    }
}
