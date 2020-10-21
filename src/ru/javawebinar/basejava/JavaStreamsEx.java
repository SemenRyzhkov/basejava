package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStreamsEx {
    public static void main(String[] args) {
//        int[] array = {9,8};
//        System.out.println(minValue(array));

        List<Integer>list = List.of(2, 1);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((x, y) -> Integer.parseInt(x + "" + y)).getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers){
        return integers.stream().filter(e->{
            if (integers.stream().mapToInt(a->a).sum()%2==0){
               return e%2==1;
            }else return e%2==0;
        }).collect(Collectors.toList());

    }
}
