package com.github.zjjfly.concurrency.ch3;

/**
 * @author zjjfly[https://github.com/zjjfly] on 2020/4/26
 */
public class Conclusion {
    //在并发程序中使用和共享对象时,有几种方式:
    //1.线程封闭,即对象只被单个线程拥有,如局部变量或ThreadLocal
    //2.只读共享,对象是只读的,所以不存在线程安全问题,包括不可变对象和事实不可变对象
    //3.线程安全共享,对象在器内部实现同步,提供了线程安全的方法来获取和修改状态,如java.util.concurrent包中的很多类
    //4.保护对象,指定是那些需要通过特定的锁才能访问的对象,包括存放在线程安全的集合中对象和已发布的有某个锁保护的对象
}
