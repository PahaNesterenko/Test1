package com.nixsolutions.nesterenko.laba16_hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.RoleDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;

public class HibernateRoleDao implements RoleDao {

    private static Log logger = LogFactory.getLog(HibernateRoleDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    static {
        logger.trace("New instance of HibernateRoleDao is created");
    }

    @Override
    @Transactional
    public void create(Role role) throws Exception {

        if (role == null) {
            logger.error("Null passed to create role");
            throw new NullPointerException();
        }
        Role temp = findByName(role.getName());
        if (temp != null) {
            logger.error("There is a same role in the database ");
            throw new Exception("There is a same role in the database");
        }

        try {
            sessionFactory.getCurrentSession().save(role);
        } catch (Exception e) {
            logger.error("Can not create session: " + e.getStackTrace());
            throw new Exception("Can not create session.", e);
        } 
    }

    @Override
    @Transactional
    public void update(Role role) throws Exception {

        if (role == null) {
            logger.error("Null passed to update role");
            throw new NullPointerException();
        }

        try {
            sessionFactory.getCurrentSession().update(role);
        } catch (Exception e) {
            logger.error("Can not create session:" + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
    }

    @Override
    @Transactional
    public void remove(Role role) throws Exception {
        if (role == null) {
            logger.error("Null passed to remove role");
            throw new NullPointerException();
        }
        Role temp = findByName(role.getName());
        if (temp == null) {
            logger.error("There is no such role in the database");
            throw new Exception("There is no such role in the database");
        }

        try {
            sessionFactory.getCurrentSession().delete(temp);
        } catch (Exception e) {
            logger.error("Can not create session. " + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Role findByName(String name) throws Exception {
        Session session = null;
        String hql = "FROM com.nixsolutions.nesterenko.laba16_hibernate.entity.Role R WHERE R.name = :roleName";
        List<Role> roles = null;
        if (name == null || name.equals("")) {
            logger.error("Invalid parameter for role name");
            throw new Exception("Invalid parameter for role name");
        }

        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("roleName", name);
            roles = query.list();
        } catch (Exception e) {
            logger.error("Can not create session. " + e.getStackTrace());
            throw new Exception("Can not create session", e);
        } 
        if (roles == null || roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

}
