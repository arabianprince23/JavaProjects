/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

public class Cell {

    /**
     * Realize any cell's game logic, will be override in other classes
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) {
    }

    /**
     * @return name of Cell
     * @since 1.0
     */
    public String CellName() {
        return "Cell Name";
    }
}
