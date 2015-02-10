package com.nixsolutions.nesterenko.laba14_maven.interfaces;

import java.util.List;

import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public interface UserDao {

    public void create(User user) throws Exception;
    public void update(User user) throws Exception;
    public void remove(User user) throws Exception;
    public List<User> findAll() throws Exception;
    public User findByLogin(String login) throws Exception;
    public User findByEmail(String email) throws Exception;
    
}
