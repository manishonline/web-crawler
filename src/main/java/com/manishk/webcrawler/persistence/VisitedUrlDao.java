package com.manishk.webcrawler.persistence;

/**
 * Created by manish on 05/04/15.
 */
public interface VisitedUrlDao {

    void insert(String domain, String uri);

    Integer select(String domain, String uri);

    void delete (String domain);

}
