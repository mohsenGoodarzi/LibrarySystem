package Models.Resources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for DVD
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Dvd extends Resource {

    private final double FINERATE = 2.00;
    private final double MAXIMUMFINE = 25.00;

    private SimpleIntegerProperty dvdId;
    private SimpleStringProperty director;
    private SimpleIntegerProperty runtime;
    private SimpleIntegerProperty languageId;
    private SimpleIntegerProperty subtitleId;

    /**
     * Constructs a new DVD object
     *
     * @param dvdId The DVD's ID
     * @param title The title of the DVD
     * @param releasedYear The release year of the DVD
     * @param imageLocation The image location of the DVD's image
     * @param director The director of the DVD
     * @param runtime The runtime of the DVD
     * @param languageId The language ID of the DVD's language
     * @param subtitleId The language ID of the DVD's subtitle language
     */
    public Dvd(int dvdId, String title, int releasedYear, String imageLocation, String director, int runtime, int languageId, int subtitleId) {
        super(dvdId, title, releasedYear, imageLocation);
        this.dvdId = new SimpleIntegerProperty(dvdId);
        this.director = new SimpleStringProperty(director);
        this.runtime = new SimpleIntegerProperty(runtime);
        this.languageId = new SimpleIntegerProperty(languageId);
        this.subtitleId = new SimpleIntegerProperty(subtitleId);
    }

    /**
     * Returns the DVD's ID
     *
     * @return The DVD's ID
     */
    public int getDvdId() {
        return dvdId.get();
    }

    /**
     * Sets the DVD's ID
     *
     * @param dvdId the ID of the DVD
     */
    public void setDvdId(int dvdId) {
        this.dvdId.set(dvdId);
    }

    /**
     * Gets the director of the DVD
     *
     * @return The director of the DVD
     */
    public String getDirector() {
        return director.get();
    }

    /**
     * Sets the director of the DVD
     *
     * @param director The director of the DVD
     */
    public void setDirector(String director) {
        this.director.set(director);
    }

    /**
     * Returns the name of the runtime of the DVD
     *
     * @return The runtime
     */
    public int getRuntime() {
        return runtime.get();
    }

    /**
     * Sets the runtime for the DVD resource
     *
     * @param runtime is the DVD's runtime length
     */
    public void setRuntime(int runtime) {
        this.runtime.set(runtime);
    }

    /**
     * Returns the name of the language of the DVD
     *
     * @return The language
     */
    public int getLanguageId() {
        return languageId.get();
    }

    /**
     * Sets the languageId for the DVD resource
     *
     * @param languageId is the DVD's runtime length
     */
    public void setLanguage(int languageId) {
        this.languageId.set(languageId);
    }

    /**
     * Returns the name of the subtitle languages of the DVD
     *
     * @return The subtitle languages
     */
    public int getSubtitleId() {
        return subtitleId.get();
    }

    /**
     * Sets the subtitle language for the DVD resource
     *
     * @param subtitleId is the DVD's subtitle languages
     */
    public void setSubtitle(int subtitleId) {
        this.subtitleId.set(subtitleId);
    }

    /**
     * Returns the fine rate of the DVD
     *
     * @return the fine rate
     */
    public double getFineRate() {
        return FINERATE;
    }

    /**
     * Returns the maximum fine a DVD can have
     *
     * @return the maximum fine
     */
    public double getMaximunFine() {
        return MAXIMUMFINE;
    }
}
