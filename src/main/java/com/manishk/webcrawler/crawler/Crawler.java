package com.manishk.webcrawler.crawler;

import com.manishk.webcrawler.bottrap.BotTrapDetectionService;
import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.fetcher.DocumentFetcher;
import com.manishk.webcrawler.indexer.DocumentIndexer;
import com.manishk.webcrawler.policy.PageSelectionPolicy;
import com.manishk.webcrawler.policy.RevisitPolicy;
import com.manishk.webcrawler.policy.WebCrawlerPolicy;
import com.manishk.webcrawler.queue.QueueService;
import com.manishk.webcrawler.revisit.RevisitUrlService;
import com.manishk.webcrawler.util.SelectionPolicy;
import com.manishk.webcrawler.util.UrlUtils;
import com.manishk.webcrawler.util.WorkerThreadFactory;
import com.manishk.webcrawler.worker.RevisitWebCrawlWorker;
import com.manishk.webcrawler.worker.WebCrawlerWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by manish on 05/04/15.
 */
@Service("crawler")
public class Crawler {

    private static final Logger log = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private QueueService<CrawlerMessage> queueService;

    @Autowired
    private PageSelectionPolicy pageSelectionPolicy;

    @Autowired
    private RevisitPolicy revisitPolicy;

    @Autowired
    private DocumentFetcher documentFetcher;

    @Autowired
    private DocumentIndexer documentIndexer;

    @Autowired
    private BotTrapDetectionService botTrapDetectionService;

    private List<String> seedUrls;

    private Integer numWorkers;

    private ExecutorService executorService;

    private ScheduledExecutorService revisitExecutorService;

    @Autowired
    private RevisitUrlService revisitUrlService;

    private Integer revisitDelay;

    Map<SelectionPolicy,WebCrawlerPolicy> policyMap;

    @Autowired
    public Crawler(@Value("${crawler.seed}") String seed,   @Value("${crawl.num.workers}") Integer numWorkers,
                   @Value("${crawl.revisitdelay}") Integer revisitDelay){
        this.seedUrls = Arrays.asList(seed.split("\\,"));
        this.numWorkers = numWorkers;
        this.revisitDelay = revisitDelay;
        executorService = Executors.newFixedThreadPool(numWorkers,new WorkerThreadFactory("Web-crawl-worker"));
        revisitExecutorService = Executors.newScheduledThreadPool(1, new WorkerThreadFactory("revisit-web-crawl-worker"));
    }

    public void startCrawl(){

        log.info("Starting crawler..");

        policyMap= new HashMap<SelectionPolicy, WebCrawlerPolicy>();
        policyMap.put(SelectionPolicy.REVISIT,revisitPolicy);
        policyMap.put(SelectionPolicy.SELECTION,pageSelectionPolicy);

        log.info("Reading seed url list..");

        for(String seed : seedUrls){
            CrawlerMessage message = UrlUtils.convertURLtoCrawlMessage(seed,null);
            if(message!=null)
                queueService.addToQueue(message);
        }
        log.info("Pushed seed urls to message queue..");

        //Create worker threads and execute
        for(int i=0; i<numWorkers;i++){
            WebCrawlerWorker worker= new WebCrawlerWorker(queueService,policyMap,documentFetcher,documentIndexer,botTrapDetectionService);
            executorService.execute(worker);
        }

        //Create revisit policy worker and start
        RevisitWebCrawlWorker revisitWebCrawlWorker = new RevisitWebCrawlWorker(revisitUrlService,queueService);
        revisitExecutorService.schedule(revisitWebCrawlWorker,revisitDelay, TimeUnit.DAYS);


    }

}
