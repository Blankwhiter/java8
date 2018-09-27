package com.example.demo.streamModule;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * 测试stream常用操作
 */
public class TestStreamMethod {

    public static void main(String[] args) {
        //collect(toList())
        List<String> collected = Stream.of("a", "b", "c")
                .collect(toList());
        assertEquals(asList("a", "b", "c"), collected);

        //map
        List<String> collected1 = Stream.of("a", "b", "hello")
                .map(string -> string.toUpperCase())
                .collect(toList());
        assertEquals(asList("A", "B", "HELLO"), collected1);

        //filter
        List<String> beginningWithNumbers =
                Stream.of("a", "1abc", "abc1")
                        .filter(value -> isDigit(value.charAt(0)))
                        .collect(toList());
        assertEquals(asList("1abc"), beginningWithNumbers);

        //flatMap
        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream())
                .collect(toList());
        assertEquals(asList(1, 2, 3, 4), together);

        // max  min
        List<Integer> list = asList(3, 5, 2, 9, 1);
        int maxInt = list.stream()
                .max(Integer::compareTo)
                .get();
        int minInt = list.stream()
                .min(Integer::compareTo)
                .get();
        assertEquals(maxInt, 9);
        assertEquals(minInt, 1);

        //reduce 加法
        int result = Stream.of(1, 2, 3, 4)
                .reduce(0, (acc, element) -> acc + element);
        assertEquals(10, result);
        //reduce 乘法
        int result1 = Stream.of(1, 2, 3, 4)
                .reduce(1, (acc, element) -> acc * element);
        assertEquals(24, result1);

        //parallel
        int sumSize = Stream.of("Apple", "Banana", "Orange", "Pear")
                .parallel()
                .map(s -> s.length())
                .reduce(Integer::sum)
                .get();
        assertEquals(sumSize, 21);


    }


}
