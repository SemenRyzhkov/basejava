package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStreamsEx {
    public static void main(String[] args) {

        int[] array = {8, 1, 2, 3, 3, 2, 3, 7};
        System.out.println(minValue(array));

        List<Integer> list = List.of(2, 1, 1);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (x, y) -> (x * 10) + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(a -> a).sum();
        return integers.stream().filter(e -> sum % 2 == 0 ? e % 2 == 1 : e % 2 == 0).collect(Collectors.toList());
    }
}
