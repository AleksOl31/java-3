package ru.alexanna.lesson_7;

import ru.alexanna.lesson_7.annotations.AfterSuite;
import ru.alexanna.lesson_7.annotations.BeforeSuite;
import ru.alexanna.lesson_7.annotations.Test;

public class MyClassForTesting {

    @BeforeSuite
    void init() {
        System.out.println("initTests");
    }

    @AfterSuite
    void terminatingFunction() {
        System.out.println("test completed");
    }

    @Test(priority = 1)
    void firstPriorityTest() {
        System.out.println("firstPriorityTest");
    }

    @Test(priority = 2)
    void secondPriorityTest() {
        System.out.println("secondPriorityTest");
    }

    @Test
    void defaultPriorityTest() {
        System.out.println("defaultPriorityTest");
    }

    @Test
    void defaultPriorityTestTwo() {
        System.out.println("defaultPriorityTestTwo");
    }

    @Test(priority = 3)
    void thirdPriorityTest() {
        System.out.println("thirdPriorityTest");
    }

    @Test(priority = 4)
    void forthPriorityTest() {
        System.out.println("forthPriorityTest");
    }

    @Test(priority = 10)
    void tenPriorityTest() {
        System.out.println("tenPriorityTest");
    }
}
