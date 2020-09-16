package GeekTest;

//Публичный класс, отвечающий за запуск тестов
public class TestRun {

    public static void start(String className) throws ClassNotFoundException {
        start(Class.forName(className));
    }

    public static void start(Class aClass) {
        System.out.printf("Запуск теста для класса %s\n---------------------------------------\n", aClass.getName());
        if (new TestContent().runTest(aClass)) {
            System.out.println("---------------------------------------\nТест завершен");
        } else {
            System.out.println("---------------------------------------\nТест провален");
        }
    }
}
