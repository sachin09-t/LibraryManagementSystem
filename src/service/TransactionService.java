package service;

import model.Book;
import model.Member;
import model.Transaction;
import util.DataStore;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private static final int LOAN_DAYS = 14;

    private final BookService bookService = new BookService();
    private final MemberService memberService = new MemberService();

    public Transaction borrowBook(String bookId, String memberId) {
        Book book = bookService.findById(bookId);
        Member member = memberService.findById(memberId);

        if (book == null || member == null) return null;
        if (!book.borrowCopy()) return null;

        String txnId = "T" + (++DataStore.txnCounter);
        Transaction txn = new Transaction(txnId, bookId, memberId,
                Transaction.Type.BORROW, LocalDate.now().plusDays(LOAN_DAYS));
        DataStore.transactions.add(txn);
        member.addBorrowedBook(bookId);
        return txn;
    }

    public Transaction returnBook(String bookId, String memberId) {
        Book book = bookService.findById(bookId);
        Member member = memberService.findById(memberId);

        if (book == null || member == null) return null;
        if (!member.getBorrowedBookIds().contains(bookId)) return null;

        book.returnCopy();
        member.removeBorrowedBook(bookId);

        String txnId = "T" + (++DataStore.txnCounter);
        Transaction txn = new Transaction(txnId, bookId, memberId,
                Transaction.Type.RETURN, LocalDate.now());
        DataStore.transactions.add(txn);
        return txn;
    }

    public List<Transaction> getHistoryForMember(String memberId) {
        return DataStore.transactions.stream()
                .filter(t -> t.getMemberId().equalsIgnoreCase(memberId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() {
        return DataStore.transactions;
    }

    public long countBorrows() {
        return DataStore.transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.BORROW).count();
    }

    public long countReturns() {
        return DataStore.transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.RETURN).count();
    }
}