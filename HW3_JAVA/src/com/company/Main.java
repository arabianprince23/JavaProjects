/*
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import java.util.Timer;

public class Main {

    public static boolean endFlag = false; // Флаг, благодаря которому отслеживается прошествие 25 секунд от старта

    public static void main(String[] args) {

        Timer timer = new Timer(); // Таймер для периодического вывода

        Animal swan = new Animal(60); // лебедь
        Animal crayfish = new Animal(180); // рак
        Animal pike = new Animal(300); // щука

        // Ниже - обработка введённых аргументов в командную строку
        try {
            if (args.length == 0) {
                Animal.point = new Point(0, 0);
            } else if (args.length == 1) {
                Animal.point = new Point(Double.parseDouble(args[0]), 0);
            } else if (args.length == 2) {
                Animal.point = new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
            } else {
                System.out.println("Incorrect quantity of arguments. Try again.");
                System.exit(1);
            }
        } catch (Exception ex) {
            System.out.println("\nSome problems with arguments. Try again." +
                    "\nExample of correct arguments : 12.35 -10.00\n");
            System.exit(1);
        }

        Thread swanThread = new Thread(swan, "swanThread");
        Thread crayfishThread = new Thread(crayfish, "crayfishThread");
        Thread pikeThread = new Thread(pike, "pikeThread");

        System.out.printf("Start coordinates : (x, y) = (%.2f , %.2f)\n\n", Animal.point.getX(), Animal.point.getY());

        long startTimePoint = System.currentTimeMillis(); // Счетчик старта работы программы

        swanThread.start();
        crayfishThread.start();
        pikeThread.start();

        timer.schedule(swan.myTask, 2000, 2000); // Выполняем myTask каждые 2 секунды (вывод инфоры о точке)

        // Потоки отрабатывают, пока не пройдут 25 секунд
        while (System.currentTimeMillis() - startTimePoint < 25000) {}

        timer.cancel(); // Приостанавливаем вывод координат каждые 2 секунды
        endFlag = true; // Флаг сменяется - потоки останавливают работу

        System.out.println("\n\n\n---------------------------------------------------------------");
        System.out.println("25 seconds have passed. All animals are finally tired.\n");
        System.out.printf("Final coordinates : (x, y) = (%.2f, %.2f)\n%n", Animal.point.getX(), Animal.point.getY());
    }
}
