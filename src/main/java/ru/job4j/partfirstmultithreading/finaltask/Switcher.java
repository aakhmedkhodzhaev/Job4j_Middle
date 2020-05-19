package ru.job4j.partfirstmultithreading.finaltask;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 20.05.2020
 * @task 1. Свитчер [#50581]
 * @aim Реализуйте объект, который хранит в себе строку или ее представление.
 * @others Имеется Тест для проверки работы программы
 */

import java.util.concurrent.Semaphore;

public class Switcher {

    private StringBuilder sb;

    public String sSb() {
        return sb.toString();
    }

    public Switcher() {
        this.sb = new StringBuilder();
    }

    public void add(int x) {
        for (int i = 0; i < 10; i++) {
            this.sb.append(x);
        }
    }
}

class ThreadFirst implements Runnable {

    private Switcher switcher;
    private Semaphore sFirst;
    private Semaphore sSecond;
    private int a;

    public ThreadFirst(Switcher switcher, Semaphore sFirst, Semaphore sSecond, int a) {
        this.switcher = switcher;
        this.sFirst = sFirst;
        this.sSecond = sSecond;
        this.a = a;
    }

    @Override
    public void run() {
        int index = 0;
        while (index < a) {
            try {
                sFirst.acquire();
                switcher.add(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                e.getMessage();
            }
            sSecond.release();
            index++;
        }
    }
}

class ThreadSecond implements Runnable {

    private Switcher switcher;
    private Semaphore sFirst;
    private Semaphore sSecond;
    private int a;

    public ThreadSecond(Switcher switcher, Semaphore sFirst, Semaphore sSecond, int a) {
        this.switcher = switcher;
        this.sFirst = sFirst;
        this.sSecond = sSecond;
        this.a = a;
    }

    @Override
    public void run() {
        int index = 0;
        while (index < a) {
            try {
                sSecond.acquire();
                switcher.add(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                e.getMessage();
            }
            sFirst.release();
            index++;
        }
    }
}