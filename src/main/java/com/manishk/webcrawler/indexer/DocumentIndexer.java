package com.manishk.webcrawler.indexer;

import com.manishk.webcrawler.entity.CrawledDocument;

/**
 * Interface for document indexer
 * Created by manish on 04/04/15.
 */
public interface DocumentIndexer {

    boolean index(CrawledDocument document);
}
