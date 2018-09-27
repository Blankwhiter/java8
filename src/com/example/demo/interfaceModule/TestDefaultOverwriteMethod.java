package com.example.demo.interfaceModule;

/**
 * 测试一个方法在多个接口同时引入问题
 */
public class TestDefaultOverwriteMethod implements DefaultInterface,DefaultOverwriteInterface {

    /**
     * 方法运行的结果：  重写 加法结果 ： 103
     * @param args
     */
    public static void main(String[] args) {
        TestDefaultOverwriteMethod testDefaultOverwriteMethod = new TestDefaultOverwriteMethod();
        System.out.println("重写 加法结果 ： "+testDefaultOverwriteMethod.add(1, 2));
    }
}
