写在前面：本文讲简易讲解java8中的接口新特性、lambda表达式、函数接口、方法引用、streamAPI、数据并行化操作、级联表达式和柯里化、Map的操作、时间日期API
测试所用到的代码地址：[https://github.com/Blankwhiter/java8](https://github.com/Blankwhiter/java8)
本文参考引用 [1]: https://www.cnblogs.com/snowInPluto/p/5981400.html 
            [2]:    http://www.importnew.com/10360.html
 
# 一、接口新特性
1.**默认方法**：java8中可以使用**default**关键字，为接口添加非抽象的方法实现。在实现拥有default方法的接口时，可以直接使用该默认方法。示例如下：
带有默认方法的接口：
```java
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

```
测试默认方法接口：
```java
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


```

在此处我们还需要多考虑2个问题：
>1.多重继承的冲突问题
当一个方法从多个接口同时引入的时候，到底会使用哪个接口的问题？ 示例如下：
编写重写DefaultInterface的add方法的接口
```java
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
```
测试多继承接口

```java
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

```
从结果来看 它遵从了优先选取最具体的实现原则
>2.当类中 同时继承了一个类已有了接口实现同样的方法的问题
编写具有相同方法的父类：
```java
public class DefaultExist {
    /**
     * 默认加200来区分其他的add方法
     * @param a
     * @param b
     * @return
     */
    public int add(int a,int b){
        return a + b + 200;
    }
}

```
测试子类实现同个方法：
```java

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

```
从结果看 遵从类优先的原则：在继承一个类的同时实现了另外一个接口的，而接口和该类中有相同的实现方法的时候，会以类的方法作为最优先的。

当然如果你想避免这样的问题，可以在接口上方

2.**静态方法**：Java8中可以在接口中声明静态方法，并加以实现。示例如下：
带有静态实现方法的接口：
```java
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

```
测试静态方法的接口：
```java
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
```
3.**@FunctionalInterface**注解
Java 8为函数式接口引入了一个新注解@FunctionalInterface，主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。那什么是函数式接口？所谓的函数式接口，当然首先是一个接口，然后就是在这个接口里面只能有一个抽象方法。符合了单一职责原则。还帮助解决了默认上述的覆盖问题。
待用FunctionalInterface注解的接口：
```java
@FunctionalInterface
public interface FuncInterface {
    void test(String txt);
}
```
测试使用带注解的接口应用于lambda表达式：
```java
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

```

# 二、lambda表达式
“lambda表达式”是一段可以传递的代码，因为他可以被执行一次或多次。
lambda常见的几种形式：
```java
// 1. 不需要参数,返回值为 5  
() -> 5  
  
// 2. 接收一个参数(数字类型),返回其2倍的值  
x -> 2 * x  
  
// 3. 接受2个参数(数字),并返回他们的差值  
(x, y) -> x – y  
  
// 4. 接收2个int型整数,返回他们的和  
(int x, int y) -> x + y  
  
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)  
(String s) -> System.out.print(s)
```
这里需要多提一点的是：对于lambdab表达式外部的变量，其访问权限的粒度与匿名对象的方式非常类似。你能够访问局部对应的外部区域的局部final变量，以及成员变量和静态变量。

# 三、函数接口
函数接口 可以理解成一段行为的抽象，即一段行为作为参数进行传递。接下来主要介绍Predicate、 Consumer、 Function、 Supplier 、UnaryOperator、 BinaryOperator 基础函数接口：
<table>
<thead>
<tr class="header">
<th style="text-align: left">接口</th>
<th style="text-align: left">参数</th>
<th style="text-align: left">返回类型</th>
<th style="text-align: left">描述</th>
<th style="text-align: left">功能</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td style="text-align: left">Predicate&lt;T&gt;</td>
<td style="text-align: left">T</td>
<td style="text-align: left">boolean</td>
<td style="text-align: left">用于判别一个对象。比如求一个人是否为男性</td>
<td style="text-align: left">用于判断对象是否符合某个条件，经常被用来过滤对象。</td>
</tr>
<tr class="even">
<td style="text-align: left">Consumer&lt;T&gt;</td>
<td style="text-align: left">T</td>
<td style="text-align: left">void</td>
<td style="text-align: left">用于接收一个对象进行处理但没有返回，比如接收一个人并打印他的名字</td>
<td style="text-align: left">消费者</td>
</tr>
<tr class="odd">
<td style="text-align: left">Function&lt;T, R&gt;</td>
<td style="text-align: left">T</td>
<td style="text-align: left">R</td>
<td style="text-align: left">转换一个对象为不同类型的对象</td>
<td style="text-align: left">将一个对象转换为另一个对象，比如说要装箱或者拆箱某个对象。</td>
</tr>
<tr class="even">
<td style="text-align: left">Supplier&lt;T&gt;</td>
<td style="text-align: left">None</td>
<td style="text-align: left">T</td>
<td style="text-align: left">提供一个对象</td>
<td style="text-align: left">提供者</td>
</tr>
<tr class="odd">
<td style="text-align: left">UnaryOperator&lt;T&gt;</td>
<td style="text-align: left">T</td>
<td style="text-align: left">T</td>
<td style="text-align: left">接收对象并返回同类型的对象</td>
<td style="text-align: left">接收和返回同类型对象，一般用于对对象修改属性</td>
</tr>
<tr class="even">
<td style="text-align: left">BinaryOperator&lt;T&gt;</td>
<td style="text-align: left">(T, T)</td>
<td style="text-align: left">T</td>
<td style="text-align: left">接收两个同类型的对象，并返回一个原类型对象</td>
<td style="text-align: left">可以理解为合并对象</td>
</tr>
</tbody>
</table>

测试示例如下：
```java

/**
 * 测试函数接口
 */
public class TestFucntionInterfaceMethod {

    public static void main(String[] args) {
        //断言函数接口   编写一个判断数字是否大于5的函数接口。有输入参数，返回Boolean
        Predicate<Integer> predicate = integer -> integer>5;
        boolean result = predicate.test(10);
        System.out.println("判断结果是"+result);

        //消费者函数接口  编写一个拼接字符串的的函数接口。只有输入参数，无返回类型
        Consumer<String> consumer = str-> System.out.println("传入的字符串是"+str);
        consumer.accept("running");

        //方法函数接口  编写一个将字符串转化成数字的函数接口。有输入参数，返回第二个参数数据类型
        Function<String,Integer> function = x->Integer.parseInt(x);
        Integer data = function.apply("10");
        System.out.println("返回的结果是"+data);

        //提供者函数接口 编写一个提供字符串的函数接口。没有输入参数，返回参数类型
        Supplier<String> supplier = ()-> "这里提供了一串字符串";
        System.out.println("提供者接口得到的结果是"+supplier.get());
        //一元函数接口 编写一个当前输入数字加1的函数接口。有输入参数，返回数据，输入参数与输出参数类型一致
        UnaryOperator<Integer> unaryOperator = x-> x + 1;
        Integer addResult = unaryOperator.apply(1);
        System.out.println("一元函数接口得到的结果是"+addResult);

        //二元函数接口 编写一个两个数加法的函数接口。有两个输入参数，一个返回数据，输入参数与输出参数类型一致
        BinaryOperator<Integer> binaryOperator = (x,y)->x+y;
        Integer applyResult = binaryOperator.apply(1, 2);
        System.out.println("二元函数接口得到的结果是"+applyResult);
    }
}

```

当然除了上面所描述的几个常见的接口函数，java8中还包含其他许多函数接口，读者可以根据需要查询API进行实验。


# 四、方法引用
方法引用通过方法的名字来指向一个方法,是lambda表达式的一种简写形式。下个表格列举出常见的方法引用形式：
<table width="100%">
<thead>
<tr class="header">
<th>类型</th>
<th>示例</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>引用静态方法</td>
<td>ContainingClass::staticMethodName</td>
</tr>
<tr class="even">
<td>引用某个对象的实例方法</td>
<td>containingObject::instanceMethodName</td>
</tr>
<tr class="odd">
<td>引用某个类型的任意对象的实例方法</td>
<td>ContainingType::methodName</td>
</tr>
<tr class="even">
<td>引用构造方法</td>
<td>ClassName::new</td>
</tr>
</tbody>
</table>

示例代码如下：
商品类实体：
```java
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

```
测试方法引用：
```java

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

```


# 五、streamAPI
java8中对**Collection**做了相当大的优化。这便是引入了stream。它遵循“做什么，而不是怎么去做”的原则。只需要描述需要做什么，而不用考虑程序是怎样实现的。这样大大方便了我们的开发过程。

**Stream** 的方法分为两类。一类叫惰性求值，一类叫及早求值。
判断一个操作是惰性求值还是及早求值很简单：只需看它的返回值。如果返回值是 Stream，那么是惰性求值。其实可以这么理解，如果调用惰性求值方法，Stream 只是记录下了这个惰性求值方法的过程，并没有去计算，等到调用及早求值方法后，就连同前面的一系列惰性求值方法顺序进行计算，返回结果。

通用形式为：
```xml
Stream.惰性求值.惰性求值. ... .惰性求值.及早求值
```
整个过程和建造者模式有共通之处。建造者模式使用一系列操作设置属性和配置，最后调 用一个 build 方法，这时，对象才被真正创建。
接下来介绍常用操作：
### 1.**collect(toList())**
**collect(toList()) **方法由 Stream 里的值生成一个列表，是一个及早求值操作。可以理解为 **Stream** 向 **Collection** 的转换。
注意这边的 **toList()** 其实是** Collectors.toList()**，因为采用了静态倒入，看起来显得简洁。
```java
List<String> collected = Stream.of("a", "b", "c")
                               .collect(Collectors.toList());
assertEquals(Arrays.asList("a", "b", "c"), collected);
```

### 2.**map**
如果有一个函数可以将一种类型的值转换成另外一种类型，map 操作就可以使用该函数，将一个流中的值转换成一个新的流。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328143810130.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbG9uZ2h1YW5nMTU3NDA1,size_16,color_FFFFFF,t_70)
```java
List<String> collected = Stream.of("a", "b", "hello")
                               .map(string -> string.toUpperCase())
                               .collect(toList());
assertEquals(asList("A", "B", "HELLO"), collected);
```
map 方法就是接受的一个 Function 的匿名函数类，进行的转换。
[外链图片转存失败(img-87S6Mjti-1567387376314)(https://i.imgur.com/Nw0WlnP.png)]

### 3.**filter**
遍历数据并检查其中的元素时，可尝试使用 **Stream** 中提供的新方法 **filter**。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328143826886.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbG9uZ2h1YW5nMTU3NDA1,size_16,color_FFFFFF,t_70)
```java
List<String> beginningWithNumbers = 
        Stream.of("a", "1abc", "abc1")
              .filter(value -> isDigit(value.charAt(0)))
              .collect(toList());
assertEquals(asList("1abc"), beginningWithNumbers);
```
**filter** 方法就是接受的一个 **Predicate** 的匿名函数类，判断对象是否符合条件，符合条件的才保留下来。
[外链图片转存失败(img-6o09MMmN-1567387376315)(https://i.imgur.com/CMg7x8F.png)]

### 4.flatMap
**flatMap** 方法可用 **Stream** 替换值，然后将多个 **Stream** 连接成一个 **Stream**。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328143902652.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbG9uZ2h1YW5nMTU3NDA1,size_16,color_FFFFFF,t_70)

```java
List<Integer> together = Stream.of(asList(1, 2), asList(3, 4))
                               .flatMap(numbers -> numbers.stream())
                               .collect(toList());
assertEquals(asList(1, 2, 3, 4), together);
```
**flatMap** 最常用的操作就是合并多个 **Collection**。

### 5.max和min
Stream 上常用的操作之一是求最大值和最小值。Stream API 中的 **max** 和 **min** 操作足以解决这一问题。
```java
List<Integer> list = Lists.newArrayList(3, 5, 2, 9, 1);
int maxInt = list.stream()
                 .max(Integer::compareTo)
                 .get();
int minInt = list.stream()
                 .min(Integer::compareTo)
                 .get();
assertEquals(maxInt, 9);
assertEquals(minInt, 1);
```
这里有 2 个要点需要注意：
<pre>
   1.max 和 min 方法返回的是一个 Optional 对象（对了，和 Google Guava 里的 Optional 对象是一样的）。Optional 对象封装的就是实际的值，可能为空，所以保险起见，可以先用 isPresent() 方法判断一下。Optional 的引入就是为了解决方法返回 null 的问题。
   2.Integer::compareTo 也是属于 Java 8 引入的新特性，叫做 方法引用（Method References）。在这边，其实就是 (int1, int2) -> int1.compareTo(int2) 的简写，可以自己查阅了解，这里不再多做赘述。
</pre>
### 6.reduce
reduce 操作可以实现从一组值中生成一个值。在上述例子中用到的 **count**、**min** 和 **max** 方法,因为常用而被纳入标准库中。事实上，这些方法都是 reduce 操作。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328143919164.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbG9uZ2h1YW5nMTU3NDA1,size_16,color_FFFFFF,t_70)


上图展示了 reduce 进行累加的一个过程。具体的代码如下：
```java
int result = Stream.of(1, 2, 3, 4).reduce(0, (acc, element) -> acc + element);
assertEquals(10, result);
```
注意 reduce 的第一个参数，这是一个初始值。0 + 1 + 2 + 3 + 4 = 10。

如果是累乘，则为：
```java
int result = Stream.of(1, 2, 3, 4)
                   .reduce(1, (acc, element) -> acc * element);
assertEquals(24, result);
```
因为任何数乘以 1 都为其自身嘛。1 * 1 * 2 * 3 * 4 = 24。
Stream 的方法还有很多，这里列出的几种都是比较常用的。Stream 还有很多通用方法，具体可以查阅 Java 8 的 API 文档。
https://docs.oracle.com/javase/8/docs/api/

### 7.数据并行化操作
Stream 的并行化也是 Java 8 的一大亮点。数据并行化是指将数据分成块，为每块数据分配单独的处理单元。这样可以充分利用多核 CPU 的优势。

并行化操作流只需改变一个方法调用。如果已经有一个 Stream 对象，调用它的 parallel() 方法就能让其拥有并行操作的能力。如果想从一个集合类创建一个流，调用 **parallelStream**() 就能立即获得一个拥有并行能力的流。
```java
int sumSize = Stream.of("Apple", "Banana", "Orange", "Pear")
                    .parallel()
                    .map(s -> s.length())
                    .reduce(Integer::sum)
                    .get();
assertEquals(sumSize, 21);
```
这里求的是一个字符串列表中各个字符串长度总和。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190328143939852.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbG9uZ2h1YW5nMTU3NDA1,size_16,color_FFFFFF,t_70)

如果你去计算这段代码所花的时间，很可能比不加上 parallel() 方法花的时间更长。这是因为数据并行化会先对数据进行分块，然后对每块数据开辟线程进行运算，这些地方会花费额外的时间。并行化操作只有在 数据规模比较大 或者 数据的处理时间比较长 的时候才能体现出有事，所以并不是每个地方都需要让数据并行化，应该具体问题具体分析。

这里多提有点的就是并行流使用的线程池是ForkJoinPool.ommonPool，默认的线程数是当前机器的cpu个数，如果读者想修改默认的线程数使用**System.serProperty("java.util.concurrent.ForkJoinPool.parallelism","线程数");**。示例如下：
```java
System.serProperty("java.util.concurrent.ForkJoinPool.parallelism","10");
int sumSize = Stream.of("Apple", "Banana", "Orange", "Pear")
                    .parallel()
                    .map(s -> s.length())
                    .reduce(Integer::sum)
                    .get();
assertEquals(sumSize, 21);
```

# 六、级联表达式和柯里化
柯里化（Currying）是把接受多个参数的函数变换成接受一个单一参数(最初函数的第一个参数)的函数，并且返回接受余下的参数且返回结果的新函数的技术。
示例如下：
```java
/**
 * 测试级联表达式 柯里化目的是为了函数标准化
 */
public class TestCascadeMethod {

    public static void main(String[] args) {
        //实现 x + y
        Function<Integer,Function<Integer,Integer>> function  = x->y->x+y;
        //柯里化的实现
        Integer res = function.apply(1).apply(2);
        System.out.println("结果为"+res);
    }
}

```

# 七、Map的操作
在java8中对map提供了多种使用的方法。
```java
import java.util.HashMap;
import java.util.Map;

/**
 * 测试map操作
 */
public class TestMapModule {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        //这里需要提一提的是map的put与putIfAbsent的区别
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("1","123");
        System.out.println("put方法覆盖前 "+map1.get("1"));
        map1.put("1","456");
        System.out.println("put方法覆盖后 "+map1.get("1"));
        HashMap<String, String> map2 = new HashMap<>();
        map2.putIfAbsent("1","123");
        System.out.println("putIfAbsent方法覆盖前 "+map2.get("1"));
        map2.putIfAbsent("1","456");
        System.out.println("putIfAbsent方法覆盖后 "+map2.get("1"));
        /**
         * 结果为
         * put方法覆盖前 123
         * put方法覆盖后 456
         * putIfAbsent方法覆盖前 123
         * putIfAbsent方法覆盖后 123
         * 从上面结果可以看出put方法当已有key对应的值的时候 仍会直接覆盖。putIfAbsent则并不会覆盖。
         */

        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

        map.remove(3, "val3");
        map.get(3);             // val33

        map.remove(3, "val33");
        map.get(3);

        map.getOrDefault(42, "not found");  // not found

        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat

    }
}

```

# 八、时间日期API
Java 8 包含了全新的时间日期API，这些功能都放在了java.time包下。新的时间日期API是基于Joda-Time库开发的，但是也不尽相同。下面的例子就涵盖了大多数新的API的重要部分。
```java
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 测试时间日期API
 */
public class TestTimeMethod {

    public static void main(String[] args) {
        /**
         * Clock提供了对当前时间和日期的访问功能。
         * Clock是对当前时区敏感的，并可用于替代System.currentTimeMillis()方法来获取当前的毫秒时间。
         * 当前时间线上的时刻可以用Instance类来表示。Instance也能够用于创建原先的java.util.Date对象。
         */
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println(millis); //1538027081813

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate); //Thu Sep 27 13:44:41 CST 2018


        /**
         * Timezones 时区类可以用一个ZoneId来表示。时区类的对象可以通过静态工厂方法方便地获取。
         * 时区类还定义了一个偏移量，用来在当前时刻或某时间与目标时区时间之间进行转换。
         */
        System.out.println(ZoneId.getAvailableZoneIds());
        // prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

        // ZoneRules[currentStandardOffset=+01:00]
        // ZoneRules[currentStandardOffset=-03:00]

        /**
         * LocalTime 本地时间类表示一个没有指定时区的时间，例如，10 p.m.或者17：30:15，
         * 下面的例子会用上面的例子定义的时区创建两个本地时间对象。
         * 然后我们会比较两个时间，并计算它们之间的小时和分钟的不同。
         */
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239


        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println(yesterday); //2018-09-26
        
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek); //FRIDAY


        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek1 = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek1);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439


    }
}

```



## 附录：
1.stream流编程-创建
<table width="100%">
	<tr><td></td><td>相关方法</td></tr>
	<tr><td>集合</td><td>Colletion.stream/parallelStream</td></tr>
	<tr><td>数组</td><td>Arrays.stream</td></tr>
	<tr><td>数字stream</td><td>1.IntStream/LongStream.reange/rangeClosed 2.Random.ints/longs/duobles</td></tr>
    <tr><td>自己创建</td><td>Stream.generate/iterate</td></tr>
</table>	
2.streaml流编程-中间操作
<table width="100%">
	<tr><td></td><td>相关方法</td></tr>
    <tr><td rowspan="6" style="text-align: center">无状态操作</td><td>map/mapToXXX</td></tr>
    <tr> <td>map/mapToXXX</td></tr>
    <tr> <td>flatMap/flatMapToXXX</td></tr>
    <tr> <td>filter</td></tr>
    <tr> <td>peek</td></tr>
    <tr> <td>unordered</td></tr>
    <tr><td rowspan="3" style="text-align: center">有状态操作</td><td>distinct</td></tr>
    <tr> <td>sorted</td></tr>
    <tr> <td>limit/skip</td></tr>
</table>	
3.streaml流编程-终止操作

<table width="100%">
	<tr><td></td><td>相关方法</td></tr>
    <tr><td rowspan="4"  style="text-align: center">非短路操作</td><td>forEach/forEachOrdered</td></tr>
    <tr> <td>collect/toArray</td></tr>
    <tr> <td>reduce</td></tr>
    <tr> <td>min/max/count</td></tr>
    <tr><td rowspan="2"  style="text-align: center">短路操作</td><td>findFirst/findAny</td></tr>
    <tr> <td>allMatch/anyMatch/noneMatch</td></tr>
    
</table>	
