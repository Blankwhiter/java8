package com.example.demo.interfaceModule;

/**
 * 测试java8接口新特性（静态方法）
 */
public interface StaticInterface {

    /**
     * 减法
     * @param a
     * @param b
     * @return
     */
     static int  reduce(int a,int b){
         return a - b;
     }
}
