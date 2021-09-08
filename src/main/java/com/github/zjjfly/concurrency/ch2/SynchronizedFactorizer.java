package com.github.zjjfly.concurrency.ch2;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * java内置的锁:同步代码库.使用关键字synchronized
 * 它是一种互斥锁,同一时间只有一个线程持有这个锁
 * 它也是一个可重入锁,意味着一个线程如果请求已经由它持有的锁的时候,请求可以成功.这样可以避免在同步方法中调用其他同步方法产生死锁
 * 重入锁的一种实现方式是为每个线程关联一个计数器和一个所有者线程.计数器为0时,表示这个锁没有被任何线程持有.线程获取锁(包括重入)的时候计数器会加1,当退出同步代码块的时候计数器减1
 * 如果有多个状态变量,需要确保这些对象都是由同一个锁保护的
 *
 * @author zjjfly
 */
@ThreadSafe
public class SynchronizedFactorizer extends HttpServlet {
    @GuardedBy("this")
    private AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    @GuardedBy("this")
    private AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    /**
     * 在这个方法上加synchronized并不好,因为HttpServlet需要能同时处理多个请求,加了synchronized会导致性能的大幅下降,因为它对整个servlet对象加了锁,导致同一时间只有一个http请求被处理
     */
    @Override
    protected synchronized void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BigInteger i = (BigInteger) req.getAttribute("number");
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(resp, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }

    private void encodeIntoResponse(HttpServletResponse response, BigInteger[] factors) throws IOException {
        response.getOutputStream()
                .write(Stream.of(factors).map(BigInteger::toString).collect(
                        Collectors.joining(",")).getBytes());
    }

    private BigInteger[] factor(BigInteger number) {
        return new BigInteger[0];
    }
}
