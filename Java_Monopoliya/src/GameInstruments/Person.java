/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;
import java.util.Random;

public class Person {
    double moneyOnAccount; // Количество денег на счету пользователя
    public int currentPositionIndex; // Индекс текущей ячейки
    boolean isPlayerDebtor; // Является ли игрок должником банка
    double creditValue; // Общая сумма, взята в долг у банка
    double allPurchasesValue; // Общая сумма, потраченная на покупки и улучшения

    public Person(double moneyOnAccount) {
        this.moneyOnAccount = moneyOnAccount;
        currentPositionIndex = 0;
        creditValue = 0.0;
        isPlayerDebtor = false;
        allPurchasesValue = 0.0;
    }

    /**
     * @return creditValue as String
     * @since 1.0
     */
    public String ShowDebt() {
        return String.valueOf((int)creditValue);
    }

    /**
     * @return moneyOnAccount as String
     * @since 1.0
     */
    public String ShowMoney() {
        return String.valueOf((int)moneyOnAccount);
    }
}
