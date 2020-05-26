package com.example.myapplication1.Model;

public class Register {

    private String email;
    private String password;
    private String password2;
    private boolean isRegistered;
    private String message;
    private String first_name;
    private String last_name;

    public Register(  String first_name, String last_name,String email, String password, String password2) {
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.first_name = first_name;
        this.last_name = last_name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstname() {
        return first_name;
    }

    public void setFirstname(String firstname) {
        this.first_name = firstname;
    }

    public String getLastname() {
        return last_name;
    }

    public void setLastname(String lastname) {
        this.last_name = lastname;
    }
}
