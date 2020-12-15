/*
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

public class Point {

    public static double x;
    public static double y;

    /**
     * Конструктор для точки
     * @param x стартовая координата по иксу
     * @param y стартовая координата по игреку
     */
    public Point(double x, double y) {
        Point.x = x;
        Point.y = y;
    }

    /**
     * Геттер для икса
     * @return икс
     */
    public double getX() {
        return x;
    }

    /**
     * Геттер для игрека
     * @return игрек
     */
    public double getY() {
        return y;
    }

    /**
     * Вывод информации о точке
     * @return строку с информацией о точке
     */
    @Override
    public String toString() {
        return String.format("Current point position is : (x, y) = (%.2f , %.2f)", x, y);
    }
}
