/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

public interface ValidationError {
    String getMessage();
    String getPath();
    Object getFailedValue();
}
