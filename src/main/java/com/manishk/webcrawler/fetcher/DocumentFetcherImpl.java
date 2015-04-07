package com.manishk.webcrawler.fetcher;

import com.manishk.webcrawler.entity.CrawledDocument;
import com.manishk.webcrawler.entity.CrawlerMessage;
import com.manishk.webcrawler.util.UrlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 05/04/15.
 */
@Service
public class DocumentFetcherImpl implements DocumentFetcher {

    private static final Logger log = LoggerFactory.getLogger(DocumentFetcherImpl.class);
    @Override
    public CrawledDocument fetch(CrawlerMessage message) {
        log.info("Crawl Started, Domain=["+message.getDomain()+"], Resource=["+message.getResource()+"]");
        return fetchURL(message.getDomain(),message.getResource());
    }

    private CrawledDocument fetchURL(String domain, String resource){
        CrawledDocument crawledDocument = null;
        try {
            String url = domain+resource;
            Document document = Jsoup.connect(UrlUtils.getURLWithProtocol(url)).get();
            Element docBody = document.body();
            Elements links = docBody.select("a[href]");
            List<String> childLinks = new ArrayList<String>();
            for(Element link : links){
                Attributes attrs = link.attributes();
                for(Attribute attr  :attrs) {
                    if ("href".equals(attr.getKey()))
                        childLinks.add(attr.getValue());
                }
            }
            crawledDocument = new CrawledDocument(domain,resource,docBody.text(),childLinks);
            log.info("Crawl Finished, Domain=["+domain+"], Resource=["+resource+"], ChildLinks=["+childLinks.size()+"]");
        } catch (IOException e) {

        }
        return crawledDocument;
    }
}
