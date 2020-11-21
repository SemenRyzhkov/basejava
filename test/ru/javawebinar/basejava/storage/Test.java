package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        String s = String.join("\n", list);
        String[] result = s.split("\n");
        System.out.println(result.length);
        List<String> list1 = new ArrayList<>();
        if (result.length != 0) {
            for (String ss : result) {
                if (ss != null && s.trim().length() != 0) {
                    list1.add(s);
                }
            }
            System.out.println(list1.size());
        } else {
            System.out.println(list1);
        }
    }
}
