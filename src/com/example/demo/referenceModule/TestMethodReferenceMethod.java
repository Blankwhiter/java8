package com.example.demo.referenceModule;

import java.util.function.*;

/**
 * 测试方法引用
 */
public class TestMethodReferenceMethod {

    public static void main(String[] args) {
        //静态方法的方法引用
        Consumer<Product> consumer = Product::showProductName;
        Product product = new Product();
        consumer.accept(product);

        //非静态方法的方法引用
        Function<Integer,Integer> function = product::sales;
        //==》Function<Integer,Integer> function = product::sales;
        // 由于输入 输出参数一致 故等同于 1.UnaryOperator<Integer> unaryOperator = product::sales;
        //                             2. IntUnaryOperator intUnaryOperator = product::sales;
        //                             3.也等用于 下方的使用类名来方法引用
        Integer surplus = function.apply(10);
        System.out.println("剩余数量是"+surplus);

        //使用类名来方法引用
        BiFunction<Product,Integer,Integer> biFunction = Product::sales;
        Integer surplus2 = biFunction.apply(product, 20);
        System.out.println("剩余数量是"+surplus2);

        //构造函数的方法引用
        Supplier<Product> supplier= Product::new;
        Product product1 = supplier.get();
        System.out.println("创建了"+product1);

        //带有参数的构造函数的方法引用
        Function<String,Product> function1 = Product::new;
        Product product2 = function1.apply("冰箱");
        System.out.println("再次创建了"+product2);
    }
}
