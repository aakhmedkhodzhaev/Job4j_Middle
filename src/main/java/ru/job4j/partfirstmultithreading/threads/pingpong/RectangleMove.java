package ru.job4j.partfirstmultithreading.threads.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    // Квадратик
    private final Rectangle rect;
    // V = 1 pix/ms, скорость перемещения
    int i = 1;
    double y = 0.1;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // выбор направления перемещения
            this.rect.setX(this.rect.getX() + this.i);
            if (this.rect.getX() > 300 || this.rect.getX() < 0) {
                this.i = -this.i;
            }

            // выбор направления перемещения
            this.rect.setY(this.rect.getY() + this.y);
            if (this.rect.getY() > 300 || this.rect.getY() < 0) {
                this.y = -this.y;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // прерывание текущей нити
                System.out.println("Завершение работы программы пинг-понг");
            }
        }
    }
}