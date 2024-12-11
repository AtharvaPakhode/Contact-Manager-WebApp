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

    private String contact_image;;

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

    public String getContact_image() {
        return contact_image;
    }

    public void setContact_image(String contact_image) {
        this.contact_image = contact_image;
    }

    public String getAbout_contact() {
        return about_contact;
    }

    public void setAbout_contact(String about_contact) {
        this.about_contact = about_contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //Constructor

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



