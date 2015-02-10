package com.nixsolutions.nesterenko.laba17_spring.form;

import java.sql.Date;


import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public class UserForm {

    private User user = new User();

    public void setUser(User user) {
        this.user = user;
    }

    private Long id;

    private String login;

    private String password;

    private String confirmPassword;

    private String firstName;

    private String lastName;

    private String email;

    private String testBirthday;

    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTestBirthday() {
        return testBirthday;
    }

    public void setTestBirthday(String testBirthday) {
        this.testBirthday = testBirthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User getUser() {
        user.setId(id);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setBirthday(Date.valueOf(testBirthday)) ;
        return user;
    }

}