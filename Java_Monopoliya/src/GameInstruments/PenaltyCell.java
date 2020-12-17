/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

import java.util.Random;

public class PenaltyCell extends Cell {
    double penaltyCoeff;

    PenaltyCell(Coefficients coefficients) {
        this.penaltyCoeff = coefficients.penaltyCoeff; // Присваиваем текущему штрафному коэффициенту сгенерированный в начале игры
    }

    /**
     * Realize penalty cell's game logic
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    @Override
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) {

        if (person instanceof Player) {
            System.out.println("\nYou are on the PenaltyCell. Your lost is : " + (int)(person.moneyOnAccount * penaltyCoeff));
            person.moneyOnAccount -= (int)(person.moneyOnAccount * penaltyCoeff);

            if (person.moneyOnAccount < 0) { // Если остаток денег на счету < 0 - завершаем игру
                if (person instanceof Bot) {
                    System.out.println("\nGame over. Bot's balance now is < 0! You are the winner! Congratulations!");
                } else {
                    System.out.println("\nGame over. Your balance now is < 0! You are the looser!");
                }
                System.exit(0);
            }
        }
        else if (person instanceof Bot) {
            System.out.println("\nBot is on the PenaltyCell. His lost is : " + (int)(person.moneyOnAccount * penaltyCoeff));

            person.moneyOnAccount -= (int)(person.moneyOnAccount * penaltyCoeff);

            if (person.moneyOnAccount < 0) { // Если остаток денег на счету < 0 - завершаем игру
                if (person instanceof Bot) {
                    System.out.println("\nGame over. Bot's balance now is < 0! You are the winner! Congratulations!");
                } else {
                    System.out.println("\nGame over. Your balance now is < 0! You are the looser!");
                }
                System.exit(0);
            }
        }
    }

    /**
     * @return name of Cell
     * @since 1.0
     */
    @Override
    public String CellName() {
        return "Penalty Cell";
    }

    /**
     * @return reduced name of Cell
     * @since 1.0
     */
    @Override
    public String toString() {
        return "%";
    }
}
