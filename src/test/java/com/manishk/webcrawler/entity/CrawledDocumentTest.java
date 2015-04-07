package com.manishk.webcrawler.entity;


import org.junit.Assert;
import org.junit.Test;

public class CrawledDocumentTest {

    @Test
    public void testCrawlDocCreation() throws Exception{
        CrawledDocument document = new CrawledDocument("www.abc.com","/","nothing",null);
        Assert.assertNull(document.getChildLinks());
        Assert.assertTrue("www.abc.com".equals(document.getDomain()));
        Assert.assertTrue("/".equals(document.getResource()));
        Assert.assertTrue("nothing".equals(document.getText()));
    }

}