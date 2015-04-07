package com.manishk.webcrawler.policy;

import com.manishk.webcrawler.util.SelectionPolicy;

/**
 * Web crawler policy interface
 * Created by manish on 04/04/15.
 */
public interface WebCrawlerPolicy {

    /**
     * If fetch from the domain is allowered as current SelectionPolicy
     * @param domain
     * @param url
     * @return
     */
    boolean allowedPerPolicy(String domain, String url);

    /**
     * Get current SelectionPolicy
     * @return
     */
    SelectionPolicy getSelectionPolicy();
}
