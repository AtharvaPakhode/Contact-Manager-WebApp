package com.yourcontacts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User entity in the system.
 * This class is mapped to the "user_table" in the database.
 * It contains information about the user, including personal details, role, and associated contacts.
 */
@Entity
@Table(name = "user_table")
public class User {

    // Unique identifier for the user
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    // User's name with validation for minimum and maximum length
    @NotBlank(message = "Name should not be blank") // Error message if blank
    @Size(min = 2, max = 20, message = "Minimum 2 and maximum 20 characters are allowed")
    private String user_name;

    // User's email, must be unique and valid
    @Column(unique = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String user_email;

    // User's password (stored in plain text here, should be hashed in a real app)
    private String user_password;

    // User's role (e.g., ADMIN, USER, etc.)
    private String user_role;

    // URL for the user's profile image
    private String image_URL;

    // Description or about section for the user with a maximum length of 500 characters
    @Column(length = 500)
    private String about_user;

    // Status indicating whether the user is active or not
    private boolean status;

    // List of contacts associated with the user (one-to-many relationship)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contacts> contact_list = new ArrayList<>();

    // Getters and Setters

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getImageURL() {
        return image_URL;
    }

    public void setImageURL(String imageURL) {
        this.image_URL = imageURL;
    }

    public String getAbout_user() {
        return about_user;
    }

    public void setAbout_user(String about_user) {
        this.about_user = about_user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Contacts> getContact_list() {
        return contact_list;
    }

    public void setContact_list(List<Contacts> contact_list) {
        this.contact_list = contact_list;
    }

    // Constructors

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor to initialize all fields of the User class.
     *
     * @param user_id The unique identifier for the user.
     * @param contact_list List of contacts associated with the user.
     * @param status User's status (active or inactive).
     * @param about_user Description about the user.
     * @param imageURL URL for the user's profile image.
     * @param user_role The role of the user (e.g., Admin, User).
     * @param user_password The user's password.
     * @param user_email The user's email address.
     * @param user_name The name of the user.
     */
    public User(int user_id, List<Contacts> contact_list, boolean status, String about_user, String imageURL, String user_role, String user_password, String user_email, String user_name) {
        this.user_id = user_id;
        this.contact_list = contact_list;
        this.status = status;
        this.about_user = about_user;
        this.image_URL = imageURL;
        this.user_role = user_role;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_name = user_name;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string containing the user's details.
     */
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_role='" + user_role + '\'' +
                ", image_URL='" + image_URL + '\'' +
                ", about_user='" + about_user + '\'' +
                ", status=" + status +
                ", contact_list=" + contact_list +
                '}';
    }
}
