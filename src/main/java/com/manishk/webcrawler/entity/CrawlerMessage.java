package com.manishk.webcrawler.entity;

import com.manishk.webcrawler.util.SelectionPolicy;

/**
 * Model holding an CrawlMessage received from the queue
 * Created by manish on 03/04/15.
 */
public class CrawlerMessage {

    private String domain;
    private String resource;
    private Integer crawlLevel;
    private SelectionPolicy selectionPolicy;

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    /**
     *
     * @param domain
     * @param resource
     * @param crawlLevel
     */
    public CrawlerMessage(String domain, String resource, Integer crawlLevel){
        this.domain = domain;
        this.resource = resource;
        this.crawlLevel = crawlLevel;
        this.selectionPolicy = SelectionPolicy.SELECTION;
    }

    /**
     *
     * @param domain
     * @param resource
     * @param crawlLevel
     * @param selectionPolicy
     */
    public CrawlerMessage(String domain, String resource, Integer crawlLevel,SelectionPolicy selectionPolicy){
        this.domain = domain;
        this.resource = resource;
        this.crawlLevel = crawlLevel;
        this.selectionPolicy = selectionPolicy;
    }

    public String getDomain() {
        return domain;
    }

    public String getResource() {
        return resource;
    }

    public Integer getCrawlLevel() {
        return crawlLevel;
    }
}
