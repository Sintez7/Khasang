package app;

import app.legacy.Product;

import java.util.*;

public class App {
    final int NUMBER_OF_PRODUCTS = 100000;
    List<Product> list1 = new ArrayList<>(NUMBER_OF_PRODUCTS);
    List<Product> list2 = new ArrayList<>(NUMBER_OF_PRODUCTS);

    Random random = new Random();

    public void start() {
        System.out.println("Data initializing");
        System.out.println();
        initData();
        System.out.println("====== find Duplicates Brute start ======");
        findDuplicatesBrute(list1, list2);
        System.out.println();
        System.out.println("====== find Duplicates Smart start ======");
        findDuplicatesSmart(list1, list2);
    }

    private void initData() {
        initList1();
        initList2();
    }

    private void initList1() {
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            Product product = new Product();
            product.setCategory(getCategory());
            product.setInternalCode(getInternalCode());
            product.setName(getName(i));
            list1.add(product);
        }
    }

    private void initList2() {
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            Product product = new Product();
            // Взяв каждый сотый элемент из 100 000 - обесепечиваем 1000 совпадений
            // При этом, их адрес в памяти будет различаться
            if (i % 100 == 0) {
                product.setCategory(list1.get(i).getCategory());
                product.setInternalCode(list1.get(i).getInternalCode());
                product.setName(list1.get(i).getName());
            } else {
                product.setCategory(getCategory());
                product.setInternalCode(getInternalCode());
                product.setName(getName(i));
            }
            list2.add(product);
        }
    }

    // Методы для заполнения объектов случайными наборами данных
    private String getCategory() {
        double temp = Math.random();
        String result;
        if (temp < 0.33) {
            result = "Food";
        } else if (temp > 0.66) {
            result = "Clothing";
        } else {
            result = "Tool";
        }
        return result;
    }

    // modifier - номер в итерации при заполнении списка
    private String getName(int modifier) {
        double temp = Math.random();
        String result;
        if (temp < 0.33) {
            result = "Something good #" + modifier;
        } else if (temp > 0.66) {
            result = "Something bad #" + modifier;
        } else {
            result = "Something #" + modifier;
        }
        return result;
    }

    private byte[] getInternalCode() {
        byte[] result = new byte[16];
        random.nextBytes(result);
        return result;
    }

    private void findDuplicatesBrute(List<Product> list1, List<Product> list2) {
        // Одинаковыми считаем продукты, у которых совпадают все 3 поля: name, category, internalCode.
        long startStamp = System.currentTimeMillis();
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            for (int j = 0; j < NUMBER_OF_PRODUCTS; j++) {
                if (bruteCompare(list1.get(i), list2.get(j))) {
                    result.add(list1.get(i));
                }
            }
        }

        System.out.println("Array size :" + result.size());
        showElapsedTime(startStamp);
    }

    private boolean bruteCompare(Product o1, Product o2) {
        return o1.getCategory().equals(o2.getCategory())
                && Arrays.equals(o1.getInternalCode(), o2.getInternalCode())
                && o1.getName().equals(o2.getName());
    }

    /* Так как в условиях задачи не указано, могу ли я менять метод хранения,
     * для выполнения я решил изменить то, как хранятся объекты Product.
     * В условиях задачи требуется посчитать затраченное время на выполнение сравнения,
     * но о времени затраченном на подготовку ничего не говорится.
     * Однако, данный метод работает довольно шустро, и я могу взять в расчёт в том числе и подготовку.
     */

    private void findDuplicatesSmart(List<Product> list1, List<Product> list2) {
        long startStamp = System.currentTimeMillis();

        Set<MyProduct> set1 = new HashSet<>();
        for (Product p : list1) {
            set1.add(new MyProduct(p));
        }

        Set<MyProduct> set2 = new HashSet<>();
        for (Product p : list2) {
            set2.add(new MyProduct(p));
        }

        set1.retainAll(set2);

        showElapsedTime(startStamp);
        System.out.println("Set elements :" + set1.size());
    }

    private void showElapsedTime(long startStamp) {
        System.out.println((System.currentTimeMillis() - startStamp) + " ms");
        System.out.println((System.currentTimeMillis() - startStamp) / 1000 + " seconds");
    }
}
