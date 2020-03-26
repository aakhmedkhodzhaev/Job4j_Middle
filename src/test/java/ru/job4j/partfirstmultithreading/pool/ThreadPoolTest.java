package ru.job4j.partfirstmultithreading.pool;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTest {

    ThreadPool tp = new ThreadPool();
    List<Integer> result = new CopyOnWriteArrayList<>();
//  List<Integer> expected = new ArrayList<Integer>(List.of(0, 2, 4, 6, 8));
//  List<Integer> expected = List.of(0, 2, 4, 6, 8);

    AtomicInteger count = new AtomicInteger(0);

    Runnable task = () -> {
        int num = count.getAndIncrement();
        result.add(num + num);
    };

    @Test
    public void whenTasks() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            tp.work(task);
        }
        while (result.size() < 5) {
            Thread.sleep(1000);
        }
        assertThat(result.size(), is(5));
//      assertThat(result, is(expected));
    }

    @Test(expected = RejectedExecutionException.class)
    public void whenShutdownWork() throws InterruptedException {
        tp.shutdown();
        tp.work(task);
    }

}