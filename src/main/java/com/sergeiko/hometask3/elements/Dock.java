package com.sergeiko.hometask3.elements;

import com.sergeiko.hometask3.exception.PortException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dock {
    private static Logger LOGGER = LogManager.getLogger();
    private static Lock lock = new ReentrantLock();

    private int id;

    public Dock(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void serve(Ship ship) {
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            ship.getShipState().action(ship);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Action was interrupted " + ship.toString());
        } catch (PortException ex) {
            LOGGER.log(Level.ERROR, ship.toString() + ex.getMessage(), ex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Pier{" +
                "id=" + id +
                '}';
    }
}
