package com.serveo.app;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private User currentUser = null;

    public boolean register(String email, String pass, Role role) {
        if (email == null || email.isBlank() || pass == null || pass.isBlank()) return false;
        if (find(email) != null) return false; // no duplicados
        users.add(new User(email.trim().toLowerCase(), pass, role));
        return true;
    }

    public boolean login(String email, String pass) {
        User u = find(email);
        if (u != null && u.getPassword().equals(pass)) {
            currentUser = u;
            return true;
        }
        return false;
    }

    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }

    private User find(String email) {
        if (email == null) return null;
        String key = email.trim().toLowerCase();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(key)) return u;
        }
        return null;
    }
}
