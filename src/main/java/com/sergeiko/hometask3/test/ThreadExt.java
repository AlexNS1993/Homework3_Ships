package com.sergeiko.hometask3.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadExt extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    public ThreadExt() {
        LOGGER.debug(this.getState());
    }

    @Override
    public void run() {

        for(int i=0;i<10;i++){
            LOGGER.info("Talking"+" "+this.getState());

            try{
                Thread.sleep(7);
            }catch (InterruptedException ex){
                LOGGER.debug(this.getState());
                LOGGER.log(Level.ERROR,ex.getMessage());
            }
        }
    }
}
