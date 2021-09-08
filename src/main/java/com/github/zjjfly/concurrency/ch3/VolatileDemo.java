package com.github.zjjfly.concurrency.ch3;

/**
 * volatile只适用下面这几个场景
 * 1.对变量的操作写入操作不依赖当前值,或者你能确保只有单个线程更新变量的值
 * 2.该变量不与其他状态变量一起纳入不变性条件
 * 3.在访问变量时不需要加锁
 */
class VolatileDemo {
    private volatile static VolatileDemo instance = null;

    private VolatileDemo() {

    }

    /**
     * volatile的一个使用场景,double check
     *
     * @return
     */
    public static VolatileDemo getInstance() {
        if (instance == null) {
            synchronized (VolatileDemo.class) {
                //再次检查的原因是,在高并发情况下,可能其他线程已经调用过这个方法,初始化了instance
                if (instance == null) {
                    instance = new VolatileDemo();
                }
            }
        }
        return instance;
    }
}
