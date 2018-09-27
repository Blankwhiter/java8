package com.example.demo.interfaceModule;

/**
 * 测试默认方法
 */
public class TestDefaultMethod implements DefaultInterface {

    /**
     * 调用DefaultInterface的默认方法
     */
    public  void useDefaultInterface(){
        System.out.println("加法结果  ： "+add(1, 2));
    }

    public  static void main(String[] args) {
        TestDefaultMethod main = new TestDefaultMethod();
        main.useDefaultInterface();
    }
}
