package com.nixSolutions.nesterenko.laba21_services;


import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public class RestControllerTest  {
    
    private RestTemplate restTemplate =  new RestTemplate();
    private User createUser = null;
    private HttpHeaders headers = null;
    
    private String REST_URL = "http://10.10.32.124:8080/laba22_backbone/users/";
    private String REST_FINDUSERBYEMAIL_URL =  REST_URL + "userByEmail/{mail}/";
    private String REST_FINDUSERBYLOGIN_URL = REST_URL + "userByLogin/{login}/";
    private String REST_DELETE_URL = REST_URL + "{id}/";
    private String REST_UPDATE_URL = REST_URL + "{id}/";
    
    
    @Before
    public void before() {
          createUser = new User("test", "password", "test@mail.ru", "TestName", "testLastName", new Date(0));
          Role userRole = new Role("user");
          createUser.setRole(userRole);
          
          headers = new HttpHeaders();
          headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         headers.setContentType(MediaType.APPLICATION_JSON);
    }
 
    @Test
    public void testCreateUser() {
        
        HttpEntity<User> request = new HttpEntity<User>(createUser, headers);
        ResponseEntity<User> createResponseEntity = restTemplate.exchange(  REST_URL , HttpMethod.POST, request, User.class);
        User respUser = createResponseEntity.getBody();
        
        ResponseEntity<User> checkResponseEntity = restTemplate.exchange( REST_FINDUSERBYLOGIN_URL , HttpMethod.GET, null, User.class, "test");
        User checkUser = checkResponseEntity.getBody();
        
        restTemplate.delete( REST_DELETE_URL, checkUser.getId() );
        
        assertNotNull(respUser);
        assertEquals( checkUser, respUser );
    }
    
    @Test
    public void testUpdateUser() {
        
        HttpEntity<User> createRequest = new HttpEntity<User>(createUser, headers);
        ResponseEntity<User> createResponseEntity = restTemplate.exchange( REST_URL , HttpMethod.POST, createRequest, User.class);
        User respUser = createResponseEntity.getBody();
        respUser.setFirstName("UpdateName");
        
        HttpEntity<User> updateRequest = new HttpEntity<User>(respUser, headers);
        ResponseEntity<User> updateResponseEntity = restTemplate.exchange( REST_UPDATE_URL , HttpMethod.PUT, updateRequest, User.class, respUser.getId());
        User updateUser = updateResponseEntity.getBody();
        
        ResponseEntity<User> checkResponseEntity = restTemplate.exchange( REST_FINDUSERBYLOGIN_URL , HttpMethod.GET, null, User.class, "test");
        User checkUser = checkResponseEntity.getBody();
        
        restTemplate.delete( REST_DELETE_URL, checkUser.getId() );
        
        assertNotNull(respUser);
        assertEquals( checkUser, updateUser );
    }
    
    @Test
    public void testFindByEmai1() {
        
        HttpEntity<User> request = new HttpEntity<User>(createUser, headers);
        ResponseEntity<User> createResponseEntity = restTemplate.exchange(  REST_URL , HttpMethod.POST, request, User.class);
        User respUser = createResponseEntity.getBody();
        
        ResponseEntity<User> responseEntity = restTemplate.exchange(  REST_FINDUSERBYEMAIL_URL , HttpMethod.GET, null, User.class, createUser.getEmail());
        User user = responseEntity.getBody();
        assertEquals(user.getEmail(), respUser.getEmail());
        
        restTemplate.delete( REST_DELETE_URL, user.getId() );
    }
    
    @Test
    public void testFindByLogin() {
        HttpEntity<User> request = new HttpEntity<User>(createUser, headers);
        ResponseEntity<User> createResponseEntity = restTemplate.exchange(  REST_URL , HttpMethod.POST, request, User.class);
        User respUser = createResponseEntity.getBody();
        
        ResponseEntity<User> responseEntity = restTemplate.exchange(  REST_FINDUSERBYLOGIN_URL , HttpMethod.GET, null, User.class, createUser.getLogin());
        User user = responseEntity.getBody();
        assertEquals(user.getEmail(), respUser.getEmail());
        
        restTemplate.delete( REST_DELETE_URL, user.getId() );
    }
    
    @Test
    public void testFindAll() {
        ResponseEntity<User[]> responseEntity = restTemplate.exchange( REST_URL , HttpMethod.GET, null, User[].class);
        User[] users = responseEntity.getBody();
        assertNotNull(users);
    }
}