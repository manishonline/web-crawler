package com.manishk.webcrawler.indexer;

/**
 * Created by manish on 04/04/15.
 */
public interface DocumentIndexer {

    boolean index(String docId, Object document);
}
