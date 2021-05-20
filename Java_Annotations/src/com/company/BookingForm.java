/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import java.util.List;

@Constrained
public class BookingForm {

    @NotNull
    @Size(min = 1, max = 5)
    private List<@NotNull @NotEmpty GuestForm> guests;

    @NotNull
    private List<@Size(min = 0, max = 15) @NotEmpty @NotBlank @AnyOf({"TV", "Kitchen"}) String> amenities;

    @NotNull
    @AnyOf({"House", "Hostel"})
    private String propertyType;

    @NotNull
    private Unrelated unrelated;

    @Positive
    @Negative
    @InRange(min = 0, max = 5)
    private int testVar1;

    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 0, max = 3)
    private String testVar2;

    public BookingForm(int testVar1, String testVar2, List<GuestForm> guests, List<String> amenities, String propertyType, Unrelated unrelated) {
        this.testVar1 = testVar1;
        this.testVar2 = testVar2;
        this.guests = guests;
        this.amenities = amenities;
        this.unrelated = unrelated;
        this.propertyType = propertyType;
    }
}
