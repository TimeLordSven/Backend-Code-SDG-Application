package com.example.feedbacktoolbackend.data.Models;
import jakarta.persistence.*;
/**
 * Represents a User entity, mapped to a database table.
 * This class defines the structure of a User and its mapping to the database.
 * @author Sven Molenaar
 */
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;
}
