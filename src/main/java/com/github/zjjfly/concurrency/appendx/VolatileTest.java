package com.github.zjjfly.concurrency.appendx;

/**
 * @author zjjfly
 */
public class VolatileTest {
    public volatile int inc = 0;

    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.increase();
                }
            }).start();
        }

        //保证前面的线程都执行完
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        //volatile可以保证可见性和有序性,但无法保证对变量操作的原子性
        //所以每次打印出的结果都不一样
        System.out.println(test.inc);
    }

    public void increase() {
        //自增操作其实不是原子操作
        inc++;
    }
}
