package com.sergeiko.hometask3.elements;

import com.sergeiko.hometask3.exception.PortException;
import com.sergeiko.hometask3.condition.ShipCondition;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship extends Thread {

    private final static Logger LOGGER = LogManager.getLogger();

    private int id;
    private int capacity;
    private int containers;
    private ShipCondition state;
    private Dock dock;

    public Ship(int id, int capacity, int containers, String state) {
        this.id = id;
        this.capacity = capacity;
        this.containers = containers;
        this.state = ShipCondition.valueOf(state.toUpperCase());
        LOGGER.log(Level.INFO, this.toString() + " is ready for mooring");
    }

    @Override
    public void run() {
        try {

            dock = Port.getInstance().moor();
            LOGGER.log(Level.INFO, this.toString() + " has moored to " + dock.toString());

            dock.serve(this);

            LOGGER.log(Level.INFO, this.toString() + " is going away");
            Port.getInstance().unmoor(this.dock);
            this.dock = null;

        } catch (PortException ex) {
            LOGGER.log(Level.ERROR, this.toString() + ex.getMessage());
        }
    }

    public int getShipId() {
        return id;
    }

    public void setShipId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getContainers() {
        return containers;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public ShipCondition getShipState() {
        return state;
    }

    public void setState(ShipCondition state) {
        this.state = state;
    }


    public Dock getPier() {
        return dock;
    }

    public void setPier(Dock dock) {
        this.dock = dock;
    }

    @Override
    public String toString() {
        return "Ship " + id + " {" +
                containers + ", " +
                state +
                '}';
    }
}
