package ru.job4j.partfirstmultithreading.nonblockingalgoritm;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CASQueueTest {

    @Test
    public void when3PushThen3Poll() {
        CASQueue<Integer> cq = new CASQueue<>();
        cq.push(1);
        cq.push(2);
        cq.push(3);
        assertThat(cq.poll(), is(3));
        assertThat(cq.poll(), is(2));
        assertThat(cq.poll(), is(1));
    }

    @Test
    public void when1PushThen1Poll() {
        CASQueue<Integer> cq = new CASQueue<>();
        cq.push(1);
        assertThat(cq.poll(), is(1));
    }

    @Test
    public void when2PushThen2Poll() {
        CASQueue<Integer> cq = new CASQueue<>();
        cq.push(1);
        cq.push(2);
        assertThat(cq.poll(), is(2));
        assertThat(cq.poll(), is(1));
    }
}