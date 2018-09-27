package com.example.demo.interfaceModule;

/**
 *测试FunctionalInterface在lambda表达式的应用
 */
public class TestFuncInterfaceMethod {

    public static void main(String[] args) {
        //System.out::println   等同于 str->System.out.println(str);
        FuncInterface fun = System.out::println;
        fun.test("测试成功");
    }

}
