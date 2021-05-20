/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

@Constrained
public class GuestForm {

    @NotNull
    @NotBlank
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @Positive
    @InRange(min = 0, max = 200)
    private int age;

    @Positive
    @Negative
    @InRange(min = 0, max = 5)
    private int testVar1;

    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 0, max = 3)
    private String testVar2;

    public GuestForm(int testVar1, String testVar2, String firstName, String lastName, int age) {
        this.testVar1 = testVar1;
        this.testVar2 = testVar2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}

