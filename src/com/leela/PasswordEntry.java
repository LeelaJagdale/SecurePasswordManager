package com.leela;
import java.io.Serializable;
import java.util.Objects;

public class PasswordEntry implements Serializable {
    // Fixed serialVersionUID to prevent compatibility errors
    private static final long serialVersionUID = 498723541987234L;  // Can be any long value
    
    private final String website;
    private final String username;
    private final String password;

    public PasswordEntry(String website, String username, String password) {
        this.website = Objects.requireNonNull(website, "Website cannot be null");
        this.username = Objects.requireNonNull(username, "Username cannot be null");
        this.password = Objects.requireNonNull(password, "Password cannot be null");
    }

    // Getters
    public String getWebsite() { 
        return website; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public String getPassword() { 
        return password; 
    }

    @Override
    public String toString() {
        return String.format(
            "Website: %s\nUsername: %s\nPassword: %s",
            website,
            username,
            "********"  // Mask password in logs/output
        );
    }

    // Additional security measures
    public String getMaskedPassword() {
        return "********"; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordEntry that = (PasswordEntry) o;
        return website.equals(that.website) && 
               username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(website, username);
    }
}
