package com.nixsolutions.nesterenko.laba21_services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;
import com.nixsolutions.nesterenko.laba17_spring.Vars;
import com.nixsolutions.nesterenko.laba17_spring.service.UserService;
import org.apache.log4j.Logger;

@Controller
@RequestMapping( value = Vars.REST_NAMESPACE, headers = "Accept=application/json")
public class RestUserService {
    
    static Logger logger = Logger.getLogger(RestUserService.class);
    
    @Autowired
    private UserService userService;
    
    @RequestMapping( value="/{id}", method=RequestMethod.PUT, produces="application/json", consumes="application/json" )
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody User updateUser( @PathVariable Long id, @RequestBody User user ) throws Exception {
        logger.trace( "Request for update user: id:" + user.getId() + " role:" + user.getRole().getName() + " name:" + user.getFirstName() );
        return userService.update(user);
    }
    
    @RequestMapping( method=RequestMethod.GET, produces="application/json")
    public @ResponseBody User[] findAll() throws Exception {
        logger.trace("Request for find all users");
        return userService.findAll().toArray(new User[0]);
    }

    @RequestMapping( value="/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody User find( @PathVariable Long id) throws Exception {
        logger.trace("Request for find users with id: " + id);
        return userService.findById(id);
    }
    
    @RequestMapping( value="/{id}",  method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.OK )
    public void deleteUser( @PathVariable Long id ) throws Exception {
        logger.trace( "Request for delete user with id: " + id );
        userService.remove(id);
    }
    
    @RequestMapping( value=Vars.REST_FINDUSERBYLOGINMAPPING, method=RequestMethod.GET, produces="application/json" )
    public @ResponseBody User userByLogin(@PathVariable String login) throws Exception{
        logger.trace("Request for find user by login \"" + login + "\"");
        return userService.findByLogin(login);
    }

    @RequestMapping( value=Vars.REST_FINDUSERBYEMAILMAPPING, method=RequestMethod.GET, produces="application/json" )
    public @ResponseBody User userByEmail(@PathVariable String email) throws Exception{
        logger.trace("Request for find user by email \"" + email + "\"");
        return userService.findByEmail(email);
    }

    @RequestMapping(  value="", method=RequestMethod.POST, produces="application/json", consumes="application/json" )
    @ResponseStatus( HttpStatus.OK )
    public @ResponseBody User createUser( @RequestBody User user ) throws Exception {
        logger.trace( "Request for create user: id:" + user.getId() + " role:" + user.getRole().getName() + " name:" + user.getFirstName() );
        return userService.create(user);
    }

}
