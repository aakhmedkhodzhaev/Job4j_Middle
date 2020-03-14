package ru.job4j.waitnotify;

import org.junit.Test;
import ru.job4j.partfirstmultithreading.waitnotify.SimpleBlockingQueue;


public class SimpleBlockingQueueTest {

    class Producer implements Runnable {
        private final SimpleBlockingQueue<Integer> queue;

        Producer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.offer(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }
    }

    class Consumer implements Runnable {

        private final SimpleBlockingQueue<Integer> queue;

        Consumer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.poll();
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }
    }


    @Test
    public void whenCheckThread() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

    }

}