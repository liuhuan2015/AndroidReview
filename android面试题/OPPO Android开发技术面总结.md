>[OPPO Android开发技术面总结](https://juejin.im/post/5bc44725e51d450e7211033f)

* 1.如何理解Java的多态？其中，重载和重写有什么区别？
* 2.谈一下JVM虚拟机内存分配？哪部分是线程公有的，哪部分是私有的？
* 3.final关键字的用法？
* 4.死锁是怎么导致的？
* 5.数据库如何进行升级？SQLite增删改查的基础sql语句
* 6.Broadcast的分类？有序，无序？粘性，非粘性？本地广播？
* 7.Touch事件是如何传递的？
* 8.Handler的原理？
* 9.ANR出现的情况有几种？怎么分析解决ANR问题？
* 10.内存泄露的场景有哪些？内存泄漏分析工具使用方法？
* 11.如何实现启动优化，有什么工具可以使用？
* 12.常用的设计模式有哪些？是否了解责任链模式？

#### 1. 如何理解Java的多态？其中，重载和重写有什么区别？
多态是指多个子类继承同一个父类，并且对父类的方法进行不同的重写，这时候，当父类引用指向不同的子类对象时，调用同一个功能方法可能会产生不同的功能行为。

多态的三个必要条件：
 1. 子类继承父类
 2. 子类重写父类的方法
 3. 父类引用指向子类对象

重载是指在一个类中存在多个方法，它们的方法名相同，方法参数不同。

重写主要存在于子类父类之间，主要是子类对父类的方法的实现重写进行编写，产生属于自己的行为。

#### 2. 谈一下JVM虚拟机内存分配？哪部分是线程公有的，哪部分是私有的？

分为程序计数器、本地方法栈、虚拟机栈、堆、方法区（运行时常量池），其中前三个是线程不共享的，即这部分内存，每个线程独有，不会让别的程序访问到，后两个是线程共享的，它们会出现线程安全问题。

 1. 程序计数器（寄存器）

     当前线程所执行的字节码信号指示器，字节码解释器工作依赖计数器控制完成，等等。

     例如：有两个线程，其中一个线程可以暂停使用，让其他线程运行，然后等自己获得cpu资源时，又能从暂停的地方开始运行，那么为什么能够记住暂停的位置的，这就依靠了程序计数器。
 2. 本地方法栈

     很多的算法或者一个功能的实现，都被java封装到了本地方法中，程序直接通过调用本地的方法就行了，本地方法栈就是用来存放这种方法的，实现该功能的代码可能是C也可能是C++,反正不一定就是java实现的。

 3. 虚拟机栈

     虚拟机栈描述的是Java方法执行的内存模型：每个方法在执行的同时都会创建一个栈帧用来存放存储局部变量表、操作数表、动态连接、方法出口等信息，每一个方法从调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中入栈到出栈的过程。
 4. 堆

     所有线程共享的一块内存区域。Java虚拟机所管理的内存中最大的一块，因为该内存区域的唯一目的就是存放对象实例。几乎所有的对象实例都在这里分配内存，也就是通常我们说的new对象，该对象就会在堆中开辟一块内存来存放对象中的一些信息。

     同时堆也是垃圾收集器管理的主要区域。因此很多时候被称为"GC堆"。
 5. 方法区和其中的运行时常量池

     和堆一样，是各个线程共享的内存区域，用于存储已被虚拟机加载的类信息、常量、静态变量、和编译器编译后的代码(也就是存储字节码文件，.class)等数据，这里可以看到常量也会在方法区中，是因为方法区中有一个运行时常量池。

#### 3. final关键字的用法？

final的意思是“不可变，最终的”，它可以用来修饰变量、方法、类。

被final修饰的变量，只能进行一次赋值操作，并且在生存期内不可以改变它的值。还有就是，final会告诉编译器，这个数据是不会修改的，那么编译器就可能会在编译时期就对该数据进行替换甚至执行计算。

被final修饰的方法，它表示该方法不能被覆盖。

被final修饰的类，它表示该类不能被继承或实现。

#### 4. 死锁是怎么导致的？

死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象。若无外力作用，它们都将无法推进下去。

死锁的发生必须具备四个必要条件：
 1. 互斥条件
 2. 请求和保持条件
 3. 不可剥夺条件
 4. 环路等待条件

#### 5. 数据库如何进行升级？SQLite增删改查的基础sql语句

android上的数据库升级是通过一个版本号来决定的，当版本号变大时，意味着数据库版本将会进行升级。

基础的sql语句有增（insert）、删（delete）、改（update）、查（select）。

在android上面操作数据库时，有时候可能不会选择直接使用原生的 sqlite，而会选择使用一些比较好的orm（对象关系映射）框架，比如GreenDao，Realm等。

#### 6. Broadcast的分类？有序，无序？粘性，非粘性？本地广播？

Broadcast分为：标准广播、有序广播、本地广播。

 1. 标准广播：context.sendBroadcast(intent)，不可被拦截
 2. 有序广播：context.sendOrderBroadcast(intent)，可被拦截
 3. 本地广播：localBroadcastManager.sendBroadcast(intent)，只在app内传播
 4. 粘性广播：context.sendStickyBroadcast(intent)，使用的话需要在清单文件中加权限。

#### 7. Touch事件是如何传递的？

Touch 事件一般都是从最外层的 ViewGroup 开始传递的，首先会触发 ViewGroup 的 dispatchTouchEvent() 方法，dispatchTouchEvent() 方法内会调用 onInterceptTouchEvent() 方法，来决定是否拦截事件，
如果拦截，则事件不会再向下传递，事件会交给 ViewGroup 的 onTouchEvent() 方法处理，如果不拦截，事件就会继续向下传递，如果下一层还是ViewGroup，处理流程一样，如果是View，并且这个View设置了onTouchListener，
那么会调用这个 onTouchListener 的 onTouch() 方法，如果 onTouch() 方法返回true，处理完毕，如果返回false，那么事件就会传递给View的onTouchEvent() 进行处理。

 1. 一个事件系列以down事件开始，中间包含数量不定的move事件，最终以up事件结束
 2. 正常情况下，一个事件序列只能由一个View拦截并消耗
 3. ViewGroup默认不拦截任何事件
 4. View没有onInterceptTouchEvent方法，一旦事件传递给它，它的onTouchEvent方法会被调用

#### 8. Handler的原理？
Handler机制是android中的一种消息机制，主要用于在子线程中发消息到主线程，然后主线程做更新UI操作的场景。

内部实现涉及到 Looper，MessageQueue，ThreadLocal等概念。

Looper，消息轮询器，它会一直检查MessageQueue中是否有新的消息，有的话就会取出交给Handler进行处理，没有的话就会一直阻塞在那里。

MessageQueue，消息队列，内部是通过一个单链表的数据结构来维护消息队列，这种数据结构在插入和删除的性能上比较有优势。

ThreadLocal，线程内部的数据存储类，Handler在创建的时候，会采用当前线程的Looper来构造消息循环系统，那么这个Looper是如何获取的呢，
使用的是ThreadLocal，ThreadLocal可以在不同的线程间互不干扰的存储和提供数据，通过ThreadLocal可以轻松的获取每个线程的Looper。

线程默认是没有Looper的，如果想要使用Handler，就必须要为线程创建Looper，然后Handler通过Looper来构建消息循环系统。我们经常提到的主线程，UI线程，即ActivityThread，
它在被创建时就会初始化Looper，这也是我们在主线程中默认可以直接使用Handler的原因。

#### 9. ANR出现的情况有几种？怎么分析解决ANR问题？
ANR，应用程序无响应，在 android 中，主线程如果在规定的时间内没有处理完相应的工作，就会出现 ANR。

具体来说，有以下几种情况：<br>
  1. 输入事件（按键和触摸事件）5s内没有被处理：Input event dispatching timed out
  2. BroadcastReceiver接收到广播事件后(onReceive()方法)，在规定的时间内没有处理完（前台广播为10s，后台广播为60s）
  3. service 前台20s，后台200s未完成启动：Timeout executing service
  4. 其它情况

分析解决：<br>
  ANR问题是由于主线程的任务在规定时间内没处理完，因此要解决的话一般从以下几个方面：<br>
  1. 主线程在做一些耗时的工作
  2. 主线程被其他线程锁
  3. cpu被其他进程占用，该进程没被分配到足够的cpu资源
可以通过查看anr日志来具体分析产生anr的原因

#### 10. 内存泄露的场景有哪些？内存泄漏分析工具使用方法？
>来自文章[Android内存优化——常见内存泄露及优化方案](https://www.jianshu.com/p/ab4a7e353076)

当一个无用对象（不需要再使用的对象）仍然被其它对象持有引用，那么就会造成这个对象无法被系统回收，以致该对象在堆中所占用的内存无法被释放，这种情况就是内存泄漏。

比如说我们常用的Activity在关闭后，他原来占用的内存空间本该被系统回收的，但是由于有一个对象持有它的引用，造成它的内存空间无法被系统回收，这样就造成了内存泄漏。

内存泄漏分析工具有LeakCanery等。

常见的内存泄漏场景有：<br>
1. 单例导致内存泄漏

    单例的静态特性使得它的生命周期同应用生命周期一样长，如果一个对象已经没有用处了，但是单例还是持有它的引用，那个在整个应用程序的生命周期它都不能被系统回收，从而导致内存泄漏。
2. 静态变量导致内存泄漏

    静态变量在内存中只有一个，存放在方法区，属于类变量，被这个类的所有实例共享。

    它的生命周期随着类的加载而开始，随着类的销毁而结束。

    当某一个类的静态变量持有其所属类的引用时，就会造成所属类无法被销毁。因为静态变量和它的所属类形成了一种相互持有引用的关系。在开发中要进行避免这种写法。
3. 非静态内部类导致内存泄漏

    非静态内部类（包括匿名内部类）默认就会持有外部类的引用，当非静态内部类对象的生命周期比外部类对象的生命周期长时，就会导致内存泄漏。

    一个典型的使用场景是Handler。

    比如我们在程序中这样使用Handler：

    ```java
    // Handler类型的成员变量
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    ```
    这样写的时候，系统就会提示我们：
    ```java
    This Handler class should be static or leaks might occur (anonymous android.os.Handler)
    ```

    当我们使用Handler发送Message的时候，Message是会持有Handler的引用的，而mHandler又是Activity的非静态内部类实例，因此mHandler会持有Activity的引用。
    Message会被发送到MessageQueue中，等待Looper的轮询处理，所以当Activity退出的时候，Message可能仍然存在于MessageQueue中等待处理或正在处理，这样就会导致Activity无法被系统回收，从而导致内存泄漏。

    在android中使用内部类，为了规避内存泄漏，一般会使用 静态内部类 + 弱引用 的方式。例如：
    > 补充：WeakReference，弱引用，如果一个对象是弱引用类型，垃圾回收器线程在扫描的过程中一旦发现它存在，不管当前内存空间是否充足，都会回收它的内存。
    垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只有弱引用的对象。
    ```java
    // new 的动作也可以放在onCreate(...)中做
    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {

        // 因为静态类不持有外部类的引用，所以这里建立一个对Activity的弱引用
        private WeakReference<MainActivity> activityWeakReference;

        public MyHandler(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null) {
                // 进行相关业务逻辑处理
                if (msg.what == 1) {

                }
            }
        }
    }
    ```
    在Activity销毁的时候，还需要把mHandler的回调和发送的消息给移除掉。
    ```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
    ```
    非静态内部类造成内存泄漏还有一种情况就是线程的使用，比如Thread和AsyncTask等，解决方法也是 静态内部类 + 弱引用。
4. 未取消注册或回调导致内存泄漏

    比如说在Activity动态注册一个广播接收者，在Activity销毁的时候要注意取消注册，不然这个广播接收者会一直存在于系统中，它持有Activity的引用，导致Activity不能被销毁，造成内存泄漏。
    这种情况程序在运行的时候AS就会就会给出提示的。
5. Timer、TimerTask的使用导致的内存泄漏

    Timer 和 TimerTask 会经常被用来做一些计时或循环任务，当Activity销毁的时候，Timer可能还在等待执行TimerTask，造成Activity不能被回收，进而造成内存泄漏。
    因此在Activity销毁的时候，要cancel掉 Timer 和 TimerTask。
6. 资源未关闭或释放导致内存泄露

    在使用IO、File流或者Sqlite、Cursor等资源时要及时关闭。

7. WebView造成内存泄漏

#### 11. 如何实现启动优化，有什么工具可以使用？
Android启动优化好像是一个大课题，详见[启动优化](https://github.com/liuhuan2015/AndroidReview/blob/master/android%E9%9D%A2%E8%AF%95%E9%A2%98/%E5%90%AF%E5%8A%A8%E4%BC%98%E5%8C%96.md)

#### 12. 常用的设计模式有哪些？是否了解责任链模式？
设计模式也是一个大课题，详见[设计模式](https://github.com/liuhuan2015/DesignPatternLearn)






















































