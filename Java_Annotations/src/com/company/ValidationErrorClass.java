/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

public class ValidationErrorClass implements ValidationError {

    String message, path;
    Object object;

    public ValidationErrorClass(String message, String path, Object object) {
        this.message = message;
        this.path = path;
        this.object = object;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return object;
    }
}
