package com.nixsolutions.nesterenko.laba17_spring.service;

import java.util.List;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public interface UserService {

    public User create(User user) throws Exception;
    public User update(User user) throws Exception;
    public void remove(Long id) throws Exception;
    public List<User> findAll() throws Exception;
    public User findByLogin(String login) throws Exception;
    public User findByEmail(String email) throws Exception;
    public User findById(Long id) throws Exception;
}
