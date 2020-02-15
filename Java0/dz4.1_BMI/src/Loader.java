/*
Напишите метод, который бы принимал параметрами вес и рост, а возвращал индекс массы тела!

@Google
Индекс массы тела рассчитывается путем деления массы тела (в килограммах)
на квадрат роста (в метрах квадратных). Например, определим индекс массы
тела для человека, который весит 65 кг при росте 170 см: ИМТ = 65 / 1,7×1,7 = 22,5.
 */

public class Loader {

    public static void main(String[] args) {
        float height = 170;
        float mass = 65;

        System.out.println(calcBMI(height, mass));
    }

    private static float calcBMI(float height, float mass) {
        return mass / ((height / 100) * (height / 100));
    }
}
