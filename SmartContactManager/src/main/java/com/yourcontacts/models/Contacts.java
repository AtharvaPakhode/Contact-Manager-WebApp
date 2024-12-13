package com.yourcontacts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact_table")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contact_id;

    @NotBlank(message = "Contact name is required")
    @Size(min = 3, max = 100, message = "Contact name must be between 3 and 100 characters")
    private String contact_name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String contact_email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone_number;

    @Size(min = 2, max = 50, message = "Nickname should be between 2 and 50 characters")
    private String contact_nickname;

    @Size(min = 2, max = 100, message = "Work should be between 2 and 100 characters")
    private String contact_work;

    private String contact_image; // Will be handled for file upload in controller

    @ManyToOne
    private User user;

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

    // Constructor

    public Contacts() {
    }

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
