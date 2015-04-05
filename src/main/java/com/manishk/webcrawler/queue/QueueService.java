package com.manishk.webcrawler.queue;

import com.manishk.webcrawler.exception.WebCrawlerNonFatalException;

import java.util.List;

/**
 * Created by manish on 03/04/15.
 */
public interface QueueService<T> {

    void addToQueue(T t) throws WebCrawlerNonFatalException;

    T readFromQueue() throws WebCrawlerNonFatalException;

    void addToQueue(List<T> list) throws WebCrawlerNonFatalException;

}
