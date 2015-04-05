package com.manishk.webcrawler.persistence;

import java.util.List;
import java.util.Map;

/**
 * Created by manish on 05/04/15.
 */
public interface RobotsTxtDao {

    void insert(String domain, List<String> uri);

    void delete(String domain);

    Map<String,List<String>> select(String domain);
}
