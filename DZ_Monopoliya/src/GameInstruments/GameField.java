/**
 *  @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package GameInstruments;

import java.util.Random;

public class GameField {

    Random rnd = new Random();
    public int height, width, quantityOfCells;
    public Cell[] fields;

    public GameField(int height, int width) {
        this.height = height;
        this.width = width;
        this.quantityOfCells = width * 2 + (height - 2) * 2;

        this.fields = new Cell[quantityOfCells]; // Создали массив ячеек

        this.fields[0] = new EmptyCell(); // Заполнили все EmptyCells (они располагаются на углах поля)
        this.fields[width - 1] = new EmptyCell();
        this.fields[width - 1 + this.height - 1] = new EmptyCell();
        this.fields[width - 1 + this.height - 1 + this.width - 1] = new EmptyCell();
    }

    /**
     * Prints generated playfield
     * @since 1.0
     */
    public void LoadField(Coefficients coefficients) {
        CompletionOfFieldLine(1, this.width - 2, coefficients);
        CompletionOfFieldLine(this.width, this.width - 1 + this.height - 2, coefficients);
        CompletionOfFieldLine(width - 1 + this.height, width - 1 + this.height - 1 + this.width - 2, coefficients);
        CompletionOfFieldLine(this.width - 1 + this.height - 1 + this.width, this.fields.length - 1, coefficients);

        for (int i = 0; i < this.width; ++i) { // Выводим первую (верхнюю) строку
            System.out.print(fields[i].toString());
        }
        System.out.println();
        int leftcolumn = this.fields.length - 1, rightcolumn = this.width;
        for (int i = 0; i < this.height - 2; ++i) {
            System.out.print(fields[leftcolumn - i].toString());
            for (int j = 0; j < this.width - 2; ++j) {
                System.out.print(" ");
            }
            System.out.print(fields[rightcolumn + i].toString());
            System.out.println();
        }

        for (int i = this.width - 1 + this.height - 1 + this.width - 1; i >= width - 1 + this.height - 1; --i) {
            System.out.print(fields[i].toString());
        }
    }

    /**
     * Generates gameField
     * @param fromIndex index to start
     * @param toIndex index to end
     * @param coefficients helpful class for game coefficients and random method
     * @since 1.0
     */
    public void CompletionOfFieldLine(int fromIndex, int toIndex, Coefficients coefficients) {
        int quantityOfFreePositions = toIndex - fromIndex + 1;
        int emptyindexes[] = new int[quantityOfFreePositions];
        int taxiQuantity = coefficients.getRandomNumber(0, 2); // количество такси на линии
        int penaltyQuantity = coefficients.getRandomNumber(0, 2); // количество штрафных клеток на линии

        // Пока ничего не заполняли - все индексы свободны
        for (int j = 0; j <= toIndex - fromIndex; ++j) {
            emptyindexes[j] = fromIndex + j;
        }

        // Если у нас перебор по количеству штрафов и такси одновременно, рандомно убираем одну из ячеек
        if ((quantityOfFreePositions - (taxiQuantity + penaltyQuantity)) == 0) {
            if (coefficients.getRandomNumber(1, 2) == 1) {
                taxiQuantity -= 1;
            } else {
                penaltyQuantity -= 1;
            }
        }

        // Так как банк у нас всего один, добавляем его в линию
        int bankPosition = coefficients.getRandomNumber(fromIndex, toIndex); // позиция отделения банка на линии
        this.fields[bankPosition] = new BankCell();
        for (int j = 0; j < emptyindexes.length; ++j) {
            if (emptyindexes[j] == bankPosition) {
                emptyindexes = removeTheElement(emptyindexes, j); // удаляем позицию банка из списка доступных позиций
            }
        }


        // Рандомно помещаем штрафные клетки
        for (int j = 0; j < penaltyQuantity; ++j) {
            int findFlag = 0;
            int randomposition = coefficients.getRandomNumber(fromIndex, toIndex); // выбираем случайную позицию из всех позиций

            for (int freeindex : emptyindexes) { // проверяем все доступные индексы
                if (randomposition == freeindex) { // если сгенерированная позиция свободна - закидываем туда штрафную клетку
                    this.fields[randomposition] = new PenaltyCell(coefficients); // Добавляем штрафную клетку

                    for (int k = 0; k < emptyindexes.length; ++k) {
                        if (emptyindexes[k] == randomposition) {
                            emptyindexes = removeTheElement(emptyindexes, k); // удаляем позицию банка из списка доступных позиций
                        }
                    }

                    findFlag = 1; // отмечаем флажок, что подходящий индекс найден
                    break;
                }
            }

            if (findFlag == 0) { // если он не найден, повторяем процесс для того же элемента
                --j;
            }
        }

        // Рандомно помещаем клетки такси
        for (int j = 0; j < taxiQuantity; ++j) {
            int findFlag = 0;
            int randomposition = coefficients.getRandomNumber(fromIndex, toIndex); // выбираем случайную позицию из всех позиций

            for (int freeindex : emptyindexes) { // проверяем все доступные индексы
                if (randomposition == freeindex) { // если сгенерированная позиция свободна - закидываем туда штрафную клетку
                    this.fields[randomposition] = new TaxiCell(); // Добавляем штрафную клетку

                    for (int k = 0; k < emptyindexes.length; ++k) {
                        if (emptyindexes[k] == randomposition) {
                            emptyindexes = removeTheElement(emptyindexes, k); // удаляем позицию банка из списка доступных позиций
                        }
                    }

                    findFlag = 1; // отмечаем флажок, что подходящий индекс найден
                    break;
                }
            }

            if (findFlag == 0) { // если он не найден, повторяем процесс для того же элемента
                --j;
            }
        }

        // Заполняем оставшиеся ячейки
        for (int j = 0; j < emptyindexes.length; ++j) {
            this.fields[emptyindexes[j]] = new ShopCell(coefficients);
        }
    }

    /**
     * Deletes element from array at particular index
     * @param arr array
     * @param index element position
     * @return edited(new) array
     * @since 1.0
     */
    public int[] removeTheElement(int[] arr, int index)
    {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        int[] anotherArray = new int[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}
