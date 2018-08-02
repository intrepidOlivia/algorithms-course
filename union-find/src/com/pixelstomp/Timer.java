package com.pixelstomp;

/**
 *
 */
public class Timer {
    private long start;
    private long stop;


    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
    }

    /**
     * @return the time, in milliseconds, last recorded by the start() and stop() functions. Will return 0 if time was not recorded appropriately.
     */
    public long getElapsed() {
        if (stop < start) {
            return 0;
        }
        return ((stop - start) / 1000000);
    }
}
