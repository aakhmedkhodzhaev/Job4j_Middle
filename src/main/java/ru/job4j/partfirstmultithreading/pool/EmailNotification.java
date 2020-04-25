package ru.job4j.partfirstmultithreading.pool;

/**
 * @author Akhmedkhodzhaev A.A.
 * @version 1.0 11.03.2020
 * @task 2. ExecutorService рассылка почты.[#209924]
 * @aim Реализовать сервис для рассылки почты
 * @others Имеется Тест для проверки работы программы
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Создает пул нитей по количеству доступных процессоров.

    public void emailTo(User user) {
        if (!pool.isShutdown()) {
            String subject = String.format("Notification %s to email %s.", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s.", user.getUsername());
            pool.submit(() -> this.send(subject, body, user.getEmail())); // Добавляет задачу в пул и сразу ее выполняет.
        }

    }

    public void close() {
        this.pool.shutdown();
        System.out.println("Pool is closed.");
    }

    public void send(String subject, String body, String email) {

    }

    class User {
        private String username;
        private String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }
}