package model;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;
    private String name;
    private String email;
    private String password;
    private List<String> borrowedBookIds;

    public Member(String memberId, String name, String email, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.borrowedBookIds = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<String> getBorrowedBookIds() { return borrowedBookIds; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public void addBorrowedBook(String bookId) { borrowedBookIds.add(bookId); }
    public void removeBorrowedBook(String bookId) { borrowedBookIds.remove(bookId); }

    @Override
    public String toString() {
        return String.format("| %-8s | %-20s | %-25s | %d book(s) |",
                memberId, name, email, borrowedBookIds.size());
    }
}