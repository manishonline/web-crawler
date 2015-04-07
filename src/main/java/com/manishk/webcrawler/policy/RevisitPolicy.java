package com.manishk.webcrawler.policy;

import com.manishk.webcrawler.persistence.LastVisitedDao;
import com.manishk.webcrawler.persistence.RobotsTxtDao;
import com.manishk.webcrawler.persistence.VisitedUrlDao;
import com.manishk.webcrawler.util.SelectionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by manish on 03/04/15.
 */
@Service
public class RevisitPolicy implements WebCrawlerPolicy {

    @Autowired
    private PageSelectionPolicy pageSelectionPolicy;

    @Value("${crawl.revisit.interval.days}")
    private Integer revisitIntervalDays;

    @Autowired
    private LastVisitedDao lastVisitedDao;

    @Autowired
    private VisitedUrlDao visitedUrlDao;

    @Autowired
    private RobotsTxtDao robotsTxtDao;

    @Override
    public boolean allowedPerPolicy(String domain, String url) {
        /*
        Check if current date is after revisitIntervalDays after last visit
         */
        if(!revisit(domain, url))
        {
            return false;
        }
        /*
        If revisit is allowed, clear visited URLS for the current domain
        PageSelectionPolicy will be checked if they are met for the current fetch
         */
        visitedUrlDao.delete(domain);

        /*
        Delete robots.txt list too
         */
        robotsTxtDao.delete(domain);

        /*
        Check if current fetch is allowed as per the normal selection policy
         */
        if(!pageSelectionPolicy.allowedPerPolicy(domain, url)){
            return false;
        }

        return true;
    }

    @Override
    public SelectionPolicy getSelectionPolicy() {
        return SelectionPolicy.REVISIT;
    }

    private boolean revisit(String domain, String url){
        Date lastVisitedDate = lastVisitedDao.select(domain);
        Date currDate = new Date();
        return addDays(currDate,-revisitIntervalDays).after(lastVisitedDate);
    }

    private Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
