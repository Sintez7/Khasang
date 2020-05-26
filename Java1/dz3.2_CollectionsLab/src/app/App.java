package app;

import app.legacy.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class App {
    final int NUMBER_OF_PRODUCTS = 1000;
    List<Product> list1 = new ArrayList<>(NUMBER_OF_PRODUCTS);
    List<Product> list2 = new ArrayList<>(NUMBER_OF_PRODUCTS);

    Random random = new Random();

    public void start() {
        initData();
//        findDuplicatesBrute(list1, list2);
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
            if (i % 10 == 0) {
                list2.add(list1.get(random.nextInt(NUMBER_OF_PRODUCTS)));
            } else {
                Product product = new Product();
                product.setCategory(getCategory());
                product.setInternalCode(getInternalCode());
                product.setName(getName(i));
                list2.add(product);
            }
        }
    }

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
        byte[] result = new byte[4]; //для тестов, пускай будет массив с 4 значениями
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
        for (Product product : result) {
            System.out.println(product);
        }
        showElapsedTime(startStamp);
    }

    private boolean bruteCompare(Product o1, Product o2) {
        return o1.getCategory().equals(o2.getCategory())
                && Arrays.equals(o1.getInternalCode(), o2.getInternalCode())
                && o1.getName().equals(o2.getName());
    }

    private void findDuplicatesSmart(List<Product> list1, List<Product> list2) {
        long startStamp = System.currentTimeMillis();
        List<Product> result = new ArrayList<>(list1);

        result.removeAll(list2);
        System.out.println("Array size :" + result.size());
        for (Product product : result) {
            System.out.println(product);
        }
        showElapsedTime(startStamp);
    }

    private void showElapsedTime(long startStamp) {
        System.out.println((System.currentTimeMillis() - startStamp) + " ms");
        System.out.println((System.currentTimeMillis() - startStamp) / 1000 + " seconds");
    }
}
