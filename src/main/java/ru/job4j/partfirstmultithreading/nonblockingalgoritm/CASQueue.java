package ru.job4j.partfirstmultithreading.nonblockingalgoritm;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 05.05.2020
 * @task 0. CAS - операции [#6859]
 * @aim Сделать CAS класс потокобезопасным
 * @others Имеется Тест для проверки работы программы
 */

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASQueue<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();
    private final AtomicReference<Node<T>> tail = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> ref;
        do {
            ref = head.get();
            temp.next = ref;
        } while (!head.compareAndSet(ref, temp));
    }

    public T poll() {
        Node<T> ref;
        Node<T> temp;
        do {
            ref = head.get();
            if (ref == null) {
                throw new UnsupportedOperationException("Queue is not impl.");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
        ref.next = null;
        return ref.value;
    }

    private static final class Node<T> {
        final T value;

        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}