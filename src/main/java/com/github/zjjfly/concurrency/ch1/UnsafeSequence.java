package com.github.zjjfly.concurrency.ch1;

import net.jcip.annotations.NotThreadSafe;

import java.util.stream.IntStream;

/**
 * @author zjjfly
 */
@NotThreadSafe
public class UnsafeSequence {
    private int value;

    public static void main(String[] args) throws InterruptedException {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> IntStream.range(0, 10000).forEach(j -> unsafeSequence.getNext())).start();
        }
        Thread.sleep(5000L);
        //最终的value的值远小于预期,其实就是因为多线程的情况下,多个线程同时读取的value可能是相同的,所以这些线程这次调用getNext只让value加了1
        System.out.println(unsafeSequence.getNext());
    }

    private int getNext() {
        return value++;
    }
}
