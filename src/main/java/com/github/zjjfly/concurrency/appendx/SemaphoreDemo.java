package com.github.zjjfly.concurrency.appendx;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author zjjfly
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(2, true);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println(
                        "线程" + Thread.currentThread().getName() + "进入，已有" + (2 - semaphore.availablePermits()) + "并发");
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                semaphore.release();
                System.err.println("线程" + Thread.currentThread().getName() + "已经离开" + "当前并发数：" + (2 - semaphore
                        .availablePermits()));
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}


