package com.sergeiko.hometask3.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleThread extends Thread{
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void run() {
        try{
            if(isDaemon()){
                LOGGER.info("Старт потока-демона");
                Thread.sleep(5005);
            }else{
                LOGGER.info("Старт обычного потока");
                Thread.sleep(5000);
            }
        }catch (InterruptedException  ex){
            LOGGER.log(Level.ERROR,ex.getMessage());
        }finally {
            if(isDaemon()){
                LOGGER.info("Завершение потока-демона");
            }else{
                LOGGER.info("Завершение обычного потока");
            }
        }
    }
}
