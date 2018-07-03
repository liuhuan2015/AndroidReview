#### 1 . 为什么Java里的匿名内部类只能访问final修饰的外部变量？
这个可以见工程测试代码(class AnonymousInnerClassTest)。<br>

 匿名内部类对外部变量的使用：如果是使用的外部的类的成员变量，该成员变量不需要声明为final；<br>
 如果匿名内部类是使用的当前所在方法内定义的变量，则该变量需要声明为final类型.<br>
 解释：<br>
 在编译后形成的class文件中，匿名内部类最终用会编译成一个单独的类，而被该类使用的变量会以构造函数参数的形式传递给该类，<br>
 例如number2,如果变量number2不定义成final的，number2在匿名内部类中可以被修改，进而造成和外部的number2不一致的问题<br>

#### 2 . 静态代理和动态代理的区别，分别用在什么场景里面？
 静态代理与动态代理的区别在于代理类生成的时间不同，如果需要对多个类进行代理，并且代理的功能都是一样的，用静态代理重复编写代理类就非常的麻烦，可以用动态代理动态的生成代理类
 ```java
 // 为目标对象生成代理对象
 public Object getProxyInstance() {
     return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
             new InvocationHandler() {
 
                 @Override
                 public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                     System.out.println("开启事务");
 
                     // 执行目标对象方法
                     Object returnValue = method.invoke(target, args);
 
                     System.out.println("提交事务");
                     return null;
                 }
             });
 }
 ```
动态代理是一个很重要的知识点，很多的开源框架都是用了动态代理，比如Retrofit等，它有两个需要重点关注的类：Proxy和InvocationHandler。<br>
动态代理模式,可以简单理解为JVM可以在运行时帮我们动态生成一系列的代理类,这样我们就不需要手动写每一个静态的代理类了<br>

 Java动态代理位于java.lang.reflect包下，一般主要涉及到以下两个类：<br>
 （1）Interface InvocationHandler<br>
 该接口中仅定义了一个方法：public Object invoke(Object obj, Method method, Object[] args)，<br>
 在使用时，第一个参数obj一般是指被代理的对象，method是被代理的方法，args为该方法的参数数组。<br>
 这个抽象方法在代理类中动态实现。<br>
 （2）Proxy<br>
 该类即为动态代理类，<br>
 static Object newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)，<br>
 返回代理类的一个实例，返回后的代理类可以当作被代理类使用<br>
 loader 类加载器<br>
 interfaces 实现接口<br>
 <p>
 JDK动态代理的一般实现步骤如下：<br>
（1）创建一个实现InvocationHandler接口的类，它必须实现invoke方法<br>
（2）创建被代理的类以及接口<br>
（3）调用Proxy的静态方法newProxyInstance，创建一个代理类<br>
（4）通过代理调用方法<br>

#### 3 . 描述一下java中的异常体系
![java中的异常体系](https://github.com/liuhuan2015/AndroidReview/blob/master/interview/images/java_%E5%BC%82%E5%B8%B8.png)<br>
如图，java中异常体系的基类是Thorwable类（表示可抛出），它是所有异常和错误的超类，两个直接子类为Error和Exception，分别表示错误和异常。其中异常类Exception又分为运行时异常(RuntimeException)和非运行时异常。<br>
* Error是程序无法处理的错误，比如OutOfMemoryError、ThreadDeath等。这些异常发生时， Java虚拟机（JVM）一般会选择线程终止。
* Exception是程序本身可以处理的异常，这种异常分两大类运行时异常和非运行时异常，程序中应当尽可能去处理这些异常。运行时异常都是RuntimeException类及其子类异常，如NullPointerException、IndexOutOfBoundsException等， 这些异常是不检查异常，程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的， 程序应该从逻辑角度尽可能避免这类异常的发生。

#### 4 . 描述一下java中一个类的加载过程
>Person person = new Person();<br>
1 . 查找Person.class，并加载到内存中。<br>
2 . 执行类里的静态代码块。<br>
3 . 在堆内存里开辟内存空间，并分配内存地址。<br>
4 . 在堆内存里建立对象的属性，并进行默认的初始化。<br>
5 . 对属性进行显示初始化。<br>
6 . 对对象进行构造代码块初始化。<br>
7 . 调用对象的构造函数进行初始化。<br>
8 . 将对象的地址赋值给person变量。<br>

#### 5 . 描述一下java中的类的加载机制
Java中的类加载是一个相对比较复杂的过程；它包括加载、验证、准备、解析和初始化五个阶段；对于开发者来说，可控性最强的是加载阶段；加载阶段主要完成三件事：<br>
 1 . 根据一个类的全限定名来获取定义此类的二进制字节流<br>
 2 . 将这个字节流所代表的的静态存储结构转化为JVM方法区中的运行时数据结构<br>
 3 . 在内存中生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据的访问入口<br>
 『通过一个类的全限定名获取描述此类的二进制字节流』这个过程被抽象出来，就是Java的类加载器模块，也即JDK中ClassLoader API<br>
 
 类加载器的双亲委派模型：<br>
 如果一个类加载器收到了加载类的请求，它不会自己立即去加载类，它会先去请求父类加载器，每个层次的类加载器都是如此。层层传递，直到传递到最高层的类加载器，只有当 父类加载器反馈自己无法加载这个类，才会由当前子类加载器去加载该类。<br>
 
 类加载器的这种双亲委派模型的好处：Java中类的相等性是由类与其类加载器共同判定的；判定两个类是否相等，只有在这两个类被同一个类加载器加载的情况下才有意义，否则即便是两个类来自同一个Class文件，被不同类加载器加载，它们也是不相等的。
 
 比如：Object类，无论哪个类加载器去尝试加载这个类，最终都会传递给最高层的类加载器去加载，这样Object类无论在何种类加载器环境下都是同一个类。
 
#### 6 . 为什么要使用多线程？
使用多线程更多的是为了提高cpu的并发，可以让cpu同时处理多个事情，提高程序效率，多线程的使用场景：<br>
 1 . 为了不让耗时操作阻塞主线程，开启新线程执行耗时操作<br>
 2 . 某种任务虽然耗时，但是不消耗cpu，例如：磁盘IO，可以开启新线程来做，可以显著提高效率<br>
 3 . 优先级比较低的任务，但是需要经常去做，例如：GC，可以开启新线程来做<br>
 
#### 7 . ThreadLocal了解多少？
在Android中消息机制主要是指Handler的运行机制，Handler的运行需要底层的MessageQueue和Looper的支持。<br>

MessageQueue，消息队列，内部存储了一组消息，以队列的形式对外提供插入和删除的工作，但是它的内部存储结构并不是真正的队列，而是单链表。<br>

Looper，轮询器，它会以无限循环的形式去查找MessageQueue中是否有新消息，如果有的话就处理，否则就一直等待着。<br>

在Looper中有一个特殊的概念，就是ThreadLocal，ThreadLocal并不是线程，它的作用是可以在不同的线程之中互不干扰的存储并提供数据。<br>

我们知道：Handler创建的时候会采用当前线程的Looper来构建消息循环系统，那么在Handler内部是如何获取到当前线程的Looper呢？这就要使用到ThreadLocal了，通过ThreadLocal可以轻松获取每个线程的Looper。但是要注意的是：线程默认是没有Looper的，如果要使用Handler就必须为线程创建Looper。<br>

我们经常提到的主线程，也叫UI线程，即ActivityThread，它在被创建时就会初始化Looper，这也是我们在主线程中默认可以使用Handler的原因。<br>

ThreadLocal是一个线程内部的数据存储类，通过它可以在指定的线程中存储数据，数据存储后，只有在指定的线程中才可以获取到数据，对于其它线程来说无法获取到数据。<br>

在日常开发中，用到ThreadLocal的地方比较少，但是在某些特殊的场景下，通过ThreadLocal可以轻松实现一些看起来很复杂的功能吗，这一点在Android的源码中也有所体现，比如Looper，ActivityThread，以及AMS中都用到了ThreadLocal。<br>

使用场景：<br>
1 . 当某些数据是以线程为作用域并且不同线程具有不同的数据副本的时候，可以考虑采用ThreadLocal。比如：对于Handler来说，它需要获取当前线程的Looper。<br>
2 . ThreadLocal的另一个使用场景是复杂逻辑下的对象传递，比如监听器的传递，有时候一个线程中的任务过于复杂，这可能表现为函数调用栈比较深以及代码入口的多样性，在这种情况下，我们又需要监听器能够贯穿整个线程的执行过程。这个时候就可以采用ThreadLocal。(详见任玉刚的Android开发艺术探索)。<br>

#### 8 . java中的注解了解多少？
注解相当于一种标记，在程序中加了注解就等于为程序打上了标记。程序可以利用java的反射机制来了解你的类及各种元素上有无何种标记，针对不同的标记，去做相应的事件。标记可以加在包，类，字段，方法，方法的参数以及局部变量上。
比如我们常用的ButterKnife注解框架。

#### 9 . String，StringBuffer，StringBuilder有什么区别？
1 . String是不可变的（修改String时，不会在原有的内存地址修改，而是重新指向一个新对象），String用final修饰，不可继承。<br>
String本质上是个final的char[]数组，所以char[]数组的内存地址不会被修改，而且String也没有对外暴露修改char[]数组的方法。<br>
不可变性可以保证线程安全以及字符串常量池的实现。<br>
2 . StringBuffer是线程安全的<br>
3 . StringBuilder是非线程安全的<br>

#### 10 . Java中的泛型了解多少？
Java泛型是JDK 5中引入的一个新特性，泛型提供了编译时类型安全检测机制，该机制允许我们在编译时检测到非法的类型。<br>
泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。<br>

为什么使用泛型？<br>
1 . 相对于使用Object这种简单粗暴的方式，泛型提供了一种参数化的能力，使得数据的类型可以像参数一样被传递进来，这提供了一种扩展能力。<br>

2 . 当数据类型确定以后，提供了一种类型检测机制，只有相匹配的数据才可以正常赋值，否则编译错误，增强了安全性。<br>

3 . 泛型提高了代码的可读性，不必等到运行时才去执行类型转换，在编写代码阶段，我们就可以通过参数书写正确的数据类型<br>

在Android中泛型得到了大量的使用，比如：Retrofit，Mvp架构等。

#### 11 . Lambda表达式了解吗？
Lambda表达式俗称匿名函数。Kotlin的Lambda表达式更“纯粹”一点，因为它是真正把Lambda抽象为了一种类型，而Java 8的Lambda只是单方法匿名接口实现的语法糖。<br>

```java
val printMsg = { msg: String -> 
	println(msg) 
}

fun main(args: Array<String>) {
  printMsg("hello")
}

```










 
 
 
  


