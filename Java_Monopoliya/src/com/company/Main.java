/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;
import GameInstruments.*;

public class Main {

    public static void main(String[] args) {

        int width = -1, height = -1, money = -1;

        try {
            if (args.length == 3) {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
                money = Integer.parseInt(args[2]);

                if (width < 6 || width > 30 || height < 6 || height > 30 || money < 500 || money > 15000) {
                    System.out.println("Incorrect input. Please, try again with another console arguments.\n" +
                            "\n6 <= width <= 30" +
                            "\n6 <= height <= 30" +
                            "\n500 <= money <= 15000\n");
                    System.exit(0);
                }
            } else {
                System.out.println("Args length should be 3. Check your input and try to run the program again!");
                System.exit(0);
            }
        } catch (Exception ex) {
            System.out.println("Incorrect console arguments");
            System.exit(0);
        }

        System.out.println("\nWidth of field is : " + width +
                "\nHeight of field is : " + height +
                "\nStart balance of players is : " + money);

        // Инициализация необходимых элементов для игрового процесса
        Coefficients coefficients = new Coefficients();
        GameField gameField = new GameField(height, width);
        Person player = new Player(money);
        Person bot = new Bot(money);

        // Генерация игрового поля
        System.out.println("\nHERE IS THE MAP OF CURRENT GAME : \n");
        gameField.LoadField(coefficients);
        System.out.println();

        int currentStep = coefficients.getRandomNumber(0, 2);
        int numberOfMove = 1;

        // Запускаем игровой процесс. Он будет идти бесконечно до тех пор, пока один из игроков не проиграет
        // Необходимые проверки и действия находятся внутри классов ячеек
        //noinspection InfiniteLoopStatement
        while (true) {

            System.out.println("\n-------------------------" + "\nINFORMATION BEFORE " + numberOfMove + " STEP : " +
                    "\nBot balance is : " + bot.ShowMoney() +
                    "\nPlayer balance is : " + player.ShowMoney() +
                    "\nPlayer's debt is : " + player.ShowDebt() +
                    "\n-------------------------");

            int firstDiceValue = coefficients.getRandomNumber(1, 7); // Рандомное значение первой игральной кости
            int secondDiceValue = coefficients.getRandomNumber(1, 7); // Рандомное значение второй игральной кости
            int step = firstDiceValue + secondDiceValue; // общий шаг игрока - сумма на игральных костях

            if (currentStep % 2 == 0) { // Если четный ход – ходит игрок
                // Определяем индекс текущей позиции игрока
                player.currentPositionIndex = (player.currentPositionIndex + step) % gameField.quantityOfCells;

                // Игрок совершает свой ход
                System.out.println("\n" + numberOfMove + ". Player's move now'!");
                // Выводим пользователю информацию о текущей позиции на карте (чтобы он понимал, где находится)
                System.out.println("\nYour current position is : " + gameField.fields[player.currentPositionIndex].CellName() +
                        "\nPosition index is : " + player.currentPositionIndex + " of " + gameField.fields.length);

                // Если игрок попал на клетку такси, мы её выполняем, а затем сразу же выполняем действие по новой клетке
                if (gameField.fields[player.currentPositionIndex] instanceof TaxiCell) {
                    gameField.fields[player.currentPositionIndex].CurrentMove(player, coefficients, bot, gameField);
                    bot.currentPositionIndex %= gameField.fields.length;

                    System.out.println("\nYour current position is : " + gameField.fields[player.currentPositionIndex].CellName() +
                            "\nPosition index is : " + player.currentPositionIndex + " of " + gameField.fields.length);

                    gameField.fields[player.currentPositionIndex].CurrentMove(player, coefficients, bot, gameField);
                    currentStep += 1;
                    numberOfMove += 1;
                    continue;
                }

                gameField.fields[player.currentPositionIndex].CurrentMove(player, coefficients, bot, gameField);
                currentStep += 1;
                numberOfMove += 1;
            }
            else if (currentStep % 2 != 0) { // Если нечетный ход - ходит бот
                // Определяем текущую позицию бота
                bot.currentPositionIndex = (bot.currentPositionIndex + step) % gameField.quantityOfCells;

                // Бот совершает свой ход
                System.out.println("\n" + numberOfMove + ". Bot's move now!");
                // Выводим пользователю информацию о текущей позиции бота на карте
                System.out.println("\nBot's current position is : " + gameField.fields[bot.currentPositionIndex].CellName() +
                        "\nPosition index is : " + bot.currentPositionIndex + " of " + gameField.fields.length);

                // Если бот попал на банковскую ячейку, мы пропускаем этот ход и бот ходит снова
                if (gameField.fields[bot.currentPositionIndex] instanceof BankCell) {
                    System.out.println("\nBot is on the bank cell. He will make his move again.");
                    continue;
                }

                // Если бот попал на клетку такси, мы её выполняем, а затем сразу же выполняем действие по новой клетке
                if (gameField.fields[bot.currentPositionIndex] instanceof TaxiCell) {
                    gameField.fields[bot.currentPositionIndex].CurrentMove(bot, coefficients, player, gameField);

                    bot.currentPositionIndex %= gameField.fields.length;
                    System.out.println("\nBot's current position is : " + gameField.fields[bot.currentPositionIndex].CellName() +
                            "\nPosition index is : " + bot.currentPositionIndex + " of " + gameField.fields.length);

                    gameField.fields[bot.currentPositionIndex].CurrentMove(bot, coefficients, player, gameField);
                    currentStep += 1;
                    numberOfMove += 1;
                    continue;
                }

                gameField.fields[bot.currentPositionIndex].CurrentMove(bot, coefficients, player, gameField);
                currentStep += 1;
                numberOfMove += 1;
            }
        }
    }
}
