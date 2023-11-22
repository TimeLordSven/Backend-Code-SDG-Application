package com.example.feedbacktoolbackend.service.models;

public class UserBusiness {
    private Long id;
    private String firstName;
    private String lastName;

    /**
     * Constructor for UserBusiness class.
     * @author Sven Molenaar
     * @param id        The ID of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     */
    public UserBusiness(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Retrieves the ID of the user.
     * @author Sven Molenaar
     * @return The ID of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * @author Sven Molenaar
     * @param id The ID of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the first name of the user.
     * @author Sven Molenaar
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * @author Sven Molenaar
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     * @author Sven Molenaar
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @author Sven Molenaar
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
