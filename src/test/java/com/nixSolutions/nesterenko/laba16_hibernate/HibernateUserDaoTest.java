package com.nixSolutions.nesterenko.laba16_hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.nixsolutions.nesterenko.laba14_maven.interfaces.RoleDao;
import com.nixsolutions.nesterenko.laba14_maven.interfaces.UserDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring-datasource.xml" })
@TestExecutionListeners({ DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
public class HibernateUserDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    private User userToCreate = null;
    private User userToRemove = null;
    private List<User> findAllUsers = null;
    private User user1 = null;
    private User user2 = null;
    private User user3 = null;

    @Before
    public void before() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1985, Calendar.JANUARY, 12);
        userToCreate = new User("sanya", "123", "sanya@mail.org", "sasha",
                "nevsky", new Date(calendar.getTime().getTime()));

        calendar.set(1972, Calendar.JANUARY, 21);
        userToRemove = new User("petr", "123", "petr@piter.org", "petr",
                "romanov", new Date(calendar.getTime().getTime()));

        calendar.set(1997, Calendar.OCTOBER, 20);
        user1 = new User("efiop", "123", "pushkin@piter.org", "sasha",
                "pushkin", new Date(calendar.getTime().getTime()));
        user1.setRole(new Role("user"));
        user1.setId(1L);
        calendar.set(1972, Calendar.JANUARY, 21);
        user2 = new User("petr", "123", "petr@piter.org", "petr", "romanov",
                new Date(calendar.getTime().getTime()));
        user2.setRole(new Role("admin"));
        user2.setId(2L);
        calendar.set(2001, Calendar.AUGUST, 3);
        user3 = new User("slava", "123", "slava@mail.org", "slava", "sevirsky",
                new Date(calendar.getTime().getTime()));
        user3.setRole(new Role("admin"));
        user3.setId(3L);

        findAllUsers = new ArrayList<User>();
        findAllUsers.add(user1);
        findAllUsers.add(user2);
        findAllUsers.add(user3);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    @ExpectedDatabase("/data/testCreateUser_expected.xml")
    public void testCreateUser() throws Exception {
        Role userRole = roleDao.findByName("user");
        userToCreate.setRole(userRole);
        userDao.create(userToCreate);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    @ExpectedDatabase("/data/testRemoveUser_expected.xml")
    public void testRemoveUser() throws Exception {
        Role adminRole = roleDao.findByName("admin");
        userToRemove.setRole(adminRole);
        userDao.remove(userToRemove);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    @ExpectedDatabase("/data/testUpdateUser_expected.xml")
    public void testUpdateUser() throws Exception {
        Role userRole = roleDao.findByName("user");
        User user = userDao.findByLogin("slava");
        user.setFirstName("slava_updated");
        user.setRole(userRole);
        userDao.update(user);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testFindAllUser() throws Exception {
        List<User> expectedList = findAllUsers;

        List<User> actualList = userDao.findAll();

        assertFalse(expectedList == actualList);
        assertTrue(expectedList.size() == actualList.size());

        for (int i = 0; i < actualList.size(); ++i) {
            User expected = expectedList.get(i);
            User actual = actualList.get(i);
            assertTrue((int) (long) expected.getId() == (int) (long) actual
                    .getId());
            assertTrue(expected.getLogin().equals(actual.getLogin()));
            assertTrue(expected.getEmail().equals(actual.getEmail()));
            assertTrue(expected.getFirstName().equals(actual.getFirstName()));
            assertTrue(expected.getPassword().equals(actual.getPassword()));
            assertTrue(expected.getRole().getName()
                    .equals(actual.getRole().getName()));
        }
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testFindByLoginUser() throws Exception {
        User expected = user3;
        Role adminRole = new Role("admin");
        expected.setId(3L);
        expected.setRole(adminRole);

        User actual = userDao.findByLogin("slava");

        assertFalse(expected == actual);
        assertTrue((int) (long) expected.getId() == (int) (long) actual.getId());
        assertTrue(expected.getLogin().equals(actual.getLogin()));
        assertTrue(expected.getEmail().equals(actual.getEmail()));
        assertTrue(expected.getFirstName().equals(actual.getFirstName()));
        assertTrue(expected.getPassword().equals(actual.getPassword()));
        assertTrue(expected.getRole().getName()
                .equals(actual.getRole().getName()));
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testFindByEmailUser() throws Exception {
        User actual = userDao.findByEmail("slava@mail.org");
        User expected = user3;
        Role adminRole = new Role("admin");
        expected.setId(3L);
        expected.setRole(adminRole);
        assertFalse(expected == actual);
        assertTrue((int) (long) expected.getId() == (int) (long) actual.getId());
        assertTrue(expected.getLogin().equals(actual.getLogin()));
        assertTrue(expected.getEmail().equals(actual.getEmail()));
        assertTrue(expected.getFirstName().equals(actual.getFirstName()));
        assertTrue(expected.getPassword().equals(actual.getPassword()));
        assertTrue(expected.getRole().getName()
                .equals(actual.getRole().getName()));
    }

}
