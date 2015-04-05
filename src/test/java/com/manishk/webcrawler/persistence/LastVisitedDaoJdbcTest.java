package com.manishk.webcrawler.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class LastVisitedDaoJdbcTest {

    @Autowired
    LastVisitedDao lastVisitedDao;
    @Test
    public void testInsertSelect() throws Exception {
        String domain= "abc.com";
        Assert.assertNull(lastVisitedDao.select(domain));
        lastVisitedDao.insertOrUpdate(domain, new Date());
        Thread.sleep(5000);
        Date lastVisited = lastVisitedDao.select(domain);
        System.out.println(lastVisited +" - "+new Date());
        Assert.assertNotNull(lastVisited);
        Assert.assertTrue(lastVisited.before(new Date()));
    }
}