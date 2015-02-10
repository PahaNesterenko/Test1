package com.nixSolutions.nesterenko.laba16_hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import com.nixsolutions.nesterenko.laba14_maven.interfaces.RoleDao;
import com.nixsolutions.nesterenko.laba16_hibernate.entity.Role;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring-datasource.xml" })
@TestExecutionListeners({ DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
public class HibernateRoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    @ExpectedDatabase("/data/testCreateRole_expected.xml")
    public void testCreateRole() throws Exception {
        Role r = new Role("sveta");
        r.setId(4L);
        roleDao.create(r);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    @ExpectedDatabase("/data/testUpdateRole_expected.xml")
    public void testUpdateUser() throws Exception {
        Role vasya = new Role("misha");
        vasya.setId(3L);
        roleDao.update(vasya);
    }

    @Test(expected = Exception.class)
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testRemoveRoleWithUser() throws Exception {
        Role user = roleDao.findByName("user");
        roleDao.remove(user);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testFindByNameNull() throws Exception {
        Role actual = roleDao.findByName("noRole");
        assertNull(actual);
    }

    @Test
    @DatabaseSetup("/data/TestDataSet.xml")
    public void testFindByNameNotnull() throws Exception {
        Role actual = roleDao.findByName("user");
        assertNotNull(actual);
    }

}
