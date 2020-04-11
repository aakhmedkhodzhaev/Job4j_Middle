package ru.job4j.partfirstmultithreading.waitnotify;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 11.04.2020
 * @task 0. Управление нитью через wait. [#6858]
 * @aim Разработайте класс, который блокирует выполнение по условию счетчика
 * @others Возможно разработка Теста для проверки работы программы
 */

public class MultiUserCount {
    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(10);
        Thread first = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " Started");
                },
                "First"
        );
        first.start();
    }
}