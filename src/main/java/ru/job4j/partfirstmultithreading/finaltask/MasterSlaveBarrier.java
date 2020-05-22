package ru.job4j.partfirstmultithreading.finaltask;

public class MasterSlaveBarrier {
    private int cnt = 0;

    static class Counter {
        private Long counter = 0L;

        public synchronized void incrementCounter() {
            counter++;
            System.out.println(counter);
        }
    }

    Counter counter = new Counter();

    public synchronized void tryMaster() {
        while (cnt >= 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cnt++;
        counter.incrementCounter();
        notify();
    }

    public synchronized void trySlave() {
        while (cnt < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cnt--;
        counter.incrementCounter();
        notify();
    }

    public void doneMaster() {
        for (int i = 0; i < 1; i++) {
            tryMaster();
        }
    }

    public void doneSlave() {
        for (int i = 0; i < 1; i++) {
            trySlave();
        }
    }

}