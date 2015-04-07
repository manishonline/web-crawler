package com.manishk.webcrawler.worker;

import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.queue.QueueService;
import com.manishk.webcrawler.revisit.RevisitUrlService;
import com.manishk.webcrawler.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by manish on 07/04/15.
 */
public class RevisitWebCrawlWorker implements  Runnable {

    private static final Logger log = LoggerFactory.getLogger(RevisitWebCrawlWorker.class);

    private RevisitUrlService revisitUrlService;

    private QueueService<CrawlerMessage> queueService;

    public RevisitWebCrawlWorker(RevisitUrlService revisitUrlService, QueueService<CrawlerMessage> queueService){
        this.revisitUrlService = revisitUrlService;
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
        List<String> links = revisitUrlService.getLinkToRevisit(new Date());
        if(!links.isEmpty()){
            List<CrawlerMessage> messageList = new ArrayList<CrawlerMessage>();
            for(String link : links){
                CrawlerMessage message = UrlUtils.convertURLtoCrawlMessage(link,0);
                if(message!= null)
                    messageList.add(message);
            }
            queueService.addToQueue(messageList);
        }
    }
}
