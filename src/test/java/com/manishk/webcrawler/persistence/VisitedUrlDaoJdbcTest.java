package com.manishk.webcrawler.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class VisitedUrlDaoJdbcTest {

    @Autowired
    VisitedUrlDao visitedUrlDao;


    @Test
    public void testInsertSelect() throws Exception {
        String url = "www.greatbong.net";
        visitedUrlDao.insert(url,"");
        Integer hash = visitedUrlDao.select(url,"");
        Assert.assertNotNull(hash);
        Assert.assertTrue(url.hashCode() == hash);
    }



}