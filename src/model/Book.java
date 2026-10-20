package model;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    public Book(String bookId, String title, String author, String isbn,
                String genre, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // Setters for editable fields
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    // Business logic: borrow/return
    public boolean borrowCopy() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String toString() {
        return String.format("| %-6s | %-30s | %-20s | %-13s | %-12s | %d/%d |",
                bookId, title, author, isbn, genre, availableCopies, totalCopies);
    }
}