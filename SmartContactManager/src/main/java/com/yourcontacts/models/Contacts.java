package com.yourcontacts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Represents a contact entity in the system.
 * This class is mapped to the "contact_table" in the database.
 * It contains information such as contact details, nickname, work, image, and the associated user.
 */
@Entity
@Table(name = "contact_table")
public class Contacts {

    // Unique identifier for the contact
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contact_id;

    // Contact name with validation for blank and size
    @NotBlank(message = "Contact name is required")
    @Size(min = 3, max = 100, message = "Contact name must be between 3 and 100 characters")
    private String contact_name;

    // Contact email with validation for blank and proper email format
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String contact_email;

    // Phone number with validation for exactly 10 digits
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone_number;

    // Nickname with validation for length between 2 and 50 characters
    @Size(min = 2, max = 50, message = "Nickname should be between 2 and 50 characters")
    private String contact_nickname;

    // Contact's work or occupation with validation for length between 2 and 100 characters
    @Size(min = 2, max = 100, message = "Work should be between 2 and 100 characters")
    private String contact_work;

    // Contact image URL, handled in controller for file upload
    private String contact_image;

    // Association with the User entity (Many contacts can belong to one user)
    @ManyToOne
    private User user;

    // Description about the contact with length validation between 10 and 500 characters
    @Size(min = 10, max = 500, message = "Description should be between 10 and 500 characters")
    private String about_contact;

    // Getters and Setters

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getContact_nickname() {
        return contact_nickname;
    }

    public void setContact_nickname(String contact_nickname) {
        this.contact_nickname = contact_nickname;
    }

    public String getContact_work() {
        return contact_work;
    }

    public void setContact_work(String contact_work) {
        this.contact_work = contact_work;
    }

    public String getContact_image() {
        return contact_image;
    }

    public void setContact_image(String contact_image) {
        this.contact_image = contact_image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAbout_contact() {
        return about_contact;
    }

    public void setAbout_contact(String about_contact) {
        this.about_contact = about_contact;
    }

    // Default constructor
    public Contacts() {
    }

    /**
     * Constructor to initialize all fields of the Contacts class.
     *
     * @param contact_id       The contact's unique ID.
     * @param contact_name     The name of the contact.
     * @param contact_email    The email of the contact.
     * @param phone_number     The phone number of the contact.
     * @param contact_nickname The nickname of the contact.
     * @param contact_work     The work or occupation of the contact.
     * @param contact_image    The image URL for the contact.
     * @param user             The user associated with the contact.
     * @param about_contact    A description of the contact.
     */
    public Contacts(int contact_id, String contact_name, String contact_email, String phone_number, String contact_nickname, String contact_work, String contact_image, User user, String about_contact) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.phone_number = phone_number;
        this.contact_nickname = contact_nickname;
        this.contact_work = contact_work;
        this.contact_image = contact_image;
        this.user = user;
        this.about_contact = about_contact;
    }

    /**
     * Returns a string representation of the Contacts object.
     *
     * @return A string containing the contact's details.
     */
    @Override
    public String toString() {
        return "Contacts{" +
                "contact_id=" + contact_id +
                ", contact_name='" + contact_name + '\'' +
                ", contact_email='" + contact_email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", contact_nickname='" + contact_nickname + '\'' +
                ", contact_work='" + contact_work + '\'' +
                ", contact_image='" + contact_image + '\'' +
                ", user=" + user +
                ", about_contact='" + about_contact + '\'' +
                '}';
    }
}
