package com.example.demo.referenceModule;

/**
 * 商品类
 */
public class Product {
    /**
     * 商品名称
     */
    private String name = "笔记本";

    /**
     * 商品数量 默认100
     */
    private int num = 100;



    public Product(){}

    public Product(String name){
        this.name = name;
    }

    /**
     * 展示商品名称
     * @param product
     */
    public static void showProductName(Product product){
        System.out.println("商品名称："+product);
    }

    /**
     * 销售商品 返回剩余.说明非静态方法中，默认第一个不可见参数是类本身的实例. public int sales(int num)==>public int sales(Product this,int num)
     * @param num
     * @return
     */
    public int sales(int num){
        this.num -= num;
        return  this.num;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
