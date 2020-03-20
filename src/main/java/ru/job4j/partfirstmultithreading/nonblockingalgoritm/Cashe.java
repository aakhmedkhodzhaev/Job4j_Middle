package ru.job4j.partfirstmultithreading.nonblockingalgoritm;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 15.03.2020
 * @task 1. Неблокирующий кеш[#209946]
 * @aim Сделать кеш для хранение моделей
 * @others Имеется Тест для проверки работы программы
 */

import java.util.concurrent.ConcurrentHashMap;

public class Cashe {

    ConcurrentHashMap<Integer, Base> cashe = new ConcurrentHashMap<>();

    static class Base {
        private int id;
        private int version;

        public Base(int id, int version) {
            this.id = id;
            this.version = version;
        }

        public void modify() {
            this.version++;
        }

        public int getVersion() {
            return version;
        }

        public int getId() {
            return id;
        }
    }

    public void add(Base model) {
        cashe.put(model.getId(), model);
    }

    public void update(Base model) {
        cashe.computeIfPresent(model.getId(),
                (key, value) -> {
                    if (model.getVersion() != value.getVersion()) {
                        throw new OptimisticException();
                    }
                    value.modify();
                    return value;
                }
        );
    }

    public boolean delete(Base model) {
        return cashe.remove(model.getId(), model);
    }

}