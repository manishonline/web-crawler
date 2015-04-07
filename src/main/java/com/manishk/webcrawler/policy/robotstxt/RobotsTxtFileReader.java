package com.manishk.webcrawler.policy.robotstxt;

import com.google.common.base.Preconditions;
import com.manishk.webcrawler.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 03/04/15.
 */
@Service
public class RobotsTxtFileReader {
    private static final Logger log = LoggerFactory.getLogger(RobotsTxtFileReader.class);
    private String userAgent;

    private String ROBOTS_TXT = "/robots.txt";

    @Autowired
    public RobotsTxtFileReader(@Value("${crawl.useragent}")String userAgent){
        this.userAgent = userAgent;
    }

    public List<String> getDisAllowedPathsForURL(String domain){
        Preconditions.checkNotNull(domain,"Domain can not be null");
        URL robotURL = null;
        List<String> disallowed = new ArrayList<String>();
        try {
            robotURL = new URL(UrlUtils.getURLWithProtocol(domain+ROBOTS_TXT));
            BufferedReader in = new BufferedReader(new InputStreamReader(robotURL.openStream()));
            String line = null;
            while((line = in.readLine()) != null) {
            }
            //TODO, Parse the response and return list ot disallowed urls
            in.close();
        } catch (MalformedURLException e) {
            log.error("Error accessing URL "+domain,e);
        } catch (IOException e) {
            log.error("Error reading from URL "+domain,e);
        }
        return disallowed;
    }
}
