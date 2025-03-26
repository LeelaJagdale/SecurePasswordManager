package com.leela;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private static final String DATA_FILE = "passwords.enc";
    private static final String TEMP_FILE = "temp.tmp";
    private List<PasswordEntry> entries;
    private final String masterPassword;

    public PasswordManager(String masterPassword) {
        this.masterPassword = masterPassword;
        this.entries = new ArrayList<>();
        loadEntries();
    }

    public void addEntry(String website, String username, String password) {
        entries.add(new PasswordEntry(website, username, password));
        saveEntries();
    }

    public List<PasswordEntry> getEntries() {
        return new ArrayList<>(entries); // Return copy for safety
    }

    private void saveEntries() {
        File tempFile = new File(TEMP_FILE);
        try {
            // Write to temporary file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(entries);
            }

            // Encrypt the temporary file
            CryptoUtils.encrypt(masterPassword, tempFile, new File(DATA_FILE));

        } catch (Exception e) {
            System.err.println("Error saving passwords: " + e.getMessage());
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private void loadEntries() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println("No existing password database found. Creating new one.");
            return;
        }

        File tempFile = new File(TEMP_FILE);
        try {
            // Decrypt to temporary file
            CryptoUtils.decrypt(masterPassword, dataFile, tempFile);

            // Read from temporary file
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
                entries = (List<PasswordEntry>) ois.readObject();
            }

        } catch (Exception e) {
            System.err.println("Error loading passwords: " + e.getMessage());
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}