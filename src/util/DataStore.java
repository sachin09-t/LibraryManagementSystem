package util;

import model.Book;
import model.Member;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory data store. Swap with a real DB (MySQL, SQLite) for production.
 */
public class DataStore {
    public static final List<Book> books = new ArrayList<>();
    public static final List<Member> members = new ArrayList<>();
    public static final List<Transaction> transactions = new ArrayList<>();

    // Auto-increment counters
    public static int bookCounter = 1000;
    public static int memberCounter = 2000;
    public static int txnCounter = 3000;
}