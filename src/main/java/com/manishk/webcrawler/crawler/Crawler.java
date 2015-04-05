package com.manishk.webcrawler.crawler;

import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.policy.PageSelectionPolicy;
import com.manishk.webcrawler.policy.RevisitPolicy;
import com.manishk.webcrawler.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by manish on 05/04/15.
 */
@Service
public class Crawler {

    @Autowired
    private QueueService<CrawlerMessage> queueService;

    @Autowired
    private PageSelectionPolicy pageSelectionPolicy;

    @Autowired
    private RevisitPolicy revisitPolicy;

    private List<String> seedUrls;

    @Value("${crawl.num.workers}")
    private Integer numWorkers;

    public Crawler(@Value("${crawler.seed}") String seed){
        this.seedUrls = Arrays.asList(seed.split("\\,"));
    }

    
}
