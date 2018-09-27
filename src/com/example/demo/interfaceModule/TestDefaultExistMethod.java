package com.example.demo.interfaceModule;

/**
 * 测试当类中 同时继承了一个类已有了接口实现同样的方法的问题
 */
public class TestDefaultExistMethod extends DefaultExist implements DefaultInterface,DefaultOverwriteInterface {

    /**
     * 方法运行的结果：继承 实现 加法结果 ： 203
     * @param args
     */
    public static void main(String[] args) {
        TestDefaultExistMethod testDefaultExistMethod = new TestDefaultExistMethod();
        System.out.println("继承 实现 加法结果 ： "+testDefaultExistMethod.add(1, 2));
    }

}
