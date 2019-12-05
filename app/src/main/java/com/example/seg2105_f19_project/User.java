package com.example.seg2105_f19_project;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String passHash;
    private String role;

    public User (String firstName, String lastName, String username, String passHash, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passHash = passHash;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassHash() {
        return passHash;
    }

    public String getRole() {
        return role;
    }
}
