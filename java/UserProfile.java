package com.example.dhruvpatel.login;



public class UserProfile {

    public String fname;
    public String lname;
    public String email;
    public String password;
    public String mobileno;
    public String address;


    public UserProfile(String fname,String lname, String email, String password,String mobileno,String address) {
        this.fname=fname;
        this.lname=lname;
        this.email = email;
        this.password = password;
        this.mobileno = mobileno;
        this.address = address;
    }
}

