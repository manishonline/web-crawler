package com.manishk.webcrawler.fetcher;

import com.manishk.webcrawler.entity.CrawledDocument;
import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.policy.WebCrawlerPolicy;

import java.util.List;

/**
 * Created by manish on 05/04/15.
 */
public interface DocumentFetcher {

    CrawledDocument fetch(CrawlerMessage message, List<WebCrawlerPolicy> policies);
}
