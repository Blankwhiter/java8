package com.example.demo.interfaceModule;

/**
 * 测试静态方法接口
 */
public class TestStaticMethod implements StaticInterface {

    /**
     * 调用了StaticInterface接口中的reduce静态方法
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("减法结果 ： "+StaticInterface.reduce(4, 2));
    }
}
