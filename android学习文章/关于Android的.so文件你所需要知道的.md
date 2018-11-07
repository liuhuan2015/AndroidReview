>[关于Android的.so文件你所需要知道的](https://www.jianshu.com/p/cb05698a1968)

早期的android系统只支持 arm v5 的cpu架构，现在支持 7 种。

android系统目前支持以下七种不同的cpu架构：

* ARM v5
* ARM v7(从2010年起)
* x86(从2011年起)
* MIPS(从2012年起)
* ARM v8
* MIPS64
* x86_64 (从2014年起)

每一种都关联着一个对应的abi。

应用程序二进制接口（Application Binary Interface）定义了二进制文件（尤其是.so文件）如何运行在相应的系统平台上，从使用的指令集，内存对齐到可用的系统函数库。

在Android系统上，每一个CPU架构对应一个ABI：armeabi，armeabi-v7a，x86，mips，arm64-v8a，mips64，x86_64。

#### 为什么需要关注 .so 文件？

如果项目中用到ndk，那么项目将会引入.so文件，然后调用其中的方法。

有的时候，我们并没有引入.so文件，但是我们需要知道，项目中依赖的函数库或者引擎库里面可能已经嵌入了.so文件，并且依赖于不同的abi。

例如项目中使用的RenderScript支持库，OpenCV，Unity，android-gif-drawable，SQLCipher等，在生成的apk文件中会包含有.so文件。

很多设备都支持多于一种的ABI。例如ARM64和x86设备也可以同时运行armeabi-v7a和armeabi的二进制包。但最好是针对特定平台提供相应平台的二进制包，这种情况下运行时就少了一个模拟层（例如x86设备上模拟arm的虚拟层），
从而可以得到更好的性能（归功于最近的架构更新，例如硬件fpu，更多的寄存器，更好的向量化等）。

#### App中可能出错的地方

提供.so文件需要注意的地方：尽可能的提供专为每个ABI优化过的.so文件，但要么全部支持，要么都不支持：不能混合着使用。应该为每个ABI目录提供对应的.so文件。

**当一个应用安装在设备上，只有该设备支持的CPU架构对应的.so文件会被安装。在x86设备上，libs/x86目录中如果存在.so文件的话，会被安装，如果不存在，则会选择armeabi-v7a中的.so文件，
如果也不存在，则选择armeabi目录中的.so文件（因为x86设备也支持armeabi-v7a和armeabi）。**

**其它可能出错的地方**

当引入一个.so文件时，不止要考虑cpu架构。有时我们还会遇到一系列常见的错误，其中最多的是："UnsatisfiedLinkError"，"dlopen: failed"以及其他类型的crash。

**使用android-21平台版本编译的.so文件运行在android-15的设备上**

使用ndk时，我们可能会倾向于使用最新的编译平台，但是因为**ndk平台不是后向兼容的，而是前向兼容的**。所以推荐使用app的minSdkVersion对应的编译平台。

这也意味着当我们引入一个预编译好的.so文件时，需要先检查它被编译时所用的平台版本。

**混合使用不同C++运行时编译的.so文件**

.so文件可以依赖于不同的C++运行时，静态编译或者动态加载。混合使用不同版本的C++运行时可能导致很多奇怪的crash，是应该避免的。作为一个经验法则，当只有一个.so文件时，静态编译C++运行时是没问题的，
否则当存在多个.so文件时，应该让所有的.so文件都动态链接相同的C++运行时。

这意味着**当引入一个新的预编译.so文件，而且项目中还存在其他的.so文件时，我们需要首先确认新引入的.so文件使用的C++运行时是否和已经存在的.so文件一致。**

**没有为每个支持的CPU架构提供对应的.so文件**

例如：项目支持armeabi-v7a和x86架构，然后项目新增了一个函数库依赖，这个函数库包含.so文件并支持更多的CPU架构，例如新增android-gif-drawable函数库：
```java
    compile ‘pl.droidsonroids.gif:android-gif-drawable:1.1.+’
```
发布我们的app后，会发现它在某些设备上会发生Crash，例如Galaxy S6，最终可以发现只有64位目录下的.so文件被安装进手机。

解决方案：重新编译我们的.so文件使其支持缺失的ABIs，或者使用 ndk.abiFilters 来显示指定支持的ABIs。
```java
    ndk {
            abiFilters "armeabi-v7a" // "armeabi", "x86", "arm64-v8a"
        }
```
作者说：如果你是一个SDK提供者，但提供的函数库不支持所有的ABIs，那你将会搞砸你的用户，因为他们能支持的ABIs必将只能少于你提供的。

**将.so文件放在错误的地方**

* Android Studio工程放在jniLibs/ABI目录中（当然也可以通过在build.gradle文件中的设置jniLibs.srcDir属性自己指定）
* Eclipse工程放在libs/ABI目录中（这也是ndk-build命令默认生成.so文件的目录）

**只提供armeabi架构的.so文件而忽略其他ABIs的**

所有的x86/x86_64/armeabi-v7a/arm64-v8a设备都支持armeabi架构的.so文件，因此似乎移除其他ABIs的.so文件是一个减少APK大小的好技巧。但事实上并不是：这不只影响到函数库的性能和兼容性。

x86设备能够很好的运行ARM类型函数库，但并不保证100%不发生crash，特别是对旧设备。64位设备（arm64-v8a, x86_64, mips64）能够运行32位的函数库，但是以32位模式运行，
在64位平台上运行32位版本的ART和Android组件，将丢失专为64位优化过的性能（ART，webview，media等等）。

以减少APK包大小为由是一个错误的借口，因为也可以选择在应用市场上传指定ABI版本的APK，生成不同ABI版本的APK可以在build.gradle中如下配置：
```java
android {
    ...
    splits {
        abi {
            enable true
            reset()
            include 'x86', 'x86_64', 'armeabi-v7a', 'arm64-v8a' //select ABIs to build APKs for
            universalApk true //generate an additional APK that contains all the ABIs
        }
    }

    // map for the version code
    project.ext.versionCodes = ['armeabi': 1, 'armeabi-v7a': 2, 'arm64-v8a': 3, 'mips': 5, 'mips64': 6, 'x86': 8, 'x86_64': 9]

    android.applicationVariants.all { variant ->
        // assign different version code for each output
        variant.outputs.each { output ->
            output.versionCodeOverride =
                    project.ext.versionCodes.get(output.getFilter(com.android.build.OutputFile.ABI), 0) * 1000000 + android.defaultConfig.versionCode
        }
    }
 }
```

























