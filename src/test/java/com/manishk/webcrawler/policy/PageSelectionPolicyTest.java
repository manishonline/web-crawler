package com.manishk.webcrawler.policy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by manish on 04/04/15.
 */
public class PageSelectionPolicyTest {

    @Test
    public void testDocumentUnsupportedMimeType(){
        Element docBody = null;
        try {
            Document document = Jsoup.connect("https://rtdmgb.files.wordpress.com/2015/02/ranveersingh-arjunkapoor-aib.jpg").get();
             docBody = document.body();
            Elements links = docBody.select("a[href]");
            for(Element link : links){
                Attributes attrs = link.attributes();
                for(Attribute attr  :attrs) {
                    if ("href".equals(attr.getKey()))
                        System.out.println(attr.getValue());
                }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        Assert.assertNull(docBody);
    }

    @Test
    public void testDocumentFetch(){
        Element docBody =null;
        Elements links =null;
        try {
            Document document = Jsoup.connect("http://www.greatbong.net").get();
             docBody = document.body();
            System.out.println(docBody.text());
             links = docBody.select("a[href]");
            for(Element link : links){
                Attributes attrs = link.attributes();
                for(Attribute attr  :attrs) {
                    if ("href".equals(attr.getKey()))
                        System.out.println(attr.getValue()+ " - "+attr.getValue().hashCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(docBody);
        Assert.assertNotNull(links);
    }
    
}
