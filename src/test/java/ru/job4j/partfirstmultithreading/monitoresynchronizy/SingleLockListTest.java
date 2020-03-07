package ru.job4j.partfirstmultithreading.monitoresynchronizy;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SingleLockListTest {
    private class SingleListThread extends Thread {
        private final SingleLockList<Integer> list;

        public SingleListThread(SingleLockList<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                list.add(1);
            }
        }
    }


    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>();
        Thread first = new SingleListThread(list);
        Thread second = new SingleListThread(list);
        Thread third = new SingleListThread(list);
        Thread four = new SingleListThread(list);
        Thread five = new SingleListThread(list);
        Thread six = new SingleListThread(list);
        Thread seven = new SingleListThread(list);
        Thread eight = new SingleListThread(list);
        first.start();
        second.start();
        third.start();
        four.start();
        five.start();
        six.start();
        seven.start();
        eight.start();
        first.join();
        second.join();
        third.join();
        four.join();
        five.join();
        six.join();
        seven.join();
        eight.join();
        int result = 0;
        for (Integer value : list) {
            result += value;
        }
        assertThat(result, is(800));
    }

}