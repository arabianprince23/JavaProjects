/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorClassTest {

    @Test
    void validate() throws IllegalAccessException {

        List<GuestForm> guests = List.of(
                new GuestForm(5, "Str", null, null, -1),
                new GuestForm(5, "Str", "lunn", "lunn", 10),
                new GuestForm(10, "String", "lunn", "", 10),
                new GuestForm(-1, "", null, "lunn", 10),
                new GuestForm(5, "      ", "lunn", "lunn", 10),
                new GuestForm(3, null, "", "lunn", 10)
        );

        BookingForm form = new BookingForm(
                5,
                null,
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
                );

        BookingForm form1 = new BookingForm(
                5,
                "String",
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
        );

        BookingForm form2 = new BookingForm(
                5,
                " ",
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
        );

        BookingForm form3 = new BookingForm(
                5,
                "Str",
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
        );

        BookingForm form4 = new BookingForm(
                5,
                "",
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
        );

        ValidatorClass validator = new ValidatorClass();
        Set<ValidationError> validationErrors = validator.validate(form, "");
        Set<ValidationError> validationErrors1 = validator.validate(form1, "");
        Set<ValidationError> validationErrors2 = validator.validate(form2, "");
        Set<ValidationError> validationErrors3 = validator.validate(form3, "");
        Set<ValidationError> validationErrors4 = validator.validate(form4, "");

        assertEquals(validationErrors.size(), 20);
        assertEquals(validationErrors1.size(), 20);
        assertEquals(validationErrors2.size(), 20);
        assertEquals(validationErrors3.size(), 19);
        assertEquals(validationErrors4.size(), 21);
    }

    @Test
    void validateObjectFields() throws IllegalAccessException {

        GuestForm guestForm1 = new GuestForm(5, "Str", null, null, -1);
        GuestForm guestForm2 = new GuestForm(5, "Str", "lunn", "lunn", 10);
        GuestForm guestForm3 = new GuestForm(10, "String", "lunn", "", 10);
        GuestForm guestForm4 = new GuestForm(-1, "", null, "lunn", 10);
        GuestForm guestForm5 = new GuestForm(5, "      ", "lunn", "lunn", 10);
        GuestForm guestForm6 = new GuestForm(3, null, "", "lunn", 10);
        GuestForm guestForm7 = new GuestForm(-100, null, null, null, -1000);

        ValidatorClass validator = new ValidatorClass();
        Set<ValidationError> validationErrors1 = validator.validateObjectFields(guestForm1, "");
        Set<ValidationError> validationErrors2 = validator.validateObjectFields(guestForm2, "");
        Set<ValidationError> validationErrors3 = validator.validateObjectFields(guestForm3, "");
        Set<ValidationError> validationErrors4 = validator.validateObjectFields(guestForm4, "");
        Set<ValidationError> validationErrors5 = validator.validateObjectFields(guestForm5, "");
        Set<ValidationError> validationErrors6 = validator.validateObjectFields(guestForm6, "");
        Set<ValidationError> validationErrors7 = validator.validateObjectFields(guestForm7, "");

        assertEquals(validationErrors1.size(), 4);
        assertEquals(validationErrors2.size(), 0);
        assertEquals(validationErrors3.size(), 3);
        assertEquals(validationErrors4.size(), 5);
        assertEquals(validationErrors5.size(), 2);
        assertEquals(validationErrors6.size(), 2);
        assertEquals(validationErrors7.size(), 7);
    }

    @Test
    void validateFieldIfList() throws IllegalAccessException {

        List<GuestForm> guests = List.of(
                new GuestForm(5, "", null, null, 10),
                new GuestForm(5, "Str", "lunn", "lunn", 10)
        );

        BookingForm form = new BookingForm(
                5,
                "String",
                guests,
                List.of("WIFI", "Breakfast"),
                "TV",
                new Unrelated(1)
        );

        Field[] fields = form.getClass().getDeclaredFields();
        var field = fields[0];
        field.setAccessible(true);

        ValidatorClass validator = new ValidatorClass();
        Set<ValidationError> validationErrors = validator.validateFieldIfList(form, field, "");

        assertEquals(validationErrors.size(), 4);
    }
}