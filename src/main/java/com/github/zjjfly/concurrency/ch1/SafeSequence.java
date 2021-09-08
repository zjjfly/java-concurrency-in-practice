package com.github.zjjfly.concurrency.ch1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.stream.IntStream;

/**
 * @author zjjfly
 */
@ThreadSafe
public class SafeSequence {
    @GuardedBy("this")
    private int value;

    public static void main(String[] args) throws InterruptedException {
        SafeSequence sequence = new SafeSequence();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> IntStream.range(0, 10000).forEach(j -> sequence.getNext())).start();
        }
        Thread.sleep(5000L);
        assert sequence.getNext() == 100000;
    }

    private synchronized int getNext() {
        return value++;
    }
}
