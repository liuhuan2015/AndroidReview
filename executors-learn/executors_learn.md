Android线程池框架，Executor、ThreadPoolExecutor详解<br>
>在Java和Android中，使用到的线程池基本都是一样的<br>

Java/Android线程池框架的结构主要包括三个部分：<br>
1. 任务：包括被执行任务需要实现的接口类，Runnable或Callable。
2. 任务的执行器：包括任务执行机制的核心接口类Executor，以及继承自Executor的ExecutorService接口。
3. 执行器的创建者：工厂类Executors。

#### 一 . Executor和ExecutorService
Executor只是一个接口，它是Java/Android线程池框架的基础，它将任务的提交和任务的执行分离开来。<br>

ExecutorService继承自Executor，有两个关键类实现了ExecutorService接口：ThreadPoolExecutor和ScheduledThreadPoolExecutor。<br>

（1）ThreadPoolExecutor 是线程池的核心实现类，用来执行被提交的任务。<br>

（2）ScheduledThreadPoolExecutor 也是一个实现类，可以在给定的延迟后运行命令，或者定期执行命令。它比Timer更灵活，功能更强大。<br>

#### 二 . Executors工厂类
 Executors是一个工厂类，它不继承任何其它类，它通过ThreadPoolExecutor、ScheduledThreadPoolExecutor创建出四种不同的线程池，分别为：<br>
 (1) newCachedThreadPool 创建一个可缓存线程池，线程池的最大长度无限制，但如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
  
 (2) newFixedThreadPool  创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

 (3) newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。 
 
 (4) newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 
 
