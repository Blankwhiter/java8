package com.example.demo.functionModule;

import java.util.function.*;

/**
 * 测试函数接口
 */
public class TestFucntionInterfaceMethod {

    public static void main(String[] args) {
        //断言函数接口   编写一个判断数字是否大于5的函数接口。有输入参数，返回Boolean
        Predicate<Integer> predicate = integer -> integer>5;
        boolean result = predicate.test(10);
        System.out.println("判断结果是"+result);

        //消费者函数接口  编写一个拼接字符串的的函数接口。只有输入参数，无返回类型
        Consumer<String> consumer = str-> System.out.println("传入的字符串是"+str);
        consumer.accept("running");

        //方法函数接口  编写一个将字符串转化成数字的函数接口。有输入参数，返回第二个参数数据类型
        Function<String,Integer> function = x->Integer.parseInt(x);
        Integer data = function.apply("10");
        System.out.println("返回的结果是"+data);

        //提供者函数接口 编写一个提供字符串的函数接口。没有输入参数，返回参数类型
        Supplier<String> supplier = ()-> "这里提供了一串字符串";
        System.out.println("提供者接口得到的结果是"+supplier.get());
        //一元函数接口 编写一个当前输入数字加1的函数接口。有输入参数，返回数据，输入参数与输出参数类型一致
        UnaryOperator<Integer> unaryOperator = x-> x + 1;
        Integer addResult = unaryOperator.apply(1);
        System.out.println("一元函数接口得到的结果是"+addResult);

        //二元函数接口 编写一个两个数加法的函数接口。有两个输入参数，一个返回数据，输入参数与输出参数类型一致
        BinaryOperator<Integer> binaryOperator = (x,y)->x+y;
        Integer applyResult = binaryOperator.apply(1, 2);
        System.out.println("二元函数接口得到的结果是"+applyResult);

    }
}
