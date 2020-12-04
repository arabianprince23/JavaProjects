/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */


package GameInstruments;

import java.util.Random;
import java.util.Scanner;

public class ShopCell extends Cell {
    double compensationCoeff; //
    double improvementCoeff;
    Person owner; // владелец магазина
    int N; // стоимость магазина (генерится случайно от 50 до 500)
    double K; // компенсация магазина (генерится от 0.5N до 0.9N)

    public ShopCell(Coefficients coefficients) {
        // Я знаю, что ниже представлена ПСЕВДОгенерация вещественного числа и генериться будут не все возможные вещественные числа,
        // однако, в рамках цели, для которой эти числа нужны, этого более чем достаточно, поэтому я сделал так
        this.compensationCoeff = coefficients.getRandomNumber(1000000, 10000001) / 10000000.0;
        this.improvementCoeff = coefficients.getRandomNumber(1000000, 20000001) / 10000000.0;
        N = coefficients.getRandomNumber(50, 501); // стоимость магазина (генерится случайно от 50 до 500)
        K = (coefficients.getRandomNumber(5000000, 9000001) / 10000000.0) * N; // компенсация магазина (генерится от 0.5N до 0.9N)
    }

    /**
     * Realize shop cell's game logic
     * @param person first player
     * @param otherPerson second person
     * @param coefficients helpful class with game coeddicients and random methods
     * @since 1.0
     */
    @Override
    public void CurrentMove(Person person, Coefficients coefficients, Person otherPerson, GameField gameField) { // Присваиваем магазину нового владельца

        Scanner in = new Scanner(System.in);

        if (person instanceof Player) { // работаем с игроком

            if (owner == null) { // проверяем, что у магазина нет владельца

                if (person.moneyOnAccount >= N) { // проверяем, что у игрока достаточно денег для покупки

                    System.out.println("\nYou are able to buy the shop. \nYour balance is : " + (int)person.moneyOnAccount + "\nShop price is : " + N +
                            "\nDo you want to buy it? Input <yes> or <no> to choose.");

                    while (true) { // Предлагаем пользователю купить магазин, пока он не откажется, либо не приобретет его
                        String answer = in.next();

                        if (answer.equals("yes")) { // Если пользователь соглашается на покупку, присваиваем ему статус владельца
                            owner = person;
                            person.moneyOnAccount -= N; // Забираем деньги за магазин у пользователя
                            person.allPurchasesValue += N;
                            System.out.println("\nThanks for buying this shop. Your current balance is : " + (int)person.moneyOnAccount);
                            break;
                        } else if (answer.equals("no")) { // Если не соглашается, ничего не делаем
                            System.out.println("Ok, see you soon. Your current balance is : " + (int)person.moneyOnAccount);
                            break;
                        } else {
                            System.out.println("\nIncorrect input. Try again. Input <yes> or <no> to choose.");
                            continue;
                        }
                    }
                } else {
                    System.out.println("\nYou are in the Shop without owner. But you don't have enough money to but it.\n" +
                            "Shop price is : " + N +
                            "\nYour balance is :" + (int)person.moneyOnAccount);
                }
            }
            else if (owner == person) {
                System.out.println("\nYou are the owner of this shop. Do you want to upgrade it?\nIt will cost you : " +
                        (int)(N * improvementCoeff) + "\nInput <yes> or <no>.");

                while (true) { // Предлагаем пользователю улучшить свой магазин, если он этого захочет
                    String answer = in.next();

                    if (answer.equals("yes")) {
                        if (person.moneyOnAccount > N * improvementCoeff) {
                            person.moneyOnAccount -= N * improvementCoeff;
                            N += (int)(improvementCoeff * N);
                            K += (int)(compensationCoeff * K);
                            System.out.println("\nYour shop has been successfully upgraded!");
                            break;
                        }
                        else {
                            System.out.println("\nSorry, but you don't have enough money to improve your shop. Try again later.");
                            break;
                        }
                    } else if (answer.equals("no")) {
                        System.out.println("\nAccepted. Coefficients of shop are not going to be changed.");
                        break;
                    } else {
                        System.out.println("\nIncorrect input. Try again. Input <yes> or <no> to choose.");
                        continue;
                    }
                }
            }
            else if (owner == otherPerson) {
                System.out.println("\nThis is your enemy's shop. You have to pay cause of being there. Sum to pay is : " + (int)K);
                person.moneyOnAccount -= K; // забираем сумму со счета игрока
                otherPerson.moneyOnAccount += K;
                if (person.moneyOnAccount < 0) { // Если остаток денег на счету < 0 - завершаем игру
                    System.out.println("\nGame over. Your balance now is : " + person.ShowMoney() + "! You are the looser!");
                    System.exit(0);
                }
            }
        }
        else if (person instanceof Bot) { // Если на клетку попал бот

            if (owner == null) {

                int isGoingToBuy = coefficients.getRandomNumber(0, 2); // рандомно определяем, будет ли бот покупать магазин

                if (isGoingToBuy == 1 && person.moneyOnAccount >= N)  { // если есть деньги на счету и выпала 1, то бот становится владельцем
                    owner = person;
                    person.moneyOnAccount -= N;
                    System.out.println("\nBot is buying shop for the price : " + N);
                }
                else {
                    System.out.println("\nBot is not going to buy shop.");
                }
            }
            else if (owner == person) {
                int isGoingToUpgrade = coefficients.getRandomNumber(0, 2);

                if (isGoingToUpgrade == 1) {
                    if (person.moneyOnAccount > N * improvementCoeff) {
                        person.moneyOnAccount -= N * improvementCoeff;
                        System.out.println("\nBot has just upgraded his shop for the price : " + N * improvementCoeff);
                        N += (int)(improvementCoeff * N);
                        K += (int)(compensationCoeff * K);
                    }
                    else {
                        System.out.println("\nBot just tried to improve his shop, but he don't have enough money for that.");
                    }
                }
                else {
                    System.out.println("\nBot is not going to improve shop.");
                }
            }
            else if (owner == otherPerson) {
                System.out.println("\nBot is in your shop. He will pay you cause of being there. Sum that you will get is : " + (int)K);
                person.moneyOnAccount -= K; // забираем сумму со счета игрока
                otherPerson.moneyOnAccount += K;
                if (person.moneyOnAccount < 0) { // Если остаток денег на счету < 0 - завершаем игру
                    System.out.println("\nGame over. Bot's balance now is : " + person.ShowMoney() + "! You are the winner!");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * @return name of Cell
     * @since 1.0
     */
    @Override
    public String CellName() {
        return "Shop Cell";
    }

    /**
     * @return reduced name of Cell
     * @since 1.0
     */
    @Override
    public String toString() {
        return "S";
    }
}
