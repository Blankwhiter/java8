package com.example.demo.interfaceModule;

/**
 * 测试java8接口新特性（默认方法/扩展方法）
 */
public interface DefaultInterface {

    /**
     * 加法
     * @param a
     * @param b
     * @return
     */
    default int add(int a,int b){
        return a+b;
    }

}

