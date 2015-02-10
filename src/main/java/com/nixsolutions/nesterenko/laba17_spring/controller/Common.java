package com.nixsolutions.nesterenko.laba17_spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.UserDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;
import com.nixsolutions.nesterenko.laba17_spring.Vars;

@Controller
public class Common {

    @Autowired
    private UserDao userDao;

   
    @RequestMapping(value = "/login", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String login(HttpSession session) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        String login = authentication.getName();

        User user = userDao.findByLogin(login);

        session.setAttribute("currentUser", user);
        if (authentication.getAuthorities().toString().equals( "[" + Vars.ADMIN_ROLE + "]" )) {
            session.setAttribute("users", userDao.findAll());
            return Vars.ADMIN_HOME;
        } else if (authentication.getAuthorities().toString().equals( "[" + Vars.USER_ROLE + "]" )) {
            return Vars.USER_HOME;
        } else {
            return Vars.LOGIN_FORM;
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(ModelMap model) throws Exception {
        model.put("users", userDao.findAll());
        return Vars.ADMIN_HOME;
    }

}
