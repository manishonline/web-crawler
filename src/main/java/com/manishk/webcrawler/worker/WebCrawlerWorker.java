package com.manishk.webcrawler.worker;

import com.manishk.webcrawler.bottrap.BotTrapDetectionService;
import com.manishk.webcrawler.entity.CrawledDocument;
import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.fetcher.DocumentFetcher;
import com.manishk.webcrawler.indexer.DocumentIndexer;
import com.manishk.webcrawler.policy.WebCrawlerPolicy;
import com.manishk.webcrawler.queue.QueueService;
import com.manishk.webcrawler.util.SelectionPolicy;
import com.manishk.webcrawler.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by manish on 04/04/15.
 * Worker class that will run in a thread.
 * Encapsulates broadly all the tasks done by a crawler
 */
public class WebCrawlerWorker implements Runnable {

    private  static  final Logger log = LoggerFactory.getLogger(WebCrawlerWorker.class);

    private  final QueueService<CrawlerMessage> queueService;

    private final Map<SelectionPolicy,WebCrawlerPolicy> policyMap;

    private final BotTrapDetectionService botTrapDetectionService;

    private final DocumentFetcher documentFetcher;

    private final DocumentIndexer documentIndexer;


    private boolean running;

    /**
     *
     * @param queueService
     * @param policyMap
     * @param documentFetcher
     * @param documentIndexer
     * @param botTrapDetectionService
     */
    public WebCrawlerWorker(QueueService<CrawlerMessage> queueService,Map<SelectionPolicy,WebCrawlerPolicy> policyMap,
                            DocumentFetcher documentFetcher, DocumentIndexer documentIndexer,
                            BotTrapDetectionService botTrapDetectionService){
        this.queueService = queueService;
        this.policyMap = policyMap;
        this.botTrapDetectionService = botTrapDetectionService;
        this.running = true;
        this.documentFetcher = documentFetcher;
        this.documentIndexer = documentIndexer;
        ThreadLocal<String> local = new ThreadLocal<String>();
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
            while (running) {
                try {
                    CrawlerMessage message;
                    message = queueService.readFromQueue();

                    if (message == null)
                        continue;

            /*
            Checking for bot trap detection
             */
                    if (!botTrapDetectionService.isTraversalSafe(message)) {
                        log.info("Message " + message + " not safe to traverse..");
                        continue;
                    }

            /*
            Checking if fetch is allowed per policy
             */
                    WebCrawlerPolicy policy = policyMap.get(message.getSelectionPolicy());
                    if (!policy.allowedPerPolicy(message.getDomain(), message.getResource())) {
                        log.info("Current fetch not allowed for domain=" + message.getDomain() + ", resource=" + message.getResource());
                        continue;
                    }

                    CrawledDocument document = documentFetcher.fetch(message);
                    if (document != null) {
                        //Both the following steps needn't run in the same thread. Can vey well be paralleled
                        documentIndexer.index(document);

                        //push child links parsed from the page to queue
                        pushChildLinkstoQueue(document, queueService, message.getCrawlLevel());
                    }
                    //Sleep the thread for random time
                    sleepThreadForRandomTime();
                }catch (Exception e){
                    //Catch all exceptions. Log and continue with next message
                    log.error("Error processing message ",e);
                    continue;
                }
            }

    }

    private WebCrawlerPolicy getWebCrawlerPolicy(CrawlerMessage message){
        return policyMap.get(message.getSelectionPolicy());
    }

    /**
     *
     * @param document
     * @param queueService
     * @param currentMessageLevel
     */
    private void pushChildLinkstoQueue(CrawledDocument document, QueueService<CrawlerMessage> queueService, Integer currentMessageLevel){
        List<CrawlerMessage> messageList = new ArrayList<CrawlerMessage>();
        for(String link : document.getChildLinks()){
            CrawlerMessage message = UrlUtils.convertURLtoCrawlMessage(UrlUtils.getURLWithDomain(document.getDomain(),link), currentMessageLevel);
            if(message!=null)
                messageList.add(message);
        }
        if(!messageList.isEmpty())
            queueService.addToQueue(messageList);
    }

    /**
     * Sleep the thread between 1 - 1000ms
     * @throws InterruptedException
     */
    private void sleepThreadForRandomTime() throws InterruptedException {
        Thread.sleep((long)(Math.random() * 1000));
    }
}
