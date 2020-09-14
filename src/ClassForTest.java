import GeekTest.AfterSuite;
import GeekTest.BeforeSuite;
import GeekTest.Test;
import GeekTest.TestPriority;

public class ClassForTest {
    private int a = 0;

    public ClassForTest() {

    }

    @Test(priority = TestPriority.RUN_4)
    public void test4(){
        //тест приоритета
        System.out.println("priority 4 - done");
    }

    @BeforeSuite
    public void before(){
        //тест запуска до всего
        System.out.println("before - done");
    }

    @AfterSuite
    public void after(){
        //тест запуска после всего
        System.out.println("after all - done");
    }

    @Test
    public void test0(){
        //Тест приоритета по умолчанию
        System.out.println("default priority - done");
    }

    //Переопределил toString просто чтобы тестировать только свои аннотации
    @Override
    public String toString() {
        return String.valueOf(a);
    }
}
