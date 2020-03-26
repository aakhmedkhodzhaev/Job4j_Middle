package ru.job4j.partfirstmultithreading.pool;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 21.03.2020
 * @task 1. Реализовать ThreadPool[#209926]
 * @aim Хранилища для ресурсов
 * @others Имеется Тест для проверки работы программы
 */

import ru.job4j.partfirstmultithreading.waitnotify.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    private boolean isActive = true;

    public void work(Runnable job) throws InterruptedException {
        if (!isActive) {
            throw new RejectedExecutionException();
        }
        tasks.offer(job); // добавляем задачи в блокирующую очередь
    }

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        while (threads.size() != size) { // ограничение по каличеству ядер в системе
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    if (!tasks.isEmpty()) {
                        try {
                            tasks.poll().run(); // получаем задачу их очереди
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            );
            threads.add(thread);
            thread.start();
        }
    }

    public void shutdown() {
        isActive = false;
        threads.forEach(Thread::interrupt);
    }
}