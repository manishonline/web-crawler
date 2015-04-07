package com.manishk.webcrawler.policy;

import com.manishk.webcrawler.persistence.RobotsTxtDao;
import com.manishk.webcrawler.persistence.VisitedUrlDao;
import com.manishk.webcrawler.policy.robotstxt.RobotsTxtFileReader;
import com.manishk.webcrawler.util.SelectionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by manish on 03/04/15.
 */
@Service
public class PageSelectionPolicy implements WebCrawlerPolicy {

    @Autowired
    private RobotsTxtDao robotsTxtDao;

    @Autowired
    private VisitedUrlDao visitedUrlDao;

    @Autowired
    private RobotsTxtFileReader robotsTxtFileReader;

    @Override
    public boolean allowedPerPolicy(String domain, String url) {
        /*
        Check if the domain has been visited already
        If domain has been visited already, PageSelectionPolicy will not proceed because revisit requests will be allowed
        from RevisitPolicy
         */

        if(visited(domain, url)){
            return false;
        }

        /*
        Get robots.txt values for the domain. Stop crawling if url in it
         */
        if(checkRobotsDisallowed(domain,url)) {
            return false;
        }

        return true;
    }

    @Override
    public SelectionPolicy getSelectionPolicy() {
        return SelectionPolicy.SELECTION;
    }

    private boolean checkRobotsDisallowed(String domain, String url){
        Map<String, List<String>> domainDisallowedUrlsMap = robotsTxtDao.select(domain);
        if(domainDisallowedUrlsMap.get(domain).isEmpty()){
            //May be robots.txt was never checked. Get List of disallowed URLS and save in the table
            List<String> disallowed = robotsTxtFileReader.getDisAllowedPathsForURL(domain);
            if(disallowed.isEmpty())
                disallowed.add("");
            robotsTxtDao.insert(domain,disallowed);
        }
        HashSet<String> disallowedUrls = new HashSet<String>(robotsTxtDao.select(domain).get(domain));
        return false;
    }

    private boolean visited(String domain, String url){
        String fullUrl = domain+url;
        return visitedUrlDao.select(domain,url)!=null;
    }
}
