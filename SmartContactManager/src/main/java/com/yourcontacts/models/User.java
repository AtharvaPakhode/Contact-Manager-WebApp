package com.yourcontacts.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_table")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int user_id;

    @NotBlank(message="name should not be blank") // message will print if user keep this field blank
    @Size(min=2 , max=20 , message =" minimum 2 and maximum 20 characters are allowed ")
    private String user_name;

    @Column(unique = true)
    @Email(message = "Email should be valid")
    @NotBlank(message="Email should not be blank")
    private String user_email;


    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, " +
                    "one lowercase letter, one digit, one special character, " +
                    "and be at least 8 characters long.")
    private String user_password;


    private String user_role;


    private String image_URL;

    @Column(length=500)
    private String about_user;

    private boolean status;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "user")
    private List<Contacts> contact_list = new ArrayList<>();


    // getters and setters --->

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

//Constructors--->

    public User() {
    }

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
