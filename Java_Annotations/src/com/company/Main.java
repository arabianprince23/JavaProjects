/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {

        ValidatorClass validator = new ValidatorClass();

        List<GuestForm> guests = List.of(
                new GuestForm(3, "Str", /*firstName*/ null, /*lastName*/ "Def", /*age*/ 21),
                new GuestForm(3, "Str", /*firstName*/ "", /*lastName*/ "Ijk", /*age*/ -3)
        );

        Unrelated unrelated = new Unrelated(-1);

        BookingForm bookingForm = new BookingForm(
                5,
                "String",
                guests,
                /*amenities*/ List.of("TV", "Piano"),
                /*propertyType*/ "Apartment",
                unrelated
        );

        Set<ValidationError> validationErrors = validator.validate(bookingForm, "");

        for (int i = 0; i < validationErrors.size(); ++i) {
            System.out.println(i + 1 + ".");
            System.out.print("Path: " + ((ValidationErrorClass)validationErrors.toArray()[i]).path + "\n");
            System.out.print("Message: " + ((ValidationErrorClass)validationErrors.toArray()[i]).message + "\n");
            System.out.print("Value: " + ((ValidationErrorClass)validationErrors.toArray()[i]).object + "\n");
            System.out.println();
        }
    }
}
