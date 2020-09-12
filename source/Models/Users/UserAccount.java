package Models.Users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for the user account.
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class UserAccount {

    private SimpleIntegerProperty userId;
    private SimpleStringProperty userName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleLongProperty mobilePhoneNumber;
    private SimpleStringProperty addressLineOne;
    private SimpleStringProperty addressLineTwo;
    private SimpleStringProperty postCode;
    private SimpleStringProperty profileImageLocation;

    /**
     * Create a new user account
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
     */
    public UserAccount(int userId, String userName, String firstName, String lastName, long mobilePhoneNumber, String addressLineOne, String addressLineTwo, String postCode, String profileImageLocation) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.mobilePhoneNumber = new SimpleLongProperty(mobilePhoneNumber);
        this.addressLineOne = new SimpleStringProperty(addressLineOne);
        this.addressLineTwo = new SimpleStringProperty(addressLineTwo);
        this.postCode = new SimpleStringProperty(postCode);
        this.profileImageLocation = new SimpleStringProperty(profileImageLocation);
    }

    /**
     * Returns the Username
     *
     * @return the Username
     */
    public String getUserName() {
        return userName.get();
    }

    /**
     * Sets the username for the user
     *
     * @param userName is the user's username
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    /**
     * Returns the first name
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets the first name for the user
     *
     * @param firstName is the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Returns the last name
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets the last name for the user
     *
     * @param lastName is the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Returns the mobile phone number
     *
     * @return the mobilePhoneNumber
     */
    public long getMobilePhoneNumber() {
        return mobilePhoneNumber.get();
    }

    /**
     * Sets the mobile phone number for the user
     *
     * @param mobilePhoneNumber is the user's mobile phone number
     */
    public void setMobilePhoneNumber(long mobilePhoneNumber) {
        this.mobilePhoneNumber.set(mobilePhoneNumber);
    }

    /**
     * Returns address line one
     *
     * @return the addressLineOne
     */
    public String getAddressLineOne() {
        return addressLineOne.get();
    }

    /**
     * Sets the first address line for the user
     *
     * @param addressLineOne is the user's first address line
     */
    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne.set(addressLineOne);
    }

    /**
     * Returns the address line two
     *
     * @return the addressLineTwo
     */
    public String getAddressLineTwo() {
        return addressLineTwo.get();
    }

    /**
     * Sets the second address line for the user
     *
     * @param addressLineTwo is the user's second address line
     */
    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo.set(addressLineTwo);
    }

    /**
     * Returns the post code
     *
     * @return the postCode
     */
    public String getPostCode() {
        return postCode.get();
    }

    /**
     * Sets the post code for the user
     *
     * @param postCode is the user's post code
     */
    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
    }

    /**
     * Returns the profile image location
     *
     * @return the profileImageLocation
     */
    public String getProfileImageLocation() {
        return profileImageLocation.get();
    }

    /**
     * Sets the profile image location for the user
     *
     * @param profileImageLocation is the location of the user's profile image
     */
    public void setProfileImageLocation(String profileImageLocation) {
        this.profileImageLocation.set(profileImageLocation);
    }

    /**
     * Returns the user ID
     *
     * @return userId the user's id
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Sets the user's ID
     *
     * @param userId user's ID
     */
    public void setUserId(int userId) {
        this.userId.set(userId);
    }
}
