package com.sergeiko.hometask3.exception;

public class PortException extends Exception {
    public PortException() {
    }

    public PortException(String s) {
        super(s);
    }

    public PortException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PortException(Throwable throwable) {
        super(throwable);
    }
}
