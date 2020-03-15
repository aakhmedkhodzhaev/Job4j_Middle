package ru.job4j.partfirstmultithreading.nonblockingalgoritm;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CasheTest {

    @Test
    public void whenThrowException() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread = new Thread(
                () -> {
                    try {
                        throw new RuntimeException("Throw Exception in Thread");
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();
        thread.join();
        assertThat(ex.get().getMessage(), is("Throw Exception in Thread"));
    }

    @Test
    public void crud() {
        Cashe st = new Cashe();
        st.add(new Cashe.Base(1, 1));
        st.add(new Cashe.Base(2, 1));
        st.update(new Cashe.Base(1, 1));
        assertThat(st.cashe.get(1).getVersion(), is(2));
        assertThat(st.cashe.get(2).getVersion(), is(1));
        st.update(new Cashe.Base(2, 1));
        assertThat(st.cashe.get(2).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenOptimisticException() {
        Cashe st = new Cashe();
        st.add(new Cashe.Base(1, 1));
        st.update(new Cashe.Base(1, 1));
        st.update(new Cashe.Base(1, 2));
        st.update(new Cashe.Base(1, 2));
        st.delete(new Cashe.Base(2, 2));
    }

}