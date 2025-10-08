package com.serveo.app;

public class User {
    private final String email;
    private final String password; // simple (texto plano) para la etapa del curso
    private final Role role;

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    @Override
    public String toString() {
        return email + " [" + role + "]";
    }
}
