package model;

import java.time.LocalDate;

public class Transaction {
    public enum Type { BORROW, RETURN }

    private String transactionId;
    private String bookId;
    private String memberId;
    private Type type;
    private LocalDate date;
    private LocalDate dueDate;

    public Transaction(String transactionId, String bookId, String memberId,
                       Type type, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.type = type;
        this.date = LocalDate.now();
        this.dueDate = dueDate;
    }

    public String getTransactionId() { return transactionId; }
    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public Type getType() { return type; }
    public LocalDate getDate() { return date; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("| %-8s | %-6s | %-8s | %-6s | %-12s | %-12s |",
                transactionId, bookId, memberId, type, date, dueDate);
    }
}