package com.sergeiko.hometask3.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JoinThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    public JoinThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        String nameT = getName();
        long timeOut = 0;
        LOGGER.info("Старт потока : " + nameT);
        try{
            switch (nameT.toUpperCase()){
                case "FIRST":
                    timeOut = 5_000;
                    break;
                case "SECOND":
                    timeOut = 1_000;
                    break;
            }
            Thread.sleep(timeOut);
            LOGGER.info("Завершение потока : "+nameT);
        }catch (InterruptedException ex){
            LOGGER.log(Level.ERROR,ex.getMessage());
        }
    }
}
