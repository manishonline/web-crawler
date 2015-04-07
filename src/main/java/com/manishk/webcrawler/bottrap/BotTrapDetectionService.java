package com.manishk.webcrawler.bottrap;

import com.manishk.webcrawler.entity.CrawlerMessage;

/**
 * Interface for bot trap detection service
 * Created by manish on 07/04/15.
 */
public interface BotTrapDetectionService {

    boolean isTraversalSafe(CrawlerMessage message);
}
