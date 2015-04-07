package com.manishk.webcrawler;

import com.manishk.webcrawler.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bootstrapping class containing main method
 * Created by manish on 03/04/15.
 */
public class BootStrap {
    private static final Logger log = LoggerFactory.getLogger(BootStrap.class);

    private static ClassPathXmlApplicationContext context;

    public static void main (String[] args)
    {
        log.info("Starting crawler Bootstrap..");
        context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        log.info("Application context loaded. Starting crawler");

        Crawler crawler = (Crawler) context.getBean("crawler");

        //start crawl
        crawler.startCrawl();

        //Live forever until interrupted

        Object o = new Object();

        try {
            o.wait();
        } catch (InterruptedException e) {
            log.error("Interrupted. Attempting to exit");
            context.close();
        }

    }
}
