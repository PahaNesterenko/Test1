package com.nixsolutions.nesterenko.laba17_spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.Validator;

import net.tanesha.recaptcha.ReCaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.RoleDao;
import com.nixsolutions.nesterenko.laba14_maven.interfaces.UserDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;
import com.nixsolutions.nesterenko.laba17_spring.Vars;
import com.nixsolutions.nesterenko.laba17_spring.form.UserForm;

@Controller
public class UserService {

    private List<User> users = null;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ReCaptcha reCaptcha;
    
    @Autowired
    private Validator validator;

    @RequestMapping(value = "/preEditUser", method = RequestMethod.GET)
    public String editUser(@RequestParam Long userId, ModelMap model,
            HttpServletRequest req) throws Exception {
        User userToEdit = null;

        users = userDao.findAll();
        for (User user : users) {
            if ((int) (long) user.getId() == (int) (long) userId) {
                userToEdit = user;
                break;
            }
        }
        model.put("userToEdit", userToEdit);
        model.put("userForm", new UserForm());
        model.put("type", Vars.EDIT_USER_TYPE);
        return Vars.USER_FORM;
    }

    @RequestMapping(value = "/preAddUser", method = RequestMethod.GET)
    public String addUser(ModelMap model, HttpServletRequest req) {
        model.put("type", Vars.ADD_USER_TYPE);
        model.put("userForm", new UserForm());
        return Vars.USER_FORM;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String editUserDo(@Valid @ModelAttribute("userForm") UserForm form,
            BindingResult res, ModelMap model, HttpServletRequest req)
            throws Exception {
        model.put("type", Vars.ADD_USER_TYPE);
        
        validator.validate(form, res);
        if (res.hasErrors() ) {
            return Vars.USER_FORM;
        }

        if (databaseHasLogin(form.getLogin())) {
            res.rejectValue("login", "login", "Not unique login");
            return Vars.USER_FORM;
        }

        if (databaseHasEmail(form.getEmail())) {
            res.rejectValue("email", "email", "Not unique email");
            return Vars.USER_FORM;
        }
        User user = form.getUser();
        user.setRole(roleDao.findByName(form.getRole()));
        userDao.create(user);

        users = userDao.findAll();

        model.put("users", users);
        return Vars.ADMIN_HOME;

    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String addUserDo(@Valid @ModelAttribute("userForm") UserForm form,
            BindingResult res, ModelMap model, HttpServletRequest req)
            throws Exception {
        model.put("type", Vars.EDIT_USER_TYPE);

        validator.validate(form, res);
        if (res.hasErrors() ) {
            return Vars.USER_FORM;
        }

        User old = userDao.findByLogin(form.getLogin());
        if (!old.getEmail().equals(form.getEmail())) {
            if (databaseHasEmail(form.getEmail())) {
                res.rejectValue("email", "email", "Not unique email");
                return Vars.USER_FORM;
            }
        }
        User user = form.getUser();
        user.setId(old.getId());
        user.setRole(roleDao.findByName(form.getRole()));
        userDao.update(user);

        users = userDao.findAll();

        model.put("users", users);
        return Vars.ADMIN_HOME;

    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam Long userId, ModelMap model,
            HttpServletRequest req) throws Exception {

        users = userDao.findAll();
        for (User user : users) {
            if ((int) (long) user.getId() == userId) {
                userDao.remove(user);
                break;
            }
        }
        users = userDao.findAll();
        model.put("users", users);
        return Vars.ADMIN_HOME;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model, HttpServletRequest req) {
        model.put("userForm", new UserForm());
        return Vars.REGISTR_FORM;
    }

    @RequestMapping(value = "/registrationDo", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String registrationDo(
            @RequestParam("recaptcha_challenge_field") String challengeField,
            @RequestParam("recaptcha_response_field") String responseField,
            @Valid @ModelAttribute("userForm") UserForm form,
            BindingResult res, ModelMap model, HttpServletRequest req)
            throws Exception {

        validator.validate(form, res);
        if (res.hasErrors() ) {
            return Vars.REGISTR_FORM;
        }
        if (databaseHasLogin(form.getLogin())) {
            res.rejectValue("login", "login", "Not unique login");
            return Vars.REGISTR_FORM;
        }

        if (databaseHasEmail(form.getEmail())) {
            res.rejectValue("email", "email", "Not unique email");
            return Vars.REGISTR_FORM;
        }

        if (!reCaptcha.checkAnswer(req.getRemoteAddr(), challengeField,
                responseField).isValid()) {
            model.put("error", "Wrong captcha answer");
            return Vars.REGISTR_FORM;
        }

        User user = form.getUser();
        user.setRole(roleDao.findByName(form.getRole()));
        userDao.create(user);

        return Vars.LOGIN_FORM;
    }

  
    private boolean databaseHasLogin(String login) throws Exception {
        return userDao.findByLogin(login) != null;
    }

    private boolean databaseHasEmail(String email) throws Exception {
        return userDao.findByEmail(email) != null;
    }

}
