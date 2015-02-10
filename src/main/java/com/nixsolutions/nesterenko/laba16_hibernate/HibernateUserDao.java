package com.nixsolutions.nesterenko.laba16_hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.UserDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public class HibernateUserDao implements UserDao {

    private Logger logger = LoggerFactory.getLogger(HibernateUserDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void create(User user) throws Exception {
        if (user == null || user.getLogin() == null) {
            logger.error("Invalid parameter for creating user");
            return;
        }

        if (findByLogin(user.getLogin()) != null) {
            logger.error("There is a same user in the database");
            return;
        }

        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
    }

    @Override
    @Transactional
    public void update(User user) throws Exception {
        if (user == null || user.getLogin() == null) {
            logger.error("Invalid parameter for updateing user");
            throw new Exception("Invalid parameter for updateing user");
        }
     
        try {
            sessionFactory.getCurrentSession().update(user);
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
    }

    @Override
    @Transactional
    public void remove(User user) throws Exception {
        if (user == null || user.getLogin() == null) {
            logger.error("Invalid parameter for removing user");
            return;
        }
        User temp = findByLogin(user.getLogin());
        if (temp == null) {
            logger.error("There is no such user in the database");
            throw new Exception("There is no such user in the database");
        }

        try {
            sessionFactory.getCurrentSession().delete(temp);
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> findAll() throws Exception {
        List<User> users = null;
        Session session = null;

        try {
            session =  sessionFactory.getCurrentSession();
            users = session.createCriteria(User.class).list();
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
        return users;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public User findByLogin(String login) throws Exception {
        Session session = null;
        String hql = "FROM com.nixsolutions.nesterenko.laba16_hibernate.entity.User U WHERE U.login = :userLogin";
        List<User> users = null;
        if (login == null || login.equals("")) {
            logger.error("Invalid parameter for user login");
            throw new Exception("Invalid parameter for user login");
        }

        try {
            session =  sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("userLogin", login);
            users = query.list();
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public User findByEmail(String email) throws Exception {
        Session session = null;
        String hql = "FROM com.nixsolutions.nesterenko.laba16_hibernate.entity.User U WHERE U.email = :email";
        List<User> users = null;
        if (email == null || email.equals("")) {
            logger.error("Invalid parameter for user email");
            throw new Exception("Invalid parameter for user email");
        }

        try {
            session =  sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            users = query.list();
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

}
