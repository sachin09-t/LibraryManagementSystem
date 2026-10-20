package util;

import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Invalid number. Try again.");
            }
        }
    }

    public static String readEmail(String prompt) {
        while (true) {
            String email = readString(prompt);
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("  ✗ Invalid email format. Try again.");
        }
    }
}