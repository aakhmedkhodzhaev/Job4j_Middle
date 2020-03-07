package ru.job4j.partfirstmultithreading.monitoresynchronizy;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 06.03.2020
 * @task 3. ThreadSafe динамический список [#209943]
 * @aim Работа Динамического массива в многопоточной среде
 * @others Имеется Тест для проверки работы программы
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;


@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final DynamicList<T> arrayList;

    public SingleLockList() {
        this.arrayList = new DynamicList<>();
    }

    public synchronized void add(T value) {
        this.arrayList.add(value);
    }

    public synchronized T get(int index) {
        return this.arrayList.get(index);
    }

    public synchronized DynamicList<T> copy() {
        DynamicList<T> list = new DynamicList<>();
        for (T value : this.arrayList) {
            list.add(value);
        }
        return list;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return this.copy().iterator();
    }
}