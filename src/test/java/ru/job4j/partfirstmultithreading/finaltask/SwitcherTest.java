package ru.job4j.partfirstmultithreading.finaltask;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;


public class SwitcherTest {

    @Test
    public void whenMakeTwoCycles() {

        Switcher sw = new Switcher();
        Semaphore sf = new Semaphore(1);
        Semaphore ss = new Semaphore(0);
        int a = 2;

        ExecutorService es = Executors.newFixedThreadPool(2);
        es.submit(new ThreadFirst(sw, sf, ss, a));
        es.submit(new ThreadSecond(sw, sf, ss, a));
        es.shutdown();

        while (!es.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(sw.sSb().equals("1111111111222222222211111111112222222222"));
    }

}