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
[]()
