package ru.job4j.partfirstmultithreading.monitoresynchronizy;

/* Динамический Массив для задачи SingleLockList*/

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicList<E> implements Iterable<E> {

    private Object[] cellsSize = new Object[10];
    private int position = 0;
    private int count = 0;

    public void checkSize() {
        if (position == cellsSize.length) {
            int newsize = 2 * cellsSize.length;
            Object[] newCellsize = new Object[newsize];
            System.arraycopy(cellsSize, 0, newCellsize, 0, cellsSize.length);
            cellsSize = newCellsize;
        }
    }

    public void add(E value) {
        checkSize();
        cellsSize[position++] = value;
    }

    public E get(int index) {
        return (E) cellsSize[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int expectedCount = count;
            int index = 0;

            @Override
            public boolean hasNext() {
                if (expectedCount != count) {
                    throw new ConcurrentModificationException();
                }
                return index < position;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) cellsSize[index++];
            }

        };

    }

}
