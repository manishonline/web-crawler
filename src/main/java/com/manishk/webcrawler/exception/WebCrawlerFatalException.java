package com.manishk.webcrawler.exception;

/**
 * Created by manish on 03/04/15.
 */
public class WebCrawlerFatalException extends Exception {

    WebCrawlerFatalException(Exception e){
        super(e);
    }
}
