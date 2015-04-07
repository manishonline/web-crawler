package com.manishk.webcrawler.persistence;


import java.util.Date;
import java.util.List;

/**
 * DAO interface for LASTVISITED URLS
 * Created by manish on 05/04/15.
 */
public interface LastVisitedDao {

    void insertOrUpdate(String domain, Date date);

    Date select(String domain);

    List<String> select(Date dateAfter);
}
