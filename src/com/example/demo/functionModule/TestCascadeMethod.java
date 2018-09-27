package com.example.demo.functionModule;

import java.util.function.Function;

/**
 * 测试级联表达式 柯里化目的是为了函数标准化
 */
public class TestCascadeMethod {

    public static void main(String[] args) {
        //实现 x + y
        Function<Integer,Function<Integer,Integer>> function  = x->y->x+y;
        //柯里化的实现
        Integer res = function.apply(1).apply(2);
        System.out.println("结果为"+res);
    }
}
