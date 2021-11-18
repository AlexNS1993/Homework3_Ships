package com.sergeiko.hometask3.elements;

import com.sergeiko.hometask3.exception.PortException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private final static Logger LOGGER = LogManager.getLogger();

    private static Lock terminalLock = new ReentrantLock();
    private static Lock pierLock = new ReentrantLock();
    private static Port instance = new Port();
    private static AtomicBoolean terminalExists = new AtomicBoolean(false);

    public final int CAPACITY = 10000;
    public final int PIERS_AMOUNT = 4;

    private int containers;
    private Deque<Dock> docks;
    private Semaphore throughput;


    public static Port getInstance() {
        if (!terminalExists.get()) {
            terminalLock.lock();
            try {
                if (instance == null) {
                    instance = new Port();
                    terminalExists.set(true);
                }

            } finally {
                terminalLock.unlock();
            }
        }

        return instance;
    }

    private Port() {
        containers = new Random().nextInt(CAPACITY + 1);
        docks = new LinkedList<>();
        throughput = new Semaphore(PIERS_AMOUNT, true);
        while (docks.size() < PIERS_AMOUNT) {
            docks.push(new Dock(docks.size() + 1));
        }
    }

    public int getContainers() {
        return containers;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public Dock moor() throws PortException {
        Dock dock = null;
        try {
            throughput.acquire();
            TimeUnit.SECONDS.sleep(1);
            pierLock.lock();
            dock = docks.poll();
            pierLock.unlock();
            return dock;
        } catch (NoSuchElementException ex) {
            LOGGER.log(Level.ERROR, "No pier is available", ex);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Mooring is interrupted", ex);
        }
        throw new PortException("Unsuccessful mooring");
    }

    public void unmoor(Dock dock) {
        try {
            if (dock != null) {
                pierLock.lock();
                docks.push(dock);
                pierLock.unlock();
            }
            TimeUnit.SECONDS.sleep(1);
            throughput.release();
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Unmooring is interrupted", ex);
        } finally {
            LOGGER.log(Level.INFO, dock.toString() + " is free");
        }
    }

    @Override
    public String toString() {
        return "Harbor{" +
                containers + " containers, " +
                docks.size() + " piers available" +
                '}';
    }
}
