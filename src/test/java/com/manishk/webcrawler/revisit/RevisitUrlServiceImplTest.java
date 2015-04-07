package com.manishk.webcrawler.revisit;

import com.manishk.webcrawler.persistence.LastVisitedDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class RevisitUrlServiceImplTest {

    @Autowired
    RevisitUrlService revisitUrlService;

    @Autowired
    LastVisitedDao lastVisitedDao;

    @Test
    public void testGetLinkToRevisit() throws Exception {
        List<String> links = revisitUrlService.getLinkToRevisit(new Date());
        Assert.assertFalse(links.contains("www.abc.com"));
        //Now visit some URLs
        lastVisitedDao.insertOrUpdate("www.abc.com", new Date());
        Thread.sleep(4000);
        links = revisitUrlService.getLinkToRevisit(new Date());
        Assert.assertTrue(links.size()>0);
        Assert.assertTrue(links.contains("www.abc.com"));

    }
}