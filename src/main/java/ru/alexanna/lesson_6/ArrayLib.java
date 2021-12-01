package ru.alexanna.lesson_6;

import java.util.Arrays;

public class ArrayLib {
    public int[] getItemsAfter4(int[] array) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i + 1;
            }
        }
        if (index < 0) {
            throw new RuntimeException("Required element is missing");
        }
        return Arrays.copyOfRange(array, index, array.length);
    }

    public boolean checkArrayForGivenContent(int[] array) {
        for (int item: array) {
            if (item != 1 & item != 4) {
                return false;
            }
        }
        boolean is1Present = findNumber(1, array);
        boolean is4Present = findNumber(4, array);
        return is1Present & is4Present;
    }

    private boolean findNumber(int number, int[] array) {
        for (int item: array) {
            if (item == number) return true;
        }
        return false;
    }
}
