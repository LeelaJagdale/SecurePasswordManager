package com.leela;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Secure Password Manager ===");
        String masterPassword = getMasterPassword();
        PasswordManager manager = new PasswordManager(masterPassword);

        while (true) {
            printMenu();
            int choice = getIntInput("Choose an option: ", 1, 3);

            switch (choice) {
                case 1 -> addPassword(manager);
                case 2 -> viewPasswords(manager);
                case 3 -> exitProgram();
            }
        }
    }

    private static String getMasterPassword() {
        while (true) {
            System.out.print("Set/Enter Master Password (min 8 characters): ");
            String password = scanner.nextLine().trim();
            
            if (password.length() >= 8) {
                return password;
            }
            System.out.println("Password must be at least 8 characters!");
        }
    }

    private static void printMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add Password");
        System.out.println("2. View Passwords");
        System.out.println("3. Exit");
    }

    private static void addPassword(PasswordManager manager) {
        System.out.println("\nAdd New Password:");
        String website = getNonEmptyInput("Website: ");
        String username = getNonEmptyInput("Username: ");
        String password = getNonEmptyInput("Password: ");
        
        manager.addEntry(website, username, password);
        System.out.println("✓ Password saved successfully!");
    }

    private static void viewPasswords(PasswordManager manager) {
        List<PasswordEntry> entries = manager.getEntries();
        
        if (entries.isEmpty()) {
            System.out.println("\nNo passwords stored yet.");
            return;
        }
        
        System.out.println("\nStored Passwords:");
        System.out.println("-----------------");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println((i + 1) + ". " + entries.get(i));
            System.out.println("-----------------");
        }
    }

    private static void exitProgram() {
        System.out.println("\nExiting Password Manager... Goodbye!");
        scanner.close();
        System.exit(0);
    }

    private static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty!");
        }
    }

    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.printf("Please enter a number between %d and %d\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}