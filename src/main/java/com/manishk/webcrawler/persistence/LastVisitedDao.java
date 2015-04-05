package com.manishk.webcrawler.persistence;


import java.util.Date;

/**
 * Created by manish on 05/04/15.
 */
public interface LastVisitedDao {

    void insertOrUpdate(String domain, Date date);

    Date select(String domain);
}
