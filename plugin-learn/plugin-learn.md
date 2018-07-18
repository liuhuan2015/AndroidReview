Android 插件化学习
>学习的目标文章是[Android插件化技术入门](https://www.jianshu.com/p/b6d0586aab9f)
#### 一 . 插件化概述
方法数超过65535的问题，我们可以通过Dex分包来解决，同时也可以通过使用插件化开发来解决。插件化的概念就是由宿主APP去加载以及运行插件APP。<br>

**插件化的优势：**<br>

* 在一个大的项目里面，为了明确的分工，往往不同的团队负责不同的插件APP，这样分工更加明确。各个模块封装成不同的插件APK，不同模块可以单独编译，提高了开发效率。
* 解决了上述的方法数超过限制的问题。
* 可以通过上线新的插件来解决线上的BUG，达到“热修复”的效果。
* 减小了宿主APK的体积。

**插件化的缺点：**<br>
* 插件化开发的APP不能在Google Play上线，也就是没有海外市场（Google Play的开发者协议不允许绕开Google Play Store进行代码层面的更新，并非是国外的开发者不想使用这个技术，毕竟在海外Google Play基本垄断着应用市场）。

**综上所述，如果要开发的app不需要支持海外的话，还是可以考虑使用插件化开发的**
#### 二 . 插件化、热修复（思想）的发展历程
>只抄写了一部分
 * 2012年7月，AndroidDynamicLoader，大众点评，陶毅敏
 * 2014年底，Dynamic-load-apk，任玉刚：动态加载APK，通过Activity代理的方式给插件Activity添加生命周期。
 * 2015年8月，DroidPlugin，360的张勇：DroidPlugin 是360手机助手在 Android 系统上实现了一种新的插件机制：
 通过Hook思想来实现，它可以在无需安装、修改的情况下运行APK文件,此机制对改进大型APP的架构，实现多团队协作开发具有一定的好处。
 * 2015年9月，AndFix，阿里：通过NDK的Hook来实现热修复。
 * 2015年11月，Nuwa，大众点评：通过dex分包方案实现热修复。
 * 2015年底，Small，林光亮：打通了宿主与插件之间的资源与代码共享。
 
 他们的对比，略过了...
 #### 三 . 插件化的原理
 上面的插件框架，其原理无非就是：<br>
 1. 通过DexClassLoader加载
 2. 代理模式添加生命周期
 3. Hook思想跳过清单验证
 插件化还需要掌握一些系统底层的知识，比如说IPC，Android系统，四大组件的启动过程，Apk的安装过程。
 #### 四 . 插件化实战体验
 ##### 4.1 通过DexClassLoader加载插件APK
 先把经过验证的插件APK复制到宿主APP的files目录下面，这样保证了APK的安全性；<br>
 
 然后通过DexClassLoader进行加载的时候，指定插件APK的路径以及解压之后的dex存放路径。<br>
 
 最后通过反射调用插件里面的代码。<br>
 ##### 4.2 通过面向接口（抽象）编程调用插件的代码
 这里讲了一种使用面向接口编程思想来调用插件代码的方法。<br>
 
 建一个pluginlibrary，我们的app以及plugin模块都要引用这个库pluginlibrary。具体代码实现可见module plugin-library、plugin-uselib、plugin-learn-uselib。<br>
 
 使用这种方式，最后在宿主加载插件的时候，可以直接把创建的对象转换为类库中的这个接口就可以，省去了反射的一系列繁琐操作。<br>
 ```java
                     Class<?> beanClass = mPluginClassLoader.loadClass("com.newabel.plugin_uselib.Bean");
                     IBean beanObject = (IBean) beanClass.newInstance();
 
                     beanObject.setName("哈哈哈哈");
                     Toast.makeText(MainActivity.this, beanObject.getName(), Toast.LENGTH_SHORT).show();
 ```
##### 4.3 通过面向切面编程调用插件中的带回调方法

 
 
 
 
 
 
 
 