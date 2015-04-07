package com.manishk.webcrawler.fetcher;

import com.manishk.webcrawler.entity.CrawledDocument;
import com.manishk.webcrawler.entity.CrawlerMessage;
import org.junit.Assert;
import org.junit.Test;

public class DocumentFetcherImplTest {

    @Test
    public void testDocumentFetch() throws Exception{
        CrawlerMessage message = new CrawlerMessage("www.greatbong.net","/",0);
        DocumentFetcher fetcher = new DocumentFetcherImpl();
        CrawledDocument document = fetcher.fetch(message);
        Assert.assertNotNull(document);
        Assert.assertNotNull(document.getDomain());
        Assert.assertTrue(document.getChildLinks().size()>0);
    }

}