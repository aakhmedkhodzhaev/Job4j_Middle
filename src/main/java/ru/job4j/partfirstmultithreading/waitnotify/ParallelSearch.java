package ru.job4j.partfirstmultithreading.waitnotify;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 14.03.2020
 * @task 2. Обеспечить остановку потребителя.[#209928]
 * @aim Разработать механизм остановки потребителя
 * @others Имеется Тест для проверки работы программы
 */

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        Base base = new Base();
        Consumer consumer = new Consumer(base);
        Producer producer = new Producer(base);
        consumer.thread.start();
        producer.thread.start();
        consumer.thread.sleep(2000);
        consumer.thread.interrupt();

    }
}

class Base {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
}


class Consumer {

    Base base;

    Consumer(Base base) {
        this.base = base;
    }

    final Thread thread = new Thread(
            () -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(base.queue.poll());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
    );

}

class Producer {

    Base base;

    Producer(Base base) {
        this.base = base;
    }

    final Thread thread = new Thread(
            () -> {
                for (int index = 0; index != 3; index++) {
                    try {
                        base.queue.offer(index);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
}