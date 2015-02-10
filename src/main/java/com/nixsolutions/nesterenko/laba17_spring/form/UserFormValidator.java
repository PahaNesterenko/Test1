/**
 *
 */
package com.nixsolutions.nesterenko.laba17_spring.form;

import java.sql.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.validator.routines.EmailValidator;

import com.nixsolutions.nesterenko.laba17_spring.Vars;


public class UserFormValidator implements Validator {

    public UserFormValidator(){}

    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login",
                "required.login", "Login can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Password can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
                "required.confirmPassword", "Password can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "Email can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "required.firstName", "First name can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "required.lastName", "Last name can't be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "testBirthday",
                "required.testBirthday", "Please, type birthday!");

        UserForm userForm = (UserForm) target;

        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "notmatch.password",
                    "Password and Conform password is not match!");
        }

        if (!EmailValidator.getInstance().isValid(userForm.getEmail())) {
            errors.rejectValue("email", "invalid.email",
                    "Email address is not valid.");
        }

        if (!isValidDate(userForm.getTestBirthday())) {
            errors.rejectValue("testBirthday", "wrongformat.testBirthday",
                    "Please, type birthday correct! Format: " + Vars.DATE_FORMAT);
        }
        

    }

    private boolean isValidDate(String date) {
       Date temp = null;
        try {
            temp = Date.valueOf(date);
            if (temp.getTime() > System.currentTimeMillis()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
