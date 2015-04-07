package com.manishk.webcrawler.util;

import com.google.common.base.Preconditions;
import com.manishk.webcrawler.entity.CrawlerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class to URL related functions
 * Created by manish on 06/04/15.
 */
public class UrlUtils {

    private static final Logger log = LoggerFactory.getLogger(UrlUtils.class);

    /**
     *
     * @param url
     * @param crawllevel
     * @return
     */
    public static CrawlerMessage convertURLtoCrawlMessage(String url,Integer crawllevel){
        Preconditions.checkNotNull(url);
        CrawlerMessage message = null;
        try {
            URL url1 = new URL(getURLWithProtocol(url));
            message = new CrawlerMessage(url1.getHost(),url1.getPath(),crawllevel==null?1:crawllevel+1);
        } catch (MalformedURLException e) {
            log.error("Error parsing "+url,e);
        }
        return message;
    }

    /**
     *
     * @param domain
     * @param resource
     * @return
     */
    public static String getURLWithDomain(String domain, String resource){
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(resource);
        if(resource.startsWith("/"))
            return domain+resource;
        else
            return resource;
    }

    /**
     *
     * @param url
     * @return
     */
    public static String getURLWithProtocol(String url){
        Preconditions.checkNotNull(url);
        if(url.startsWith("http://") || url.startsWith("https://"))
            return url;
        else
            return "http://"+url;
    }
}
