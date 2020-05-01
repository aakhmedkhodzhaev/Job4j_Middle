package ru.job4j.partfirstmultithreading.waitnotify;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            monitor.notifyAll();
            count++;
        }
    }

    public void await() {
        synchronized (monitor) {
            if (count == total) {
                // Я не совсем понял что должно быть здесь
            } else {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}