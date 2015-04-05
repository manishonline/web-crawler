package com.manishk.webcrawler.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RobotsTxtDaoJdbcTest {

    @Autowired
    RobotsTxtDao robotsTxtDao;

    @Test
    public void testInsertSelect() throws Exception {
        List<String> uri = new ArrayList<String>();
        String domain = "abc.com";
        uri.add("a.html");
        uri.add("b.thml");
        robotsTxtDao.insert(domain,uri);

        Map<String, List<String>> fromDB = robotsTxtDao.select(domain);

        Assert.assertTrue(fromDB.values().size()==1);
        Assert.assertTrue(fromDB.containsKey("abc.com"));

    }
}