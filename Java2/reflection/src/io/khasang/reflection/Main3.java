package io.khasang.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main3 {
    public static void main(String[] args) throws NoSuchMethodException {
//        Class<Test> manager = Test.class;
//        Class<TestAbstract> manager = TestAbstract.class;
        Class<VoidClass> manager = VoidClass.class;

        Package aPackage = manager.getPackage();
        System.out.printf("package %s;%n", aPackage.getName());

        int modifiers = manager.getModifiers(); // 101010101
//        Modifier
//        modifiers = 1; // 00001
//        modifiers = 3; // 00011
//        modifiers = 5; // 00101
//        System.out.println(Modifier.isPublic(modifiers));
//        System.out.println(Modifier.isPrivate(modifiers));
//        System.out.println(Modifier.toString(modifiers));

        System.out.printf("%s %s %s", Modifier.toString(modifiers),
                manager.isInterface() ? "interface" : "class", manager.getSimpleName());

//        System.out.printf(" extends %s", manager.getSuperclass().getSimpleName());
        if (manager.getSuperclass() != Object.class) {
            System.out.printf(" extends %s", manager.getSuperclass().getSimpleName());
        }

        if (manager.getInterfaces().length != 0) {
            System.out.print(" implements ");
            Class<?>[] interfaces = manager.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {
                Class<?> anInterface = interfaces[i];
                System.out.print(i == 0 ? "" : ", ");
                System.out.print(anInterface.getSimpleName());
            }
        }
        System.out.println(" {");

        Field[] fields = manager.getDeclaredFields();

        if (fields.length != 0) {
            for (Field field : fields) {
                System.out.printf("\t%s %s %s;%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName());
            }
        }

        Constructor<?>[] declaredConstructors = manager.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            System.out.printf("\t%s %s(%s) {}%n",
                    Modifier.toString(constructor.getModifiers()),
                    manager.getSimpleName(),
                    getParameters(constructor.getParameterTypes()));
        }

        System.out.println();

        Method[] methods = manager.getDeclaredMethods();
        if (methods.length != 0) {
            for (Method method : methods) {
                System.out.printf("\t%s %s(%s) {}%n",
                        Modifier.toString(method.getModifiers()),
                        manager.getSimpleName(),
                        getParameters(method.getParameterTypes()));
            }
        }
        System.out.println("}");
    }

    private static String getParameters(Class<?>[] parametersType) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parametersType.length; i++) {
            Class<?> parameter = parametersType[i];
            result.append(i == 0 ? "" : ", ")
                    .append(parameter.getSimpleName())
                    .append(" p")
                    .append(i);
        }
        return result.toString();
    }
}
