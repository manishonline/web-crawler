package com.manishk.webcrawler.queue;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by manish on 03/04/15.
 */

public class QueueServiceTest {

    @Test
    public void testQueueService(){
        QueueService<String>  queueService = new QueueServiceImpl<String>();
        queueService.addToQueue("Test String");
        String str = queueService.readFromQueue();
        Assert.assertTrue("Test String".equals(str));
    }

    @Test
    public void testQueueServiceWithMultiThreads(){

        final QueueService<String>  queueService = new QueueServiceImpl<String>();
        final AtomicInteger produced=new AtomicInteger(0),consumed=new AtomicInteger(0);
        Runnable producer = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    queueService.addToQueue(i+"");
                    produced.incrementAndGet();
                }
            }
        };

        final Runnable consumer = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(queueService.readFromQueue());
                    consumed.incrementAndGet();
                }
            }
        };

        new Thread(consumer).start();
        new Thread(producer).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }

        Assert.assertTrue(produced.intValue() == consumed.intValue());

    }
}
