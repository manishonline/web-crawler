package com.manishk.webcrawler.fetcher;

import com.manishk.webcrawler.entity.CrawledDocument;
import com.manishk.webcrawler.entity.CrawlerMessage;

/**
 * Document fetcher interface
 * Created by manish on 05/04/15.
 */
public interface DocumentFetcher {

    CrawledDocument fetch(CrawlerMessage message);
}
