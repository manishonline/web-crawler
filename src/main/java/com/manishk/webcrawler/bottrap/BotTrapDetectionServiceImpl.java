package com.manishk.webcrawler.bottrap;

import com.manishk.webcrawler.entity.CrawlerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * BotTrap detection service Impl
 * Created by manish on 07/04/15.
 */
@Service
public class BotTrapDetectionServiceImpl implements BotTrapDetectionService {

    private Integer maxAllowedLevel;

    @Autowired
    public BotTrapDetectionServiceImpl(@Value("${crawl.maxdepth}") Integer maxAllowedLevel){
        this.maxAllowedLevel = maxAllowedLevel;
    }
    @Override
    public boolean isTraversalSafe(CrawlerMessage message)
    {
        return message.getCrawlLevel()==null || message.getCrawlLevel() <= maxAllowedLevel;
    }
}
