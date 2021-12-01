package ru.alexanna.lesson_6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ArrayLibTest {
private ArrayLib arrayLib;

    @BeforeEach
    void init() {
        arrayLib = new ArrayLib();
    }

    @ParameterizedTest
    @MethodSource("getItemsAfter4DataProvider")
    void shouldPerformGetItemsAfter4_whenValidArrayPassed(int[] resultArray, int[] inputArray) {
        Assertions.assertArrayEquals(resultArray, arrayLib.getItemsAfter4(inputArray));
    }

    private static Stream<Arguments> getItemsAfter4DataProvider() {
        return Stream.of(
                Arguments.of(new int[] {1, 2, 3}, new int[] {1,2,3,4,1,2,3}),
                Arguments.of(new int[] {1,2,3,1,2}, new int[] {4,1,2,3,1,2}),
                Arguments.of(new int[] {}, new int[] {1,2,3,4})
        );
    }

    @Test
    void shouldPerformGetItemsAfter4_whenRuntimeExceptionWillHappen() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            arrayLib.getItemsAfter4(new int[] {1,1,1,1,});
        });
    }

    @ParameterizedTest
    @MethodSource("checkArrayOperationWithNumbers1and4DataProvider")
    void shouldPerformCheckArray_whenArrayWithNumbers1and4Passed(int[] array) {
        Assertions.assertTrue(arrayLib.checkArrayForGivenContent(array));
    }

    private static Stream<Arguments> checkArrayOperationWithNumbers1and4DataProvider() {
        return Stream.of(
                Arguments.of(new int[] {1, 1, 1, 4, 4, 1, 4, 4}),
                Arguments.of(new int[] {4, 1})
        );
    }

    @ParameterizedTest
    @MethodSource("checkArrayOperationWithDifferentDigitsDataProvider")
    void shouldPerformCheckArray_whenArrayWithDifferentDigitsPassed(int[] array) {
        Assertions.assertFalse(arrayLib.checkArrayForGivenContent(array));
    }

    private static Stream<Arguments> checkArrayOperationWithDifferentDigitsDataProvider() {
        return Stream.of(
                Arguments.of(new int[] {1, 1, 1, 1, 1, 1, 1, 1}),
                Arguments.of(new int[] {4, 4}),
                Arguments.of(new int[] {1, 1, 4, 1, 3, 1, 1, 1}),
                Arguments.of(new int[] {4, 4, 4, 6})
        );
    }
}