package com.example.taskmanagermaster.Model;

public class User {
    private String fullname, email, password, confirm;

    public User() {
    }

    public User(String fullname, String email, String password, String confirm) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.confirm = confirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
