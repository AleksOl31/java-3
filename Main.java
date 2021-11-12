package ru.alexanna.lesson_1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] array = {"1D","2D","3D","4D","5D"}; //{1D,2D,3D,4D,5D};

        // Task #1
        swapElements(array, 0, 3);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println();

        // Task #2
        ArrayList<String> arrayList = convertArray(array);
        System.out.println(arrayList);

        // Task #3
        fruitInBox();
    }

    // Task #1
    public static <T> void swapElements(T[] array, int pos1, int pos2) {
        if (pos1 < array.length && pos2 < array.length) {
            T temp = array[pos1];
            array[pos1] = array[pos2];
            array[pos2] = temp;
        }
    }

    // Task #2
    public static <E> ArrayList<E> convertArray(E[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    // Task #3
    public static void fruitInBox() {
        Box<Apple> appleBox1 = new Box<>(new Apple());
        for (int i = 0; i < 3; i++) {
            appleBox1.add(new Apple());
        }
        System.out.println("Weight appleBox1: " + appleBox1.getWeight());
        Box<Apple> appleBox2 = new Box<>(new Apple());
        for (int i = 0; i < 6; i++) {
            appleBox2.add(new Apple());
        }
        System.out.println("Weight appleBox2: " + appleBox2.getWeight());
        Box<Orange> orangeBox1 = new Box<>(new Orange());
        for (int i = 0; i < 2; i++) {
            orangeBox1.add(new Orange());
        }
        System.out.println("Weight orangeBox1: " + orangeBox1.getWeight());
        Box<Orange> orangeBox2 = new Box<>(new Orange());
        for (int i = 0; i < 4; i++) {
            orangeBox2.add(new Orange());
        }
        System.out.println("Weight orangeBox2: " + orangeBox2.getWeight());
        System.out.println("Compare appleBox1 and orangeBox1: " + appleBox1.compare(orangeBox1));
        System.out.println("Compare appleBox1 and orangeBox2: " + appleBox2.compare(orangeBox1));

        if (appleBox1.pourFruitInto(appleBox2)) {
            System.out.println("Weight appleBox1: " + appleBox1.getWeight());
            System.out.println("Weight appleBox2: " + appleBox2.getWeight());
        }

    }

}
