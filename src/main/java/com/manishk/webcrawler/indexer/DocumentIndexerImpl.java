package com.manishk.webcrawler.indexer;

import org.springframework.stereotype.Service;

/**
 * Created by manish on 04/04/15.
 */
@Service
public class DocumentIndexerImpl  implements  DocumentIndexer{
    @Override
    public boolean index(String docId, Object document) {
        return false;
    }
}
