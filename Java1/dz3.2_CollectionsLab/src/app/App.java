package app;

import app.legacy.Product;

import java.util.*;

public class App {
    final int NUMBER_OF_PRODUCTS = 100000;
    List<Product> list1 = new ArrayList<>(NUMBER_OF_PRODUCTS);
    List<Product> list2 = new ArrayList<>(NUMBER_OF_PRODUCTS);

    Random random = new Random();

    public void start() {
        initData();
//        findDuplicatesBrute(list1, list2);
        findDuplicatesSmart(list1, list2);
//        test();
    }

    private void test() {
        Product product1 = new Product();
        product1.setCategory(getCategory());
        product1.setInternalCode(getInternalCode());
        product1.setName(getName(1));

        Product product2 = new Product();
        product2.setCategory(getCategory());
        product2.setInternalCode(getInternalCode());
        product2.setName(getName(2));

        MyProduct my1 = new MyProduct(product1);
        MyProduct my2 = new MyProduct(product1);
        MyProduct my3 = new MyProduct(product2);

        System.out.println(my1.equals(my2));
        System.out.println(my1.equals(my3));
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

    /* Так как в условиях задачи не указано, могу ли я менять метод хранения,
     * для выполнения я решил изменить то, как хранятся объекты Product.
     * В условиях задачи требуется посчитать затраченное время на выполнение поиска,
     * но о времени затраченном на подготовку ничего не говорится.
     */

    private void findDuplicatesSmart(List<Product> list1, List<Product> list2) {
        long startStamp = System.currentTimeMillis();

//        Set<MyProduct> set1 = new HashSet<>();
//        for (Product p : list1) {
//            set1.add(new MyProduct(p));
//        }
//
//        Set<MyProduct> set2 = new HashSet<>();
//        for (Product p : list2) {
//            set2.add(new MyProduct(p));
//        }

//        Set<MyProduct> set3 = new HashSet<>();
//        for (int i = 0; i < list1.size(); i++) {
//            set3.add(new MyProduct(list1.get(i)));
//        }
//
//        Set<MyProduct> set4 = new HashSet<>();
//        for (int i = 0; i < list2.size(); i++) {
//            set4.add(new MyProduct(list2.get(i)));
//        }

//        Map<Integer, List<MyProduct>> map1 = new HashMap<>();
//        for (int i = 0; i < list1.size(); i++) {
//            MyProduct temp = new MyProduct(list1.get(i));
//            if (map1.containsKey(temp.getCachedHashCode())) {
//                map1.get(temp.getCachedHashCode()).add(temp);
//            } else {
//                List<MyProduct> tempList = new ArrayList<>();
//                tempList.add(temp);
//                map1.put(temp.getCachedHashCode(), tempList);
//            }
//        }

//        set1.retainAll(set2);

//        List<MyProduct> result = retainDuplicates(map1, set2);

        List<LinkedList<MyProduct>> tempList = new ArrayList<>(NUMBER_OF_PRODUCTS);
        for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
            tempList.add(new LinkedList<MyProduct>());
        }

        showElapsedTime(startStamp);
//        System.out.println("Set elements :" + set1.size());
//        for (MyProduct p : set1) {
//            System.out.println(p);
//        }
        System.out.println();
    }

//    private ArrayList<MyProduct> retainDuplicates(Map<Integer, List<MyProduct>> map, Set<MyProduct> set) {
//
//        ArrayList<MyProduct> result = new ArrayList<>();
//
//        for (int i = 0; i < map.size(); i++) {
//            for (MyProduct p : set) {
//                if (map.containsKey(p.getCachedHashCode())) {
//                    result.add(p);
//                }
//            }
//        }
//        return result;
//    }

//    for (int j = 0; j < set.size(); j++) {
//        if (map.containsKey(set.)) {
//
//        }
//    }

    private void showElapsedTime(long startStamp) {
        System.out.println((System.currentTimeMillis() - startStamp) + " ms");
        System.out.println((System.currentTimeMillis() - startStamp) / 1000 + " seconds");
    }
}
