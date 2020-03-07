package ru.job4j.partfirstmultithreading.threads.count;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ThreadCounterTest {

    private class ThreadCount extends Thread {
        private final Count counter;

        private ThreadCount(final Count counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            this.counter.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        //Создаем счетчик.
        final Count counter = new Count();

        //Создаем нити.
        Thread first = new ThreadCount(counter);
        Thread second = new ThreadCount(counter);

        //Запускаем нити.
        first.start();
        second.start();

        //Заставляем главную нить дождаться выполнения наших нитей.
        first.join();
        second.join();

        //Проверяем результат.
        assertThat(counter.get(), is(2));
    }
}