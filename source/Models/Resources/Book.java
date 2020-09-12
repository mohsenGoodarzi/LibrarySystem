package Models.Resources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class holds the data and methods for the books
 *
 * @author Scott Ankin
 * @version 1.0
 */
public class Book extends Resource {

    private final double FINERATE = 2.00;
    private final double MAXIMUMFINE = 25.00;

    private SimpleIntegerProperty bookId;
    private SimpleStringProperty author;
    private SimpleStringProperty publisher;
    private SimpleIntegerProperty genreId;
    private SimpleLongProperty isbn;
    private SimpleIntegerProperty languageId;

    /**
     * Constructs a new book
     *
     * @param bookId The book ID
     * @param title The title of the book
     * @param releasedYear The release year of the book
     * @param imageLocation The image location of the books image
     * @param author The author of the book
     * @param publisher The publisher of the book
     * @param genreId The id of the genre of the book
     * @param isbn The ISBN number of the book
     * @param languageId The ID of the language of the book
     */
    public Book(int bookId, String title, int releasedYear, String imageLocation, String author, String publisher, int genreId, long isbn, int languageId) {
        super(bookId, title, releasedYear, imageLocation);
        this.bookId = new SimpleIntegerProperty(bookId);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.genreId = new SimpleIntegerProperty(genreId);
        this.isbn = new SimpleLongProperty(isbn);
        this.languageId = new SimpleIntegerProperty(languageId);
    }

    /**
     * Gets the Book ID of the author of the book
     *
     * @return The book ID
     */
    public int getBookId() {
        return bookId.get();
    }

    /**
     * Sets the ID for the Book resource
     *
     * @param bookId is the book's ID
     */
    public void setBookId(int bookId) {
        this.bookId.set(bookId);
    }

    /**
     * Returns the name of the author of the Laptop
     *
     * @return author The name of the author
     */
    public String getAuthor() {
        return author.get();
    }

    /**
     * Sets the Author for the Book resource
     *
     * @param author is the name of the book's author
     */
    public void setAuthor(String author) {
        this.author.set(author);
    }

    /**
     * Returns the name of the publisher of the Book
     *
     * @return The name of the publisher
     */
    public String getPublisher() {
        return publisher.get();
    }

    /**
     * Sets the publisher for the Book resource
     *
     * @param publisher is the Book's publisher
     */
    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    /**
     * Returns the genre id of the genre of the Book
     *
     * @return The genre id of the genre
     */
    public int getGenreId() {
        return genreId.get();
    }

    /**
     * Sets the genreID for the Book resource
     *
     * @param genreId genreId of the book.
     */
    public void setGenreId(int genreId) {
        this.genreId.set(genreId);
    }

    /**
     * Returns the name of the ISBN number of the Book
     *
     * @return The ISBN number of the book
     */
    public long getIsbn() {
        return isbn.get();
    }

    /**
     * Sets the ISBN number for the Book resource
     *
     * @param isbn is the Book's ISBN number
     */
    public void setIsbn(long isbn) {
        this.isbn.set(isbn);
    }

    /**
     * Returns the language of the Book
     *
     * @return The language of the book
     */
    public int getLanguageId() {
        return languageId.get();
    }

    /**
     * Sets the languageID for the Book resource
     *
     * @param languageId is the Book's language
     */
    public void setLanguage(int languageId) {
        this.languageId.set(languageId);
    }

    /**
     * Returns the fine rate of the book
     *
     * @return the fine rate
     */
    public double getFineRate() {
        return FINERATE;
    }

    /**
     * Returns the maximum fine a book can have
     *
     * @return the maximum fine
     */
    public double getMaximumFine() {
        return MAXIMUMFINE;
    }
}
