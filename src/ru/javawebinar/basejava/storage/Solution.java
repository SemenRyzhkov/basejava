package ru.javawebinar.basejava.storage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        System.out.println(calculate(-4, "*", 0));

    }
//    Для целого числа k расположите все элементы данного массива таким образом, чтобы: все элементы, меньшие k,
//    располагались перед элементами, которые не меньше k; все элементы, которые меньше k, остаются в том же порядке относительно друг друга;
//    все элементы, которые не меньше k, остаются в одном порядке относительно друг друга.

    int[] splitByValue(int k, int[] elements) {
        int[] arr = IntStream.concat(Arrays.stream(elements).filter(e -> e < k),
                Arrays.stream(elements).filter(e -> e >= k)).toArray();
        return arr;
    }


//          В криптоанализе шаблоны слов могут быть полезным инструментом для взлома простых шифров.
//
//        Шаблон слова - это описание сочетания букв, встречающихся в слове, где каждой букве дается целочисленный код в порядке появления.
//        Таким образом, первой букве присваивается код 0, а второй - 1, если она отличается от первой буквы, или 0 в противном случае,
//        и так далее.
//
//        Например, слово «привет» будет преобразовано в «0.1.2.2.3». В этой задаче игнорируется чувствительность к регистру,
//        поэтому «hello», «helLo» и «heLlo» будут возвращать один и тот же образец слова.
//
//        Ваша задача - вернуть образец слова для данного слова. Все предоставленные слова будут непустыми строками только из буквенных символов,
//        то есть совпадающими с регулярным выражением «[a-zA-Z] +».

    public static String wordPattern(final String word) {
        Map<Integer, Integer> map = new HashMap<>();
        String[] arr = word.toLowerCase().chars()
                .mapToObj(key -> "" + map.computeIfAbsent(key, value -> map.size()))
                .toArray(String[]::new);
        return String.join(".", arr);
    }

//    В этом Ката мы собираемся перевернуть строку, сохраняя пробелы (если они есть) на их исходных местах.
//
//    Например:
//
//    решить ("our code") = "edo cruo"
//            - Обычный разворот без пробелов - "edocruo".
//            - Однако в индексе 3 есть пробел, поэтому строка становится "edo cruo".
//
//    решить ("your code rocks") = "skco redo cruoy".
//    решить ("codewars") = "srawedoc"

    public static String solve(String s) {
        StringBuilder str = new StringBuilder(s.replaceAll(" ", "")).reverse();
        IntStream.range(0, s.length())//создаем IntStream ,равный от 0 до длины строки(не вкл) с шагом 1
                .filter(i -> s.charAt(i) == ' ')//для каждого числа в диапазоне находим символ в строке с индексом, равным этому числу
                //если этот символ равен ' ',мы оставляем число в диапазоне.
                .forEach(j -> str.insert(j, ' '));//для каждого оставшегося числа мы делаем операцию str.insert(j, ' '),
        //тем самым добавляет символ ' ' перед индексом j StringBuilder
        return str.toString();
    }


    //Поменять в строке строчные буквы на прописные и наоборот

    static String alternateCase(final String string) {
//        String[] arr = string.split("");
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i].matches("[A-Z]")) {
//                arr[i] = arr[i].toLowerCase();
//            } else if (arr[i].matches("[a-z]")) {
//                arr[i] = arr[i].toUpperCase();
//            }
//        }
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < arr.length; i++) {
//            sb.append(arr[i]);
//        }
//        return sb.toString();


        return string.chars()
                .mapToObj(i -> (char) i)
                .map(i -> Character.isUpperCase(i) ? Character.toLowerCase(i) : Character.toUpperCase(i))
                .map(i -> i.toString())
                .collect(Collectors.joining(""));

    }

//    Ваша команда пишет новый модный текстовый редактор, и вам было поручено реализовать нумерацию строк.
//
//    Напишите функцию, которая принимает список строк и возвращает каждую строку с правильным номером.

    public static List<String> number(List<String> lines) {
//        for (int i = 0; i < lines.size(); i++) {
//            String result = String.format("%d: %s", i + 1, lines.get(i));
//            lines.set(i, result);
//        }
//        return lines;


        if (lines.size() == 0) return lines;
        return IntStream.range(0, lines.size())
                .mapToObj(n -> String.format("%d: %s", n + 1, lines.get(n)))
                .collect(Collectors.toList());
    }


    public static Double calculate(final double numberOne, final String operation, final double numberTwo) {
        double result = 0;

        if (!operation.matches("[-+*/]")) {
            return null;
        } else if (operation.matches("[*]")) {
            if (numberOne == 0 || numberTwo == 0) {
                return 0d;
            } else
                result = numberOne * numberTwo;
        } else if (operation.matches("[-]")) {
            result = numberOne - numberTwo;
        } else if (operation.matches("[+]")) {

            result = numberOne + numberTwo;
        } else if (operation.matches("[/]")) {
            if (numberTwo == 0) {
                return null;
            } else
                result = numberOne / numberTwo;
        }
        return result;

        // Show me the code!
    }

}

