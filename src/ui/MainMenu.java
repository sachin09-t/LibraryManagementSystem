package ui;

import util.InputValidator;

public class MainMenu {
    private final LibrarianDashboard librarianDashboard = new LibrarianDashboard();
    private final MemberDashboard memberDashboard = new MemberDashboard();

    public void start() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   ONLINE LIBRARY MANAGEMENT SYSTEM     ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Librarian Login                    ║");
            System.out.println("║  2. Member Login                       ║");
            System.out.println("║  3. Member Registration                ║");
            System.out.println("║  0. Exit                               ║");
            System.out.println("╚════════════════════════════════════════╝");

            int choice = InputValidator.readInt("Enter choice: ");
            switch (choice) {
                case 1 -> librarianDashboard.login();
                case 2 -> memberDashboard.login();
                case 3 -> memberDashboard.register();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("  ✗ Invalid choice.");
            }
        }
    }
}