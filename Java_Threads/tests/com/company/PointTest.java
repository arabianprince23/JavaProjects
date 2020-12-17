/*
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getX() {
        Point point = new Point(0, 0);
        assertEquals(point.getX(), 0);
    }

    @Test
    void getY() {
        Point point = new Point(0, 0);
        assertEquals(point.getY(), 0);
    }

    @Test
    void testToString() {
        Point point = new Point(11.15, 18.11);
        assertEquals(point.toString(), "Current point position is : (x, y) = (11,15 , 18,11)");
    }
}