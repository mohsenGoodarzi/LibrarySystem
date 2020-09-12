package Models.Resources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for the languages
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Language {

    private SimpleIntegerProperty languageId;
    private SimpleStringProperty name;

    /**
     * Creates a new language.
     *
     * @param languageId
     * @param name
     */
    public Language(int languageId, String name) {
        this.languageId = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
    }

    /**
     * Sets the ID for the Book resource
     *
     * @param languageID is the language ID
     */
    public void setLanguageID(int languageID) {
        this.languageId.set(languageID);
    }

    /**
     * Sets the language name for the language
     *
     * @param language is the name of the language
     */
    public void setLanguage(String language) {
        this.name.set(language);
    }
}
