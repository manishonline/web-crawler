package com.manishk.webcrawler.entity;

import java.util.List;

/**
 * Created by manish on 05/04/15.
 */
public class CrawledDocument {
    private String domain;
    private String resource;
    private String text;
    private List<String> childLinks;

    public CrawledDocument(String domain,String resource, String text, List<String> childLinks){
        this.domain = domain;
        this.resource = resource;
        this.text = text;
        this.childLinks = childLinks;
    }

    public String getDomain() {
        return domain;
    }

    public String getResource() {
        return resource;
    }

    public String getText() {
        return text;
    }

    public List<String> getChildLinks() {
        return childLinks;
    }
}
