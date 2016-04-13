package com.pillar.skynet.facekiosk.capture;

public abstract class StoppableRunnable implements Runnable {

    private boolean running;

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

}
