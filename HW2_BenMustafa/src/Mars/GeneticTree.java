package Mars;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class GeneticTree<T> {

    Martian<T> rootPerson;

    public GeneticTree(Martian<T> person) {
        rootPerson = person;
    }

    public void novatorToCoservator() {
        rootPerson = ((Novator<T>)rootPerson).novatorToConservator();
    }

    public void TreeToText(Martian<T> currentMartian, int level, String path) {
        String genCodeType = currentMartian instanceof Novator ? String.valueOf(((Novator<T>)currentMartian).genCode) : String.valueOf(((Conservator<T>)currentMartian).genCode);
        String typeOfT = currentMartian.zzz.getClass().getName();
        String className = this.getClass().getName();

        for (int i = 0; i < level; ++i) {
            System.out.print(" ");
        }
        System.out.print(String.format("%s (%s:%s)", className, typeOfT, genCodeType));

        List<Martian<T>> children = currentMartian.getChildrenList();
        System.out.println();

        for (int i = 0; i < children.size(); ++i) {
            TreeToText(children.get(i), level + 1, path);
        }
    }

    public void TextToTree(String _path) {
        try (FileReader fr = new FileReader(_path)) {
            Path path = Paths.get("/Users/anasbenmustafa/Desktop/text.txt");
            List<String> lines = Files.readAllLines(path); // Массив из строк снизу вверх
            List<Integer> spacesInTree = new ArrayList<Integer>(); // Массив из количества пробелов для соотв. строк

            if (lines.get(0).contains("Innovator")) { // Если дерево инноваторов

                for (int i = lines.size() - 1; i >= 0; --i) { // Проходим по всем строкам в файле

                    int quantityOfSpaces = 0; // Кол-во пробелов изначально равно нулю

                    for (int k = 0; k < lines.get(i).length(); k++) { // Для каждой строки считаем количество пробелов

                        char a = lines.get(i).charAt(k); // Получаем текущий символ

                        if (a == ' ') { // Если текущий символ - пробел, увеличиваем количество пробелов
                            quantityOfSpaces += 1; // Увеличиваем число пробелов для конкретной строки
                        }
                    }
                    spacesInTree.add(quantityOfSpaces - 1); // Добавляем в массив кол-во пробелов на соотв. позицию
                }

            }

        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
