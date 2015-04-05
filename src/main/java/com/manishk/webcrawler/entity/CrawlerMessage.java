package com.manishk.webcrawler.entity;

/**
 * Created by manish on 03/04/15.
 */
public class CrawlerMessage {

    private String domain;
    private String resource;
    private Integer crawlLevel;

    public CrawlerMessage(String domain, String resource, Integer crawlLevel){
        this.domain = domain;
        this.resource = resource;
        this.crawlLevel = crawlLevel;
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
