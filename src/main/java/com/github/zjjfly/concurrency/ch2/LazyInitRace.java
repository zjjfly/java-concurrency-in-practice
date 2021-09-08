package com.github.zjjfly.concurrency.ch2;

import net.jcip.annotations.NotThreadSafe;

/**
 * 由于不恰当的执行时序而导致的问题叫做竞态条件
 * 一种常见的竞态条件是先检查后执行,它先检查某个条件是否符合,如果符合则执行某种操作.但在多线程的情况下,很容易发生检查之后,发生了状态变化
 *
 * @author zjjfly
 */
@NotThreadSafe
public class LazyInitRace {
    private Object expensiveOInstance = null;

    public Object getInstance() {
        if (expensiveOInstance == null) {
            //这个是可能其他线程已经初始化了一个对象
            expensiveOInstance = new Object();
        }
        return expensiveOInstance;
    }
}
