/* Напишите по памяти или пересмотрите лекцию по выводу таблицы умножения в консоль с помощью вложенных циклов.
   Сделайте так, чтобы вывод напоминал визуально то, как подобная таблица умножения выглядит в школьной тетради.
   То есть у вас должно получиться четыре колонки и два ряда

   Ага, сделали? Дополнительное задание.
   Основная цель практических работ – развить архитектурную гибкость мышления. Мы привыкаем видеть не один путь решения, а сразу несколько.
   Поэтому вам нужно это задание сделать тремя разными способами.

   //----------------------------------

   На сайте в качестве примера приведена таблица где до 5 считается с умножением на 10, а после - без него.
   Не знаю, ошибка ли это, ведь ниже приведён скрин с таблицей а там считается до 10.
   Первые два метода сделаю как в образце, потому что могу.
   Раз уж урок про применение цикла for, в третьем варианте использую его бесконечную версию.
   За прерывание будет отвечать ввод пользователя, либо внутренняя ошибка при парсе.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) {
        System.out.println("Метод 1:\n");   // прямой счёт (работа с примитивами)
        method1();
        System.out.println("Метод 2:\n");   // получить массив и отобразить его (работа с ссылочными типами данных)
        method2();
        System.out.println("Метод 3:\n");   // делегирование другому методу (например через интерфейс)
        method3();
    }

    private static void method1() {
        int offset = 0;
        int multTo = 10;
        for(int bigRow = 0; bigRow < 2; bigRow++) {
            for(int multiplier = 1; multiplier <= multTo; multiplier++) {
                for(int firstNum = 2; firstNum <= 5; firstNum++) {
                    System.out.printf("%d * %d = %d\t\t", firstNum + offset, multiplier, (firstNum + offset) * multiplier);
                }
                System.out.println();
            }
            offset += 4;
            multTo = 9;
            System.out.println();
        }
    }

    private static void method2() {
        ArrayList<String> mathSheet = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for(int column = 1; column <= 10; column++) {
            for(int row = 2; row <= 5; row++) {
                str.append(row).append(" * ").append(column).append(" = ").append(column * row).append("\t\t");
            }
            mathSheet.add(str.toString());
            str.delete(0, str.length());
        }
        mathSheet.add("");
        for(int column = 1; column <= 9; column++) {
            for(int row = 6; row <= 9; row++) {
                str.append(row).append(" * ").append(column).append(" = ").append(row * column).append("\t\t");
            }
            mathSheet.add(str.toString());
            str.delete(0, str.length());
        }
        for(String s : mathSheet) {
            System.out.println(s);
        }
        System.out.println();
    }

    private static void method3() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int from;
        int to;
        String temp;
        for(;;) {
            System.out.println("Input starting number of math sheet.\n" +
                    "Available range 1 - 9 inclusive.\n" +
                    "Press Enter with empty line to close.");
            try {
                temp = reader.readLine();
                if(temp.equals("")) break;
                from = Integer.parseInt(temp);
                if (from > 9) {
                    System.out.println("Error! Inputted number is higher than 9.");
                    break;
                }
                if (from < 1) {
                    System.out.println("Error! Inputted number is less than 1.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getLocalizedMessage());
                break;
            }

            System.out.println("Input ending number of math sheet.\n" +
                    "Available range 1 - 9 inclusive.\n" +
                    "Ending number MUST be lower than starting\n" +
                    "Press Enter with empty line to close.");
            try {
                temp = reader.readLine();
                if(temp.equals("")) break;
                to = Integer.parseInt(temp);
                if (to > 9) {
                    System.out.println("Error! Inputted number is higher than 9.");
                    break;
                }
                if (to < 1) {
                    System.out.println("Error! Inputted number is less than 1.");
                    break;
                }
                if (to < from) {
                    System.out.println("Error: Ending number higher than starting!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getLocalizedMessage());
                break;
            }
            System.out.println("Result: ");
            printMathSheet(from, to);
        }
    }

    private static void printMathSheet(int from, int to) {
        for(int row = 1; row <= 10; row++) {
            for(int column = from; column <= to; column++) {
                System.out.printf("%d * %d = %d\t\t", column, row, column * row);
            }
            System.out.println();
        }
        System.out.println();
    }
}
