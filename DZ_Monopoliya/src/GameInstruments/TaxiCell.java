/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

import java.util.stream.StreamSupport;

public class TaxiCell extends Cell {
    int taxiDistance;

    /**
     * Makes a taxi move for any player
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    @Override
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) {
        taxiDistance = coefficients.getRandomNumber(3, 6);
        person.currentPositionIndex = (person.currentPositionIndex + taxiDistance) % gameField.quantityOfCells;

        // Выводим в консоль предупреждение о том, что бот/игрок перемещены на другую клетку
        if (person instanceof Player) {
            System.out.println("\nYou are shifted forward by " + taxiDistance + " cells.");
        }
        else if (person instanceof Bot) {
            System.out.println("\nBot has been shifted forward by " + taxiDistance + " cells.");
        }
    }

    /**
     * @return name of cell
     * @since 1.0
     */
    @Override
    public String CellName() {
        return "Taxi Cell";
    }

    /**
     * @return reduced name of Cell
     * @since 1.0
     */
    @Override
    public String toString() {
        return "T";
    }
}
