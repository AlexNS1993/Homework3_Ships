package com.sergeiko.hometask3.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PriorThread extends Thread {

    //private static final Logger LOGGER = LogManager.getLogger();

    public PriorThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            //LOGGER.info(this.getName()+"#"+i);
            System.out.println(this.getName()+"#"+i);
            try {
                Thread.sleep(10);
            }catch (InterruptedException ex){
                //LOGGER.log(Level.ERROR,ex.getMessage());
                System.err.print(ex);
            }
        }
    }
}
