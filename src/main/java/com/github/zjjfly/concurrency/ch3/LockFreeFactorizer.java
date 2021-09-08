package com.github.zjjfly.concurrency.ch3;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 对于多个变量的竞态条件问题,可以把这些变量放入一个不可变对象,这样别的线程无法改变这个对象的状态
 * 然后用一个volatile来保证这个对象的可见性,这样不用锁就写出了线程安全的程序
 *
 * @author zjjfly
 */
@ThreadSafe
public class LockFreeFactorizer extends HttpServlet {
    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BigInteger number = (BigInteger) req.getAttribute("number");
        BigInteger[] factors = cache.getFactors(number);
        if (factors == null) {
            factors = factor(number);
            cache = new OneValueCache(number, factors);
        }
    }

    private BigInteger[] factor(BigInteger number) {
        return new BigInteger[0];
    }

}
