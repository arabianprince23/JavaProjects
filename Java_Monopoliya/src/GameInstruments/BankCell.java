/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

import java.util.Scanner;

public class BankCell extends Cell {

    /**
     * Realize bank cell's game logic
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    @Override
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) {

        if (person.isPlayerDebtor) { // Смотрим, является ли текущий игрок должником

            System.out.println("\nYou are the debtor of the bank. Your debt is : " + person.ShowDebt());
            person.moneyOnAccount -= (int)Math.round(person.creditValue);
            person.creditValue = 0;
            person.isPlayerDebtor = false;
            System.out.println("Bank withdraws it's money from your account. Your balance now is : " + person.ShowMoney());

            if (person.moneyOnAccount < 0) { // Если остаток денег на счету < 0 - завершаем игру
                System.out.println("\nGame over. Your balance now is < 0! You are the looser!");
                System.exit(0);
            }
        }

        System.out.println("\nYou are in the bank! Input <yes> if you want to take credit or input <no> if you don't.");
        Scanner in = new Scanner(System.in);
        String IsTaking;

        while (true) { // Предлагаем пользователю получить кредит, пока он не откажется, либо не приобретет его
            IsTaking = in.next();
            if (IsTaking.equals("yes")) {

                System.out.println("\nBank is going to give you a credit. " +
                        "Available sum of credit is : " + (int)(coefficients.creditCoeff * person.allPurchasesValue) + "\n");

                double sumOfCredit;

                while (true) { // Продолжаем ввод числа, пока он не окажется успешным
                    try {
                        System.out.print("Enter sum that you want to take : ");
                        sumOfCredit = in.nextInt(); // Пользователь вводит сумму, которую хочет получить
                        if (sumOfCredit > coefficients.creditCoeff * person.allPurchasesValue || sumOfCredit < 0) { // Проверяем, что она не больше доступной
                            System.out.println("\nYou are not available to take this sum. Try again");
                            continue;
                        }
                        System.out.println("\nNice. You have just take a credit for the sum : " + (int)sumOfCredit);
                        break;
                    } catch (Exception ex) {
                        System.out.println("\nSum of credit should be an integer number. Try to input again.");
                        in.next();
                        continue;
                    }
                }

                person.isPlayerDebtor = true; // Игрок становится должником, так как согласился взять кредит
                person.creditValue += Math.round(sumOfCredit * coefficients.debtCoeff); // Увеличиваем значение переменной, которая считает сумму долга
                person.moneyOnAccount += (int)Math.round(sumOfCredit); // Увеличиваем общее количество денег на счету пользователя
                System.out.println("Your debt now is : " + (int)person.creditValue);
                break;
            }
            else if (IsTaking.equals("no")) {
                System.out.println("\nSee you soon. Your current balance is : " + (int)person.moneyOnAccount);
                break;
            }
            else {
                System.out.println("\nIncorrect input. Try again. Input <yes> or <no> to choose.");
                continue;
            }
        }

    }

    /**
     * @return name of Cell
     * @since 1.0
     */
    @Override
    public String CellName() {
        return "Bank Cell";
    }

    /**
     * @return reduced name of Cell
     * @since 1.0
     */
    @Override
    public String toString() {
        return "$";
    }
}
