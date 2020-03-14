package ru.job4j.partfirstmultithreading.waitnotify;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 14.03.2020
 * @task 1. Реализовать шаблон Producer Consumer.[#209930]
 * @aim Реализовать собственную версию bounded blocking queue
 * @others Имеется Тест для проверки работы программы
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;


@ThreadSafe
public class SimpleBlockingQueue<T> {

    /**
     * Очередь для хранения элементов
     * */
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * Добавляем элемент если очереди нет, если есть выставляем wait();
     * */
    public synchronized void offer(T value) {
        while (queue.size() >= limit) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
        this.queue.offer(value);
        notifyAll();
    }

    /**
     * Возвращаем элемент из очереди, если в очереди нет элемента, выставляем поток в состоянии wait();
     * */
    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        notify();
        return queue.poll();
    }
}