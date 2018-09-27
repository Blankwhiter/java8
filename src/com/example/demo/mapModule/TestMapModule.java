package com.example.demo.mapModule;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试map操作
 */
public class TestMapModule {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        //这里需要提一提的是map的put与putIfAbsent的区别
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("1","123");
        System.out.println("put方法覆盖前 "+map1.get("1"));
        map1.put("1","456");
        System.out.println("put方法覆盖后 "+map1.get("1"));
        HashMap<String, String> map2 = new HashMap<>();
        map2.putIfAbsent("1","123");
        System.out.println("putIfAbsent方法覆盖前 "+map2.get("1"));
        map2.putIfAbsent("1","456");
        System.out.println("putIfAbsent方法覆盖后 "+map2.get("1"));
        /**
         * 结果为
         * put方法覆盖前 123
         * put方法覆盖后 456
         * putIfAbsent方法覆盖前 123
         * putIfAbsent方法覆盖后 123
         * 从上面结果可以看出put方法当已有key对应的值的时候 仍会直接覆盖。putIfAbsent则并不会覆盖。
         */

        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

        map.remove(3, "val3");
        map.get(3);             // val33

        map.remove(3, "val33");
        map.get(3);

        map.getOrDefault(42, "not found");  // not found

        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat

    }
}
