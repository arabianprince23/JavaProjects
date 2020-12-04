/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

public class Coefficients {
    public double compensationCoeff; // коэффициент увеличения компенсации
    public double improvementCoeff; // коэффициент увеличения стоимости улучшения магазина
    public double creditCoeff; // коэффициент для взятия долга
    public double debtCoeff; // кредитный коэфф, на который умножается взятая у банка сумма
    public int taxiDistance; // Переброс пользователя по клеткам из такси. Диапозон 3 - 5
    public double penaltyCoeff; // Коэфф для штрафа (умножается на сумму на счете)

    public Coefficients() { // Здесь те коэффициенты, которые генерируются единожды для всего

        // Я знаю, что ниже представлена ПСЕВДОгенерация вещественного числа и генериться будут не все возможные вещественные числа,
        // однако, в рамках цели, для которой эти числа нужны, этого более чем достаточно, поэтому я сделал так
        this.creditCoeff = getRandomNumber(2000000, 200000001) / 1000000000.0;
        System.out.println("Credit coeff is : " + this.creditCoeff);

        this.debtCoeff = getRandomNumber(100000000, 300000001) / 100000000.0;
        System.out.println("Debt coeff is : " + this.debtCoeff);

        this.penaltyCoeff = getRandomNumber(1000000, 10000001) / 100000000.0;
        System.out.println("Penalty coeff is : " + this.penaltyCoeff);
    }

    /**
     * Realize any cell's game logic, will be override in other classes
     * @param min minimal value of random number
     * @param max maximal value of random number (not includes particular number)
     * @return random number between min and max
     * @since 1.0
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
