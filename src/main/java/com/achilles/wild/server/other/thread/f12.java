package com.achilles.wild.server.other.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class f12 implements Runnable{

    private final static Logger LOG = LoggerFactory.getLogger(f12.class);

    private String name;

    public f12(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 13; i++) {
            LOG.info(name + "  RUN  :  " + i);
            //try {
            //    Thread.sleep(300);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }
}
