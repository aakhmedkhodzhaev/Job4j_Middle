package ru.job4j.partfirstmultithreading.threads.count;

public class ThreadCounter {

    public static class CounterThread extends Thread {

        private final Count counter;

        public CounterThread(final Count counter) {
            this.counter = counter;
        }

        public void run() {
            while (counter.get() < 2000) {
                for (int i = 0; i < 10; i++) {
                    counter.increment();
//            System.out.println(counter.get());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Count counter = new Count();
        Thread threadA = new CounterThread(counter);
        Thread threadB = new CounterThread(counter);

        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        System.out.println(counter.get());

    }
}