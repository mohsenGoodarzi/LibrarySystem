package Models.Resources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for genre
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Genre {

    private SimpleIntegerProperty genreId;
    private SimpleStringProperty name;

    /**
     * Creates the specific Genre.
     * @param genreId
     * @param name
     */
    public Genre(int genreId, String name) {
        this.genreId = new SimpleIntegerProperty(genreId);
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Creates a new genre
     *
     * @return int as the id.
     */
    public int getGenreId() {
        return this.genreId.get();
    }

    /**
     * Sets the ID for the genre
     *
     * @param genreId is the genre's ID
     */
    public void setGenreId(int genreId) {
        this.genreId.set(genreId);
    }

    /**
     * Sets the genre name for the genre
     *
     * @param name is the name of the genre
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns the name of the genre
     *
     * @return the name of the genre which type is string
     */
    public String getName() {
        return name.get();
    }

}
