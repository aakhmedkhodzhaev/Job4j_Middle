package ru.job4j.partfirstmultithreading.monitoresynchronizy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class UserStorageTest {

    private final UserStorage stoge = new UserStorage();

    private class Transfer extends Thread {
        private final UserStorage stoge;

        private Transfer(final UserStorage stoge) {
            this.stoge = stoge;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                stoge.transfer(1, 5, 500);
            }
        }
    }

    @Before
    public void purchased() {
        stoge.add(new User(1, 100));
        stoge.add(new User(2, 200));
    }

    @Test
    public void transferWithTreads() throws InterruptedException {
        for (int i = 0; i < 7; i++) {
            new Thread(new Transfer(stoge)).start();
        }
        Thread.sleep(500);
        assertThat(stoge.getUser(1).getAmount(), is(100));
        assertThat(stoge.getUser(2).getAmount(), is(200));

    }

    @Test
    public void addUser() {
        assertThat(stoge.add(new User(1, 750)), is(false));
        assertThat(stoge.add(new User(4, 550)), is(true));
    }

    @Test
    public void deletUser() {
        assertThat(stoge.delete(new User(4, 700)), is(false));
        assertThat(stoge.getUser(4), is(nullValue())); // assertThat(stoge.getUser(3),is(nullValue()));
    }

    @Test
    public void updateUser() {
        assertThat(stoge.update(new User(1, 1000)), is(true));
        assertThat(stoge.update(new User(2, 500)), is(true));

    }

    @Test
    public void trybuy() {
        assertThat(stoge.transfer(1, 2, 100), is(true));
        assertThat(stoge.transfer(2, 1, 200), is(true));
    }

}