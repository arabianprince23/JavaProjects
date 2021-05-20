/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

// Нет аннотации @Constrained, поэтому проверке не подвергается public class Unrelated {
public class Unrelated {
    @Positive
    private int x;
    public Unrelated(int x) {
        this.x = x;
    }
}
