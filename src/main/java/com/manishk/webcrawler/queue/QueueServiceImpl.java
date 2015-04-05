package com.manishk.webcrawler.queue;

import com.manishk.webcrawler.exception.WebCrawlerNonFatalException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by manish on 03/04/15.
 */
@Service
public class QueueServiceImpl<T> implements QueueService<T> {

    private BlockingQueue<T> backingQueue;

    QueueServiceImpl(){
        backingQueue = new LinkedBlockingQueue<T>();
    }

    @Override
    public void addToQueue(T o)  {
        try {
            backingQueue.put(o);
        } catch (InterruptedException e) {
            throw new WebCrawlerNonFatalException(e);
        }
    }

    @Override
    public T readFromQueue()  {
        try {
            return backingQueue.take();
        } catch (InterruptedException e) {
            throw new WebCrawlerNonFatalException(e);
        }
    }

    @Override
    public void addToQueue(List<T> list) {
        for(T t : list){
            try {
                backingQueue.put(t);
            } catch (InterruptedException e) {
                throw new WebCrawlerNonFatalException(e);
            }
        }
    }
}
