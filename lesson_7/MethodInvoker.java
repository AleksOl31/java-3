package ru.alexanna.lesson_7;

import ru.alexanna.lesson_7.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class MethodInvoker {

    public void singleAnnotatedMethodInvoke(Class<? extends Annotation> aClass, List<Method> methods, Object object) {
        List<Method> annotatedMethods = findAnnotatedMethods(aClass, methods);
        singleUseCheck(annotatedMethods);
        singleInvoke(annotatedMethods, object);
    }

    public void testAnnotatedMethodInvoke(List<Method> methods, Object object) {
        List<Method> testMethods = findAnnotatedMethods(Test.class, methods);
        testMethods.sort(Comparator.comparingInt((priority) -> priority.getAnnotation(Test.class).priority()));
        testMethods.forEach(method -> {
            invoke(object, method);
        });
    }

    private void singleInvoke(List<Method> methods, Object object) {
        if (methods.size() == 1) {
            invoke(object, methods.get(0));
        }
    }

    private void singleUseCheck(List<Method> methods) {
        if (methods.size() > 1) {
            throw new RuntimeException("This annotation can be present only once.");
        }
    }

    private void invoke(Object object, Method method) {
        try {
            method.setAccessible(true);
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(false);
        }
    }

    private List<Method> findAnnotatedMethods(Class<? extends Annotation> aClass, List<Method> methods) {
        return methods.stream().filter(method -> method.isAnnotationPresent(aClass)).collect(Collectors.toList());
    }
}
