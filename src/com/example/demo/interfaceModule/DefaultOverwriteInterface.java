package com.example.demo.interfaceModule;

/**
 * 测试多重继承的冲突问题
 */

public interface DefaultOverwriteInterface extends DefaultInterface {
    /**
     * 默认多加100来区分DefaultInterface的add
     * @param a
     * @param b
     * @return
     */
    @Override
    default int add(int a, int b){
        return a + b+100;
    }
}
