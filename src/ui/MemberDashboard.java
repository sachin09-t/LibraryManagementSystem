package ui;

import model.Book;
import model.Member;
import model.Transaction;
import service.BookService;
import service.MemberService;
import service.TransactionService;
import util.InputValidator;

public class MemberDashboard {
    private final BookService bookService = new BookService();
    private final MemberService memberService = new MemberService();
    private final TransactionService txnService = new TransactionService();

    private Member currentMember;

    public void register() {
        System.out.println("\n--- Member Registration ---");
        String name = InputValidator.readString("Name: ");
        String email = InputValidator.readEmail("Email: ");
        String pass = InputValidator.readString("Password: ");

        Member m = memberService.register(name, email, pass);
        if (m == null) {
            System.out.println("  ✗ Email already registered.");
        } else {
            System.out.println("  ✓ Registered! Member ID: " + m.getMemberId());
        }
    }

    public void login() {
        System.out.println("\n--- Member Login ---");
        String email = InputValidator.readString("Email: ");
        String pass = InputValidator.readString("Password: ");
        Member m = memberService.authenticate(email, pass);
        if (m == null) {
            System.out.println("  ✗ Invalid credentials.");
            return;
        }
        currentMember = m;
        System.out.println("  ✓ Welcome, " + m.getName() + "!");
        dashboard();
    }

    private void dashboard() {
        while (true) {
            System.out.println("\n=== MEMBER DASHBOARD (" + currentMember.getMemberId() + ") ===");
            System.out.println("1. Search Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. My Borrowing History");
            System.out.println("5. My Current Books");
            System.out.println("6. Update Profile");
            System.out.println("0. Logout");

            int choice = InputValidator.readInt("Choice: ");
            switch (choice) {
                case 1 -> searchBooks();
                case 2 -> borrow();
                case 3 -> returnBook();
                case 4 -> history();
                case 5 -> currentBooks();
                case 6 -> updateProfile();
                case 0 -> { currentMember = null; return; }
                default -> System.out.println("  ✗ Invalid.");
            }
        }
    }

    private void searchBooks() {
        String kw = InputValidator.readString("Search keyword (title/author/genre): ");
        var results = bookService.search(kw);
        if (results.isEmpty()) {
            System.out.println("  No results.");
            return;
        }
        System.out.printf("| %-6s | %-30s | %-20s | %-13s | %-12s | %s |%n",
                "ID", "Title", "Author", "ISBN", "Genre", "Copies");
        System.out.println("-".repeat(110));
        for (Book b : results) System.out.println(b);
    }

    private void borrow() {
        String bookId = InputValidator.readString("Enter Book ID to borrow: ");
        Transaction t = txnService.borrowBook(bookId, currentMember.getMemberId());
        if (t == null) System.out.println("  ✗ Cannot borrow. Unavailable or invalid ID.");
        else System.out.println("  ✓ Borrowed! Due: " + t.getDueDate());
    }

    private void returnBook() {
        String bookId = InputValidator.readString("Enter Book ID to return: ");
        Transaction t = txnService.returnBook(bookId, currentMember.getMemberId());
        System.out.println(t != null ? "  ✓ Returned." : "  ✗ Return failed.");
    }

    private void history() {
        var txns = txnService.getHistoryForMember(currentMember.getMemberId());
        if (txns.isEmpty()) { System.out.println("  No history yet."); return; }
        System.out.printf("| %-8s | %-6s | %-8s | %-6s | %-12s | %-12s |%n",
                "TXN ID", "Book", "Member", "Type", "Date", "Due");
        System.out.println("-".repeat(78));
        for (Transaction t : txns) System.out.println(t);
    }

    private void currentBooks() {
        var ids = currentMember.getBorrowedBookIds();
        if (ids.isEmpty()) { System.out.println("  No books borrowed."); return; }
        System.out.println("Currently borrowed:");
        for (String id : ids) {
            Book b = bookService.findById(id);
            if (b != null) System.out.println("  - " + b.getBookId() + " : " + b.getTitle());
        }
    }

    private void updateProfile() {
        String name = InputValidator.readString("New Name: ");
        String email = InputValidator.readEmail("New Email: ");
        String pass = InputValidator.readString("New Password: ");
        memberService.updateMember(currentMember.getMemberId(), name, email, pass);
        System.out.println("  ✓ Profile updated.");
    }
}