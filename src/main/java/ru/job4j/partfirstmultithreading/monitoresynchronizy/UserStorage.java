package ru.job4j.partfirstmultithreading.monitoresynchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> client = new HashMap<>(); // список покупателей

    public synchronized boolean add(User user) { // добавление
        return this.client.putIfAbsent(user.getId(), user) == null; //computeIfAbsent(user.getId(), key-> user)==null;
    }

    public synchronized boolean update(User user) { // обновелние
        return this.client.computeIfPresent(user.getId(), (key, value) -> user) != null;
    }

    public synchronized boolean delete(User user) { // удаление
        return this.client.remove(user.getId()) != null;
    }

    public synchronized User getUser(int id) {  // получения id покупателя для тестирования
        return this.client.get(id);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (this.client.get(fromId).getAmount() >= amount) {
            User buyUserId = new User(fromId, client.get(fromId).getAmount() - amount);
            User receiveUserId = new User(toId, client.get(fromId).getAmount() + amount);

            this.update(buyUserId);
            this.update(receiveUserId);
            result = true;
        }
        return result;
    }

}