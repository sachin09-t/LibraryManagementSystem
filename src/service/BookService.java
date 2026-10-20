package service;

import model.Book;
import util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    public Book addBook(String title, String author, String isbn,
                        String genre, int copies) {
        String id = "B" + (++DataStore.bookCounter);
        Book book = new Book(id, title, author, isbn, genre, copies);
        DataStore.books.add(book);
        return book;
    }

    public boolean updateBook(String bookId, String title, String author,
                              String isbn, String genre, int copies) {
        Book book = findById(bookId);
        if (book == null) return false;
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setTotalCopies(copies);
        return true;
    }

    public boolean deleteBook(String bookId) {
        Book book = findById(bookId);
        if (book == null) return false;
        DataStore.books.remove(book);
        return true;
    }

    public Book findById(String bookId) {
        return DataStore.books.stream()
                .filter(b -> b.getBookId().equalsIgnoreCase(bookId))
                .findFirst().orElse(null);
    }

    public List<Book> search(String keyword) {
        String k = keyword.toLowerCase();
        return DataStore.books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(k)
                        || b.getAuthor().toLowerCase().contains(k)
                        || b.getGenre().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return DataStore.books;
    }
}