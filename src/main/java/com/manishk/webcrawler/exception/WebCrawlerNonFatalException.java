package com.manishk.webcrawler.exception;

/**
 * Created by manish on 03/04/15.
 */
public class WebCrawlerNonFatalException extends RuntimeException {
    public WebCrawlerNonFatalException(Exception e){
        super(e);
    }
}
