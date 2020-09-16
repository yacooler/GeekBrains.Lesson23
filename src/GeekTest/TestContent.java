package GeekTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.SortedMap;
import java.util.TreeMap;

import static GeekTest.TestUtils.*;


//package private класс, отвечающий за работу тестов
class TestContent {

    boolean runTest(Class aClass) {
        //1. Создаем инстанс объекта (в задании мы не передаем объект, только класс, поэтому экземпляр делаем через Constructor.newInstance())
        Object object = newInstance(aClass);
        if (object == null) return false;
        //2. Создаем список методов в порядке их вызова
        SortedMap<Integer, Method> methodList = getMethods(object);
        if (methodList == null) return false;
        if (methodList.isEmpty()) {
            System.out.println("Класс не содержит аннотаций для тестирования!");
            return false;
        }
        //3. Запускаем методы
        for (Method method : methodList.values()) {
            System.out.printf("Запуск метода %s\n", method.getName());
            try {
                method.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Не удалось запустить метод!");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    /**
     * Отсортированный список методов для запуска
     *
     * @param object
     * @return
     */
    private SortedMap<Integer, Method> getMethods(Object object) {

        //По умолчанию сортируется по ключу, что нам и требуется
        SortedMap<Integer, Method> methods = new TreeMap<>();

        //Бежим по методам
        for (Method method : object.getClass().getMethods()) {

            //Ищем аннотации
            for (Annotation annotation : method.getAnnotations()) {
                //Если наша аннотация
                if (checkAnnotation(annotation.annotationType().getName())) {
                    //Вычитываем приоритет метода из аннотации и признак однократности запуска
                    try {
                        int methodPriority = getMethodPriority(annotation);
                        boolean methodSingleValue = getMethodSingleRun(annotation);

                        //Проверим, не было ли такого метода ранее
                        if (methodSingleValue && methods.containsKey(methodPriority)){
                            throw new RuntimeException(String.format("Метод с аннотацией %s может быть запущен только один раз!", annotation.annotationType().getName()));
                        }
                        //Добавляем метод в мапу
                        methods.put(methodPriority, method);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        System.out.println("Не удалось определить приоритет вызова метода");
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        return methods;
    }
}
