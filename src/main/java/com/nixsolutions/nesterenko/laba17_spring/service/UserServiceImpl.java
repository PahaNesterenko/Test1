package com.nixsolutions.nesterenko.laba17_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.RoleDao;
import com.nixsolutions.nesterenko.laba14_maven.interfaces.UserDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
	private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User create(User user) throws Exception {
	    Role r = roleDao.findByName(user.getRole().getName());
        user.setRole(r);
		userDao.create(user);
		return userDao.findByLogin(user.getLogin());
	}

	@Override
	public User update(User user) throws Exception {
	    Role r = roleDao.findByName(user.getRole().getName());
	    user.setRole(r);
		userDao.update(user);
		return userDao.findByLogin(user.getLogin());
	}

	@Override
	public void remove(Long id) throws Exception {
		List<User> users = userDao.findAll();
        for( User u : users){
            if( (int)(long)u.getId() == (int)(long) id ){
                userDao.remove(u);
            }
        }
    }

    @Override
    public User findById(Long id) throws Exception {
        List<User> users = userDao.findAll();
        for( User u : users){
            if( (int)(long)u.getId() == (int)(long) id ){
                return u;
            }
        }
        return null;
    }

	@Override
	public List<User> findAll() throws Exception {
		return userDao.findAll();
	}

	@Override
	public User findByLogin(String login) throws Exception {
		return userDao.findByLogin(login);
	}

	@Override
	public User findByEmail(String email) throws Exception {
		return userDao.findByEmail(email);
	}

}
