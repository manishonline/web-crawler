package com.manishk.webcrawler.indexer;

import com.manishk.webcrawler.entity.CrawledDocument;
import org.springframework.stereotype.Service;

/**
 * Document indexer interface
 * Created by manish on 04/04/15.
 */
@Service
public class DocumentIndexerImpl  implements  DocumentIndexer{
    @Override
    public boolean index(CrawledDocument document) {
        return false;
    }
}
