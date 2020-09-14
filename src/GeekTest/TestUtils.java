package GeekTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//package private Содержит статик методы, используемые в пакете
class TestUtils {

    private static final String[] ANNOTATIONS = {"GeekTest.Test", "GeekTest.BeforeSuite", "GeekTest.AfterSuite"};

    /**
     * Проверка, нужно ли обрабатывать аннотацию
     *
     * @param anno
     * @return
     */
    static boolean checkAnnotation(String anno) {
        for (String s : ANNOTATIONS) {
            if (s.equalsIgnoreCase(anno)) return true;
        }
        return false;
    }

    /**
     * Получаем приоритет метода из аннотации
     * @param annotation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    static int getMethodPriority(Annotation annotation) throws InvocationTargetException, IllegalAccessException {
        //У аннотации ищем метод, позволяющий получить приоритет
        for (Method met : annotation.getClass().getMethods()) {
            if (met.getName().equalsIgnoreCase("priority")) {
                if (met.getReturnType().equals(TestPriority.class)) {
                    return ((TestPriority) met.invoke(annotation)).getOrder();
                }
            }
        }
        return 0;
    }

    /**
     * Получение признака того, что метод может быть запущен только один раз
     * @param annotation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    static boolean getMethodSingleRun(Annotation annotation) throws InvocationTargetException, IllegalAccessException {
        //У аннотации ищем метод, позволяющий получить признак однократности запуска
        for (Method met : annotation.getClass().getMethods()) {
            if (met.getName().equalsIgnoreCase("isSingleRun")) {
                if (met.getReturnType().equals(TestPriority.class)) {
                    return ((TestPriority) met.invoke(annotation)).isSingleRun();
                }
            }
        }
        return true;
    }

    /**
     * Создает инстанс объекта через конструктор без параметров
     *
     * @param aClass
     * @return
     */
    static Object newInstance(Class aClass) {
        Constructor<Object>[] constructors = aClass.getConstructors();
        for (Constructor<Object> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                try {
                    System.out.println("Запуск конструктора без параметров");
                    return constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.printf("Невозможно создать объект класса %s\n", aClass.getName());
                    e.printStackTrace();
                    return null;
                }
            }
        }
        System.out.printf("У тестируемого класса %s найден конструктор без параметров!\n", aClass.getName());
        return null;
    }

}
