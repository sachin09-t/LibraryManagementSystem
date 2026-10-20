package ui;

import model.Book;
import model.Member;
import model.Transaction;
import service.BookService;
import service.MemberService;
import service.TransactionService;
import util.InputValidator;

public class LibrarianDashboard {
    private final BookService bookService = new BookService();
    private final MemberService memberService = new MemberService();
    private final TransactionService txnService = new TransactionService();

    // Simple hard-coded credential for demo. Replace with auth service.
    private static final String LIB_EMAIL = "admin@library.com";
    private static final String LIB_PASSWORD = "admin123";

    public void login() {
        System.out.println("\n--- Librarian Login ---");
        String email = InputValidator.readString("Email: ");
        String pass = InputValidator.readString("Password: ");
        if (!email.equals(LIB_EMAIL) || !pass.equals(LIB_PASSWORD)) {
            System.out.println("  ✗ Invalid credentials.");
            return;
        }
        System.out.println("  ✓ Welcome, Librarian!");
        dashboard();
    }

    private void dashboard() {
        while (true) {
            System.out.println("\n=== LIBRARIAN DASHBOARD ===");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Issue Book (Borrow)");
            System.out.println("4. Return Book");
            System.out.println("5. View All Transactions");
            System.out.println("6. Inventory Reports");
            System.out.println("0. Logout");

            int choice = InputValidator.readInt("Choice: ");
            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageMembers();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> viewTransactions();
                case 6 -> inventoryReport();
                case 0 -> { return; }
                default -> System.out.println("  ✗ Invalid.");
            }
        }
    }

    private void manageBooks() {
        System.out.println("\n-- Book Management --");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. List All Books");
        System.out.println("5. Search Books");
        int c = InputValidator.readInt("Choice: ");

        switch (c) {
            case 1 -> {
                String title = InputValidator.readString("Title: ");
                String author = InputValidator.readString("Author: ");
                String isbn = InputValidator.readString("ISBN: ");
                String genre = InputValidator.readString("Genre: ");
                int copies = InputValidator.readInt("Copies: ");
                Book b = bookService.addBook(title, author, isbn, genre, copies);
                System.out.println("  ✓ Book added with ID: " + b.getBookId());
            }
            case 2 -> {
                String id = InputValidator.readString("Book ID: ");
                Book existing = bookService.findById(id);
                if (existing == null) { System.out.println("  ✗ Not found."); return; }
                String title = InputValidator.readString("New Title: ");
                String author = InputValidator.readString("New Author: ");
                String isbn = InputValidator.readString("New ISBN: ");
                String genre = InputValidator.readString("New Genre: ");
                int copies = InputValidator.readInt("New Total Copies: ");
                bookService.updateBook(id, title, author, isbn, genre, copies);
                System.out.println("  ✓ Book updated.");
            }
            case 3 -> {
                String id = InputValidator.readString("Book ID: ");
                System.out.println(bookService.deleteBook(id)
                        ? "  ✓ Book deleted." : "  ✗ Not found.");
            }
            case 4 -> printBooks(bookService.getAllBooks());
            case 5 -> {
                String kw = InputValidator.readString("Search keyword: ");
                printBooks(bookService.search(kw));
            }
        }
    }

    private void manageMembers() {
        System.out.println("\n-- Member Management --");
        System.out.println("1. List Members");
        System.out.println("2. Update Member");
        int c = InputValidator.readInt("Choice: ");

        if (c == 1) {
            System.out.printf("| %-8s | %-20s | %-25s | %-12s |%n",
                    "ID", "Name", "Email", "Borrowed");
            System.out.println("-".repeat(78));
            for (Member m : util.DataStore.members) System.out.println(m);
        } else if (c == 2) {
            String id = InputValidator.readString("Member ID: ");
            Member existing = memberService.findById(id);
            if (existing == null) { System.out.println("  ✗ Not found."); return; }
            String name = InputValidator.readString("New Name: ");
            String email = InputValidator.readEmail("New Email: ");
            String pass = InputValidator.readString("New Password: ");
            memberService.updateMember(id, name, email, pass);
            System.out.println("  ✓ Member updated.");
        }
    }

    private void issueBook() {
        String bookId = InputValidator.readString("Book ID: ");
        String memberId = InputValidator.readString("Member ID: ");
        Transaction t = txnService.borrowBook(bookId, memberId);
        if (t == null) {
            System.out.println("  ✗ Cannot issue. Check IDs / availability.");
        } else {
            System.out.println("  ✓ Issued! Due on: " + t.getDueDate());
        }
    }

    private void returnBook() {
        String bookId = InputValidator.readString("Book ID: ");
        String memberId = InputValidator.readString("Member ID: ");
        Transaction t = txnService.returnBook(bookId, memberId);
        System.out.println(t != null ? "  ✓ Returned successfully." : "  ✗ Return failed.");
    }

    private void viewTransactions() {
        System.out.printf("| %-8s | %-6s | %-8s | %-6s | %-12s | %-12s |%n",
                "TXN ID", "Book", "Member", "Type", "Date", "Due");
        System.out.println("-".repeat(78));
        for (Transaction t : txnService.getAllTransactions()) System.out.println(t);
    }

    private void inventoryReport() {
        System.out.println("\n=== INVENTORY REPORT ===");
        System.out.println("Total books:       " + bookService.getAllBooks().size());
        long totalCopies = bookService.getAllBooks().stream()
                .mapToInt(Book::getTotalCopies).sum();
        long availableCopies = bookService.getAllBooks().stream()
                .mapToInt(Book::getAvailableCopies).sum();
        System.out.println("Total copies:      " + totalCopies);
        System.out.println("Available copies:  " + availableCopies);
        System.out.println("Borrowed copies:   " + (totalCopies - availableCopies));
        System.out.println("Total members:     " + util.DataStore.members.size());
        System.out.println("Total borrows:     " + txnService.countBorrows());
        System.out.println("Total returns:     " + txnService.countReturns());
    }

    private void printBooks(java.util.List<Book> books) {
        System.out.printf("| %-6s | %-30s | %-20s | %-13s | %-12s | %s |%n",
                "ID", "Title", "Author", "ISBN", "Genre", "Copies");
        System.out.println("-".repeat(110));
        for (Book b : books) System.out.println(b);
    }
}