package com.example.feedbacktoolbackend.service.models;

import com.example.feedbacktoolbackend.enums.Role;

public class UserBusiness {
    private Long id;
    private String firstName;
    private String prefixes;
    private String lastName;
    private String email;
    private final String password;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public UserBusiness(String firstName, String prefixes, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.prefixes = prefixes;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserBusiness(Long id, String firstName, String prefixes, String lastName, String email, String password, Role role) {
        this(firstName, prefixes, lastName, email, password, role);
        this.id = id;
    }

    /**
     * Checks if the stored email is valid using a regex pattern.
     *
     * @return True if the email is valid, false otherwise
     * @author Sven Molenaar
     */
    public boolean hasValidEmail() {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public boolean hasRole(Role expectedRole) {
        return this.role.equals(expectedRole);
    }

}