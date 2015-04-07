package com.manishk.webcrawler.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactory for worker threads
 * Created by manish on 06/04/15.
 */
public class WorkerThreadFactory implements ThreadFactory {
    private AtomicInteger counter = new AtomicInteger(0);
    private String prefix;

    /**
     *
     * @param prefix, NotNull
     */
    public WorkerThreadFactory(String prefix){
        this.prefix = prefix;
    }
    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to
     * create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,prefix+"-"+counter.incrementAndGet());
    }
}
