package ru.alexanna.lesson_7;

import ru.alexanna.lesson_7.annotations.AfterSuite;
import ru.alexanna.lesson_7.annotations.BeforeSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class PerformTest {

    public static <T> void start(Class<T> testClass) {
        T testObject = createTestObject(testClass);
        List<Method> testMethodList = Arrays.asList(testClass.getDeclaredMethods());
        MethodInvoker methodInvoker = new MethodInvoker();

        methodInvoker.singleAnnotatedMethodInvoke(BeforeSuite.class, testMethodList, testObject);
        methodInvoker.testAnnotatedMethodInvoke(testMethodList, testObject);
        methodInvoker.singleAnnotatedMethodInvoke(AfterSuite.class, testMethodList, testObject);
    }

    private static <T> T createTestObject(Class<T> testClass) {
        try {
            return testClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("RuntimeException", e);
        }
    }
}
