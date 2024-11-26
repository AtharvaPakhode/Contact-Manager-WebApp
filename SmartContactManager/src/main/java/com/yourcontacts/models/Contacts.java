package com.yourcontacts.models;

import jakarta.persistence.*;


@Entity
@Table(name="contact_table")
public class Contacts {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int contact_id;

    private String contact_name;

    private String contact_email;

    private String phone_number;

    private String contact_nickname;

    private String contact_work;

    private String contact_imageURL;

    @ManyToOne
    private User user;

    @Column(length=500)
    private String about_contact;

    //Getters and setters---->

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

    public String getContact_imageURL() {
        return contact_imageURL;
    }

    public void setContact_imageURL(String contact_imageURL) {
        this.contact_imageURL = contact_imageURL;
    }

    public String getAbout_contact() {
        return about_contact;
    }

    public void setAbout_contact(String about_contact) {
        this.about_contact = about_contact;
    }


    //Constructor

    public Contacts() {
    }

    public Contacts(int contact_id, String contact_name, String contact_email, String phone_number, String contact_nickname, String contact_work, String contact_imageURL, String about_contact) {
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.phone_number = phone_number;
        this.contact_nickname = contact_nickname;
        this.contact_work = contact_work;
        this.contact_imageURL = contact_imageURL;
        this.about_contact = about_contact;
    }
}



