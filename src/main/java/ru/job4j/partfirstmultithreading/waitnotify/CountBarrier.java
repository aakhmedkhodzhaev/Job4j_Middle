package ru.job4j.partfirstmultithreading.waitnotify;

public class CountBarrier implements Runnable {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
        }
    }

    public void await() {
        synchronized (monitor) {
            run();
            if (count == total) {
                monitor.notifyAll();
            } else {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < total; i++) {
            count();
        }
    }
}