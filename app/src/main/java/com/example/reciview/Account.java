package com.example.reciview;

//Account class. Provides the variables that each user account on the platform will have.
//
public class Account {

    private String phonenum;
    private String emailAddress;
    private String username;
    private String password;

    //Firebase needs an empty constructor to avoid errors.
    public Account(){
    }

    //Actual constructor
    public Account(String phonenum, String emailAddress, String username, String password){

        this.phonenum = phonenum;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }

    //Getters and Setters
    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
