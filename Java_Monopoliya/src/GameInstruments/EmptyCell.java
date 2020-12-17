/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

public class EmptyCell extends Cell {

    /**
     * Realize empty cell's game logic
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    @Override
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) {
        if (person instanceof Player) {
            System.out.println("\nYou are on the EmptyCell. Just relax there.");
        }
        else if (person instanceof Bot){
            System.out.println("\nBot is relaxing on the EmptyCell.");
        }
    }

    /**
     * @return name of Cell
     * @since 1.0
     */
    @Override
    public String CellName() {
        return "Empty Cell";
    }

    /**
     * @return reduced name of Cell
     * @since 1.0
     */
    @Override
    public String toString() {
        return "E";
    }
}
