public class Reception {
    static void greet() {
        System.out.println("Heelo from greet()!");
        manage();
    }

    static void manage() {
        //sout+Tab
        System.out.println("Manage smth");
        System.out.println(2 + 2);
    }

    static void clean() {
        System.out.println("Что-нибудь убрали");
    }

    //psvm+Tab
    public static void main(String[] args) {
        System.out.println("Департамент Reception");
        greet();
        clean();
    }

    //123
}
