package com.github.zjjfly.concurrency.ch2;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zjjfly
 */
@ThreadSafe
public class CountingFactorizer extends HttpServlet {

    //AtomicLong是一个线程安全的对象
    //如果一个无状态的类添加一个状态时,如果这个对象完全用线程安全对象管理,那么这个类仍然是线程安全的
    private final AtomicLong count = new AtomicLong(0);

    public AtomicLong getCount() {
        return count;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        count.incrementAndGet();
    }
}
