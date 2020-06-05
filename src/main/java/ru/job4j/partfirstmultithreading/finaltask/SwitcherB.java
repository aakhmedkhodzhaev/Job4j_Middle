package ru.job4j.partfirstmultithreading.finaltask;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 23.05.2020
 * @task 1. Свитчер [1108#209950]
 * @aim В этом задании нужно многопоточные нити сделать последовательными
 * @others Возможно разработка Тест для проверки работы программы
 */

public class SwitcherB {

    public static void main(String[] args) throws InterruptedException {

        MasterSlaveBarrier ms = new MasterSlaveBarrier();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        ms.tryMaster();
                        System.out.println("Thread A: ");
                        try {
                            ms.doneMaster();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        ms.trySlave();
                        System.out.println("Thread B: ");
                        try {
                            ms.doneSlave();
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}