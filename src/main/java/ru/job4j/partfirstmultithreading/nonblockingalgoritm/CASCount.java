package ru.job4j.partfirstmultithreading.nonblockingalgoritm;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 26.06.2020
 * @task 0. CAS - операции [#6859]
 * @aim Реализовать неблокирующий счетчик
 * @others Возможно добавление Теста для проверки работы программы
 */

import java.util.concurrent.atomic.AtomicReference;

public class CASCount<T> {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer current = count.get();
        Integer next = current + 1;
        count.compareAndSet(current, next);
    }

    public AtomicReference<Integer> get() {
        return count;
    }
}