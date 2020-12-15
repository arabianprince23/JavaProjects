/*
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import java.util.TimerTask;

public class Animal extends Thread {

    public static Point point; // статическая переменная точки
    private final double sin; // синус угла (устанавливается в конструкторе)
    private final double cos; // косинус угла (устанавливается в конструкторе)
    private final double si; // случайный коэффициент а интервале [1;10) (генерируется для каждого существа)
    private int semaphore = 1; // семафор

    /**
     * Конструктор для создания персонажа-животного
     * @param ai - угол натяжения, для лебедя 60, рака 180, щуки 300 градусов
     */
    public Animal(double ai) {
        sin = Math.sin(Math.toRadians(ai));
        cos = Math.cos(Math.toRadians(ai));
        si = randomDouble(1, 10);
    }

    /**
     * Метод для генерации случайного действительного числа на полуинтервале
     * @param startNumber - левая граница
     * @param finalNumber - правая граница
     * @return случайное число из полуинтервала
     */
    public static double randomDouble(int startNumber, int finalNumber) {
        return startNumber + (Math.random() * finalNumber);
    }

    /**
     * TimerTask для вывода информации о текущей точке каждые 2 секунды.
     * В данной ситуации можно не оборачивать метод в synchronized,
     * поскольку он вшит внутрь timer.schedule, который вызывается в мейне
     */
    TimerTask myTask = new TimerTask() {
        @Override
        public void run() {
            System.out.println("\n" + point.toString() + "\n");
        }
    };

    /**
     * Основной метод взаимодействия с потоками, отслеживает, что время еще не закончилось и нужно делать сдвиг
     */
    @Override
    public void run() {
        while (!Main.endFlag) {
            makeMove(); // Переход к выполнению синхронизированного метода сдвига
            try {
                // случайное время в миллисекундах, которое нужно животному для отдыха
                int relaxTime = (int)randomDouble(1000, 5000);
                Thread.sleep(relaxTime);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("Thread with name : '" + currentThread().getName() + "' has done his work and ended.");
    }

    /**
     * Метод, изменяющий значения координат по заданным по условию формулам
     */
    private void changeCoordinates() {
        Point.x = Point.x + si * cos;
        Point.y = Point.y + si * sin;
    }

    /**
     * Синхронизированный метод для совершения сдвига тележки.
     * Пока семафор блокирует доступ к критической секции, потоки ждут.
     * После того, как какой-то поток отработал, семафор разблокируется
     * и продолжает работу потока, у которого ранее был вызван метод wait()
     */
    private synchronized void makeMove() {
        while (semaphore == 0) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace();}
        }
        semaphore = 0;
        System.out.println("Thread with name : '" + currentThread().getName() + "' is moving a cart right now...");
        changeCoordinates();
        semaphore = 1;
        notify();
    }
}
