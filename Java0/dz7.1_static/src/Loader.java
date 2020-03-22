/*
 * 1. Сделайте класс Кошка так, чтобы у каждой кошки был индивидуальный номер по порядку,
 * который бы увеличивался для новой кошки каждый раз, когда создается новая кошка.
 * Для это используйте static и конструктор.
 *
 * 2. Пусть у всех кошек будет одна на всех миска и одна на всех тарелка.
 * В тарелке и миске храните кол-во оставшейся еды/воды.
 * Реализуйте двумя путями:
 * а) Через статические переменные внутри класса Кошка.
 * б) Через отдельный класс Миска (Bowl) и отдельный класс Тарелка (Plate), но уже
 * без использования static. Обратите внимание, что
 * в классе Миска не должно быть ни статических полей, ни статических методов.
 * Чтобы вы могли при желании создать сколько угодно общих тарелок.
 *
 * 3. Проверьте, что все работает
 * а) Из другого класса (например, Main) создайте несколько кошек и выведите их номер,
 * чтобы было видно, что нумерация кошек работает.
 * б) Встречается одна типичная ошибка, чтобы ее отловить (если она вдруг закралась к вам),
 * сделайте следующее: в конце проверочной программы снова выведите информацию по первой кошке,
 * так вы проверите, действительно ли у каждой кошки есть свой индивидуальный номер и он сохраняется
 * в ее полях класса.
 * в) Сделайте так, чтобы кошки пили и ели из тарелки и миски (статических).
 * Отобразите, сколько съела/выпила каждая кошка и сколько еды/воды
 * осталось в тарелке и миске.
 * г) Создайте экземпляры классов Тарелка и Миска.
 * д) Сделайте так, чтобы кошки пили и ели из этих экземпляров класса.
 * Отобразите, сколько съела/выпила каждая кошка и сколько еды/воды
 * осталось в тарелке и миске.
 */

public class Loader {

    public static void main(String[] args) {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        Cat cat3 = new Cat();

        System.out.println(cat1.getNumber());
        System.out.println(cat2.getNumber());
        System.out.println(cat3.getNumber());
        System.out.println(cat1.getNumber());

        System.out.println("shared bowl food count = " + Cat.getSharedBowl());
        System.out.println("shared plate water count = " + Cat.getSharedPlate());
        System.out.println("=======================================");

        System.out.println("cat1 eat static");
        cat1.eatStatic();
        System.out.println("cat1 drink static");
        cat1.drinkStatic();
        System.out.println("cat2 eat static");
        cat2.eatStatic();
        System.out.println("cat2 drink static");
        cat2.drinkStatic();
        System.out.println("cat3 eat static");
        cat3.eatStatic();
        System.out.println("cat3 drink static");
        cat3.drinkStatic();
        System.out.println("=======================================");

        cat1.info();
        cat2.info();
        cat3.info();
        System.out.println("=======================================");

        Bowl bowl1 = new Bowl();
        Plate plate1 = new Plate();
        Bowl bowl2 = new Bowl();
        Plate plate2 = new Plate();
        Bowl bowl3 = new Bowl();
        Plate plate3 = new Plate();

        System.out.println("cat1 eat");
        cat1.eat(bowl1);
        System.out.println("cat1 drink");
        cat1.drink(plate1);
        System.out.println("cat2 eat");
        cat2.eat(bowl2);
        System.out.println("cat2 drink");
        cat2.drink(plate2);
        System.out.println("cat3 eat");
        cat3.eat(bowl3);
        System.out.println("cat3 drink");
        cat3.drink(plate3);
        System.out.println("=======================================");

        cat1.info();
        cat2.info();
        cat3.info();
    }
}
