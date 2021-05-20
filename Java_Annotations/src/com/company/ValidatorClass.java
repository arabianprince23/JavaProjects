/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import java.util.Set;
import java.lang.reflect.*;
import java.util.*;

public class ValidatorClass implements Validator {

    /**
     * Основная функция валидации - отсюда объект передается в дальнейшую функцию валидации своих полей. Здесь также заполняется итоговый массив ошибок.
     * @param object - переданный объект
     * @param path - путь до объекта
     * @return - возвращает итоговый список ошибок
     * @throws IllegalAccessException - в некотоырх случаях может выбрасывать исключение
     */
    @Override
    public Set<ValidationError> validate(Object object, String path) throws IllegalAccessException {

        Set<ValidationError> errorsList = new HashSet<>();
        Set<ValidationError> tmpErrorsList = new HashSet<>();

        // Проверяем, что валидируемый объект - не null
        if (object != null) {

            Class<?> objectClass = object.getClass();

            // Проверяем, что объект подвергается проверке
            if (objectClass.isAnnotationPresent(Constrained.class)) {
                tmpErrorsList = validateObjectFields(object, path);
            }

            // Если возвращенный временный сет ошибок - не null, добавляем все его элементы в общий сет
            if (tmpErrorsList != null) {
                errorsList.addAll(tmpErrorsList);
            }

            return errorsList;
        }

        // иначе просто возвращаем список ошибок
        return errorsList;
    }

    /**
     * Функция, валидирующая поля переданного в аргументы объекта.
     * @param object - объект
     * @param path - путь до объекта
     * @return - возвращает список ошибок, возникших по текущему объекту
     * @throws IllegalAccessException - в некотоырх случаях может выбрасывать исключение
     */
    public Set<ValidationError> validateObjectFields(Object object, String path) throws IllegalAccessException {

        boolean shouldBePositive = false;
        Set<ValidationError> errorsList = new HashSet<>();
        Set<ValidationError> tmpErrorsList;

        if (object != null) {

            // Все поля переданного объекта
            Field[] objectFields = object.getClass().getDeclaredFields();

            for (int i = 0; i < objectFields.length; i++) {

                var field = objectFields[i];

                // Доступ к полям
                field.setAccessible(true);

                // Получаем все аннотации
                AnnotatedType annotatedType = field.getAnnotatedType();

                if (annotatedType.isAnnotationPresent(NotNull.class)) {
                    if (field.get(object) == null) {
                        String message = "Must not be null";
                        Object failedValue = "null";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), failedValue));
                        continue;
                    }
                }

                if (annotatedType.isAnnotationPresent(Positive.class)) {
                    shouldBePositive = true;
                    if (Long.parseLong(String.valueOf(field.get(object))) <= 0) {
                        String message = "Must be positive";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                if (annotatedType.isAnnotationPresent(Negative.class)) {
                    if (Long.parseLong(String.valueOf(field.get(object))) > 0 && !shouldBePositive) {
                        String message = "Must be negative";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                if (annotatedType.isAnnotationPresent(NotBlank.class)) {
                    if (field.get(object).toString().isBlank()) {
                        String message = "Must not be blank";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), "''"));
                    }
                }

                // Тут стринги и коллекции
                if (annotatedType.isAnnotationPresent(NotEmpty.class)) {
                    if (field.get(object) instanceof String) {
                        if (((String) field.get(object)).isEmpty()) {
                            String message = "Must not be empty";
                            errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                        }
                    }
                    else if (Collections.singletonList(field.get(object)).size() == 0) {
                        String message = "Must not be empty";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                // Тут стринги и коллекции
                if (annotatedType.isAnnotationPresent(Size.class)) {

                    if (annotatedType.getAnnotation(Size.class).min() > annotatedType.getAnnotation(Size.class).max()) {
                        throw new IllegalArgumentException("MIN should be less then MAX!");
                    }

                    if (field.get(object) instanceof String) {
                        if (((String) field.get(object)).length() < annotatedType.getAnnotation(Size.class).min()
                                || ((String) field.get(object)).length() > annotatedType.getAnnotation(Size.class).max()) {
                            String message = "String must be in size from " + annotatedType.getAnnotation(Size.class).min() + " to " + annotatedType.getAnnotation(Size.class).max();
                            errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                        }
                    }
                    else if (Collections.singletonList(field.get(object)).size() < annotatedType.getAnnotation(Size.class).min()
                            || Collections.singletonList(field.get(object)).size() > annotatedType.getAnnotation(Size.class).max()) {
                        String message = "String must be in size from " + annotatedType.getAnnotation(Size.class).min() + " to " + annotatedType.getAnnotation(Size.class).max();
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                if (annotatedType.isAnnotationPresent(InRange.class)) {

                    if (annotatedType.getAnnotation(InRange.class).min() > annotatedType.getAnnotation(InRange.class).max()) {
                        throw new IllegalArgumentException("MIN should be less then MAX!");
                    }

                    if (Long.parseLong(String.valueOf(field.get(object))) < annotatedType.getAnnotation(InRange.class).min()
                            || Long.parseLong(String.valueOf(field.get(object))) > annotatedType.getAnnotation(InRange.class).max()) {
                        String message = "Must be in range from " + annotatedType.getAnnotation(InRange.class).min() + " to " + annotatedType.getAnnotation(InRange.class).max();
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                if (annotatedType.isAnnotationPresent(AnyOf.class)) {
                    if (!Arrays.asList(annotatedType.getAnnotation(AnyOf.class).value()).contains(field.get(object))) {
                        String message = "Must be one of the suggested elements";
                        errorsList.add(new ValidationErrorClass(message, path.equals("") ? field.getName() : path + "." + field.getName(), field.get(object)));
                    }
                }

                if (List.class.isAssignableFrom(field.getType())) {
                    tmpErrorsList = validateFieldIfList(object, field, path.equals("") ? field.getName() : path + "." + field.getName());
                } else {
                    tmpErrorsList = validate(field.get(object), path + "." + field.getName());
                }

                if (tmpErrorsList != null) {
                    errorsList.addAll(tmpErrorsList);
                }
            }

            return errorsList;
        }

        return errorsList;
    }

    /**
     * Функция, валидирующая листы
     * @param object - переданный объект
     * @param field - переданное поле
     * @param path - путь до объекта
     * @return - возвращает список возникших ошибок
     * @throws IllegalAccessException - в некотоырх случаях может выбрасывать исключение
     */
    public Set<ValidationError> validateFieldIfList(Object object, Field field, String path) throws IllegalAccessException {

        boolean shouldBePositive = false;
        Set<ValidationError> errorsList = new HashSet<>();
        List<?> list;

        try {
            list = (List<?>) field.get(object); // values
            int size = list.size();
        } catch (NullPointerException ex) {
            return errorsList;
        }

        for (int i = 0; i < list.size(); ++i) {

            var annotatedType = ((AnnotatedParameterizedType) field.getAnnotatedType()).getAnnotatedActualTypeArguments()[0];

            if (annotatedType.isAnnotationPresent(NotNull.class)) {

                if (list.get(i) == null) {
                    String message = "Must be not null";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", "null"));
                    continue;
                }
            }

            if (annotatedType.isAnnotationPresent(Positive.class)) {
                shouldBePositive = true;
                if (Long.parseLong(String.valueOf(list.get(i))) <= 0) {
                    String message = "Must be positive";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            if (annotatedType.isAnnotationPresent(Negative.class)) {
                if (Long.parseLong(String.valueOf(list.get(i))) > 0 && !shouldBePositive) {
                    String message = "Must be negative";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            if (annotatedType.isAnnotationPresent(NotBlank.class)) {
                if (list.get(i).toString().isBlank()) {
                    String message = "Must not be blank";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            // Тут стринги и коллекции
            if (annotatedType.isAnnotationPresent(NotEmpty.class)) {
                if (list.get(i) instanceof String) {
                    if (((String) list.get(i)).isEmpty()) {
                        String message = "Must not be empty";
                        errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                    }
                }
                else if (Collections.singletonList(list.get(i)).size() == 0) {
                    String message = "Must not be empty";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            // Тут стринги и коллекции
            if (annotatedType.isAnnotationPresent(Size.class)) {

                if (annotatedType.getAnnotation(Size.class).min() > annotatedType.getAnnotation(Size.class).max()) {
                    throw new IllegalArgumentException("MIN should be less then MAX!");
                }

                if (list.get(i) instanceof String) {
                    if (((String) list.get(i)).length() < annotatedType.getAnnotation(Size.class).min()
                            || ((String) list.get(i)).length() > annotatedType.getAnnotation(Size.class).max()) {
                        String message = "String must be in size ["
                                + annotatedType.getAnnotation(Size.class).min()
                                + ";"
                                + annotatedType.getAnnotation(Size.class).max() + "]";
                        errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                    }
                }
                else if (Collections.singletonList(list.get(i)).size() < annotatedType.getAnnotation(Size.class).min()
                        || Collections.singletonList(list.get(i)).size() > annotatedType.getAnnotation(Size.class).max()) {

                    String message = "Must be in size from "
                            + annotatedType.getAnnotation(Size.class).min()
                            + " to "
                            + annotatedType.getAnnotation(Size.class).max();

                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            if (annotatedType.isAnnotationPresent(InRange.class)) {

                if (annotatedType.getAnnotation(InRange.class).min() > annotatedType.getAnnotation(InRange.class).max()) {
                    throw new IllegalArgumentException("MIN should be less then MAX!");
                }

                if (Long.parseLong(String.valueOf(list.get(i))) < annotatedType.getAnnotation(InRange.class).min()
                        || Long.parseLong(String.valueOf(list.get(i))) > annotatedType.getAnnotation(InRange.class).max()) {

                    String message = "Must be in range from "
                            + annotatedType.getAnnotation(InRange.class).min()
                            + " to "
                            + annotatedType.getAnnotation(InRange.class).max();

                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            if (annotatedType.isAnnotationPresent(AnyOf.class)) {
                if (!Arrays.asList(annotatedType.getAnnotation(AnyOf.class).value()).contains(list.get(i))) {
                    String message = "Must be one of the suggested elements";
                    errorsList.add(new ValidationErrorClass(message, path + "[" + i + "]", list.get(i)));
                }
            }

            Set<ValidationError> tmpErrorsList = validate(list.get(i), path + "[" + i + "]");
            if (tmpErrorsList != null) {
                errorsList.addAll(tmpErrorsList);
            }
        }

        return errorsList;
    }
}

