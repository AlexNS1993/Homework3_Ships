package com.sergeiko.hometask3.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptThread extends Thread {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
