package com.manishk.webcrawler.worker;

import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.queue.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by manish on 04/04/15.
 */
public class WebCrawlerWorker implements Runnable {

    private  static  final Logger log = LoggerFactory.getLogger(WebCrawlerWorker.class);

    private  final QueueService<CrawlerMessage> queueService;

    public WebCrawlerWorker(QueueService<CrawlerMessage> queueService){
        this.queueService = queueService;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        CrawlerMessage message;
        message = queueService.readFromQueue();

    }
}
