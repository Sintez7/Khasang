/*
 * Написать код,
 * 1) который при запуске спрашивает у пользователя три числа и сохраняет их в три переменные k, t, n;
 * 2) создает два массива целых чисел (arr1 и arr2), каждый длинной n элементов.
 * 3) заполняет первый массив по формуле
 * arr1[i] = k * (i - t), где arr1[i] - i-ый элемент первого массива
 * 4) заполняет второй массив по формуле
 * arr2[i] = (k + t - i), где arr2[i] - i-ый элемент второго массива
 * 5) выводит содержимое обоих массивов
 * Если ввести:
 * 10
 * 100
 * 5
 *
 * То ответом будет:
 * [-1000 -990 -980 -970 -960]
 * [110 109 108 107 106]
 */

import java.util.Arrays;
import java.util.Scanner;

public class Exercise2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первое целое число");
        int k = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Введите второе целое число");
        int t = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Введите n");
        int n = scanner.nextInt();

        scanner.nextLine();

        int[] arr1 = new int[n];
        int[] arr2 = new int[n];

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = k * (i - t);
            arr2[i] = (k + t - i);
        }

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }
}
