package com.sergeiko.hometask3.condition;

import com.sergeiko.hometask3.elements.Port;
import com.sergeiko.hometask3.elements.Ship;
import com.sergeiko.hometask3.exception.PortException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public enum ShipCondition {
    UPLOAD {
        /**
         * Load containers TO ship
         */
        @Override
        public void action(Ship ship) throws PortException, InterruptedException {

            int ship_free_space = ship.getCapacity() - ship.getContainers();

            if (Port.getInstance().getContainers() < 1) {

                throw new PortException(" Empty storage");

            } else if (Port.getInstance().getContainers() >= ship_free_space) {

                Port.getInstance().setContainers(Port.getInstance().getContainers() - ship_free_space);
                ship.setContainers(ship.getCapacity());

                TimeUnit.MILLISECONDS.sleep(ship_free_space);

                LOGGER.log(Level.INFO, ship.toString() + " UPLOAD OK");

            } else if (Port.getInstance().getContainers() < ship_free_space) {

                LOGGER.log(Level.INFO,
                        "There is not enough containers in the storage for " + ship.toString()
                                + " " + Port.getInstance().getContainers()
                );

                TimeUnit.MILLISECONDS.sleep(Port.getInstance().getContainers());

                ship.setContainers(Port.getInstance().getContainers());
                Port.getInstance().setContainers(0);

                LOGGER.log(Level.INFO, ship.toString() + " UPLOAD OK (not full)");
            }
        }
    },

    UNLOAD {
        /**
         * Unload containers FROM ship
         */
        @Override
        public void action(Ship ship) throws PortException, InterruptedException {
            int storage_free_space = Port.getInstance().CAPACITY - Port.getInstance().getContainers();

            if (storage_free_space < 1) {

                throw new PortException(" Storage full");

            } else if (storage_free_space >= ship.getContainers()) {

                TimeUnit.MILLISECONDS.sleep(ship.getContainers());

                Port.getInstance().setContainers(Port.getInstance().getContainers() + ship.getContainers());
                ship.setContainers(0);

                LOGGER.log(Level.INFO, ship.toString() + " UNLOAD OK");

            } else if (storage_free_space < ship.getContainers()) {

                LOGGER.log(Level.INFO,
                        "There is not enough space in the storage for " + ship.toString()
                                + " Storage space: " + storage_free_space
                );

                TimeUnit.MILLISECONDS.sleep(storage_free_space);

                Port.getInstance().setContainers(Port.getInstance().CAPACITY);
                ship.setContainers(ship.getContainers() - storage_free_space);

                LOGGER.log(Level.INFO, ship.toString() + " UNLOAD OK (not full)");
            }
        }
    },

    RELOAD {
        /**
         * Use UNLOAD & UPLOAD methods
         */
        @Override
        public void action(Ship ship) throws PortException, InterruptedException {
            UNLOAD.action(ship);
            UPLOAD.action(ship);
        }
    };

    private static Logger LOGGER = LogManager.getLogger();

    public abstract void action(Ship ship) throws PortException, InterruptedException;
}
