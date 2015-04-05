package com.manishk.webcrawler.policy;

/**
 * Created by manish on 04/04/15.
 */
public interface WebCrawlerPolicy {

    boolean allowedPerPolicy(String domain, String url);
}
