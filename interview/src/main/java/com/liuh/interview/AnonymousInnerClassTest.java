package com.liuh.interview;

/**
 * Date: 2018/7/3 09:34
 * Description:匿名内部类<br>
 * 匿名内部类对外部变量的使用：如果是使用的外部的类的成员变量，该成员变量不需要声明为final；<br>
 * 如果匿名内部类是使用的当前所在方法内定义的变量，则该变量需要声明为final类型.<br>
 * 解释：<br>
 * 在编译后形成的class文件中，匿名内部类最终用会编译成一个单独的类，而被该类使用的变量会以构造函数参数的形式传递给该类，<br>
 * 例如number2,如果变量number2不定义成final的，number2在匿名内部类中可以被修改，进而造成和外部的number2不一致的问题<br>
 *
 *
 */

public class AnonymousInnerClassTest {

    private int number;

    public void test(String[] args) {
        int number2 = 1;

        new MyInterface() {
            @Override
            public void doSomething() {
                System.out.println(number);
            }
        }.doSomething();

    }

}
