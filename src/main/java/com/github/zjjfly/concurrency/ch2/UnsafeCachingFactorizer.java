package com.github.zjjfly.concurrency.ch2;

import net.jcip.annotations.NotThreadSafe;

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
 * 计算一个数的因数,并把结果放入缓存
 * 这个类有两个状态,尽管这两个对象都是线程安全的,但代码中还是存在竞态条件
 * 保持状态的一致性,需要在单个原子操作中更新所有相关的状态变量
 *
 * @author zjjfly
 */
@NotThreadSafe
public class UnsafeCachingFactorizer extends HttpServlet {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这个类行为正确的条件是,更新lastNumber和更新lastFactor这两个动作需要在一个原子操作中完成,否则就可能出错,因为它们是关联的
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

    private BigInteger[] factor(BigInteger number) {
        return new BigInteger[0];
    }

    private void encodeIntoResponse(HttpServletResponse response, BigInteger[] factors) throws IOException {
        response.getOutputStream()
                .write(Stream.of(factors).map(BigInteger::toString).collect(
                        Collectors.joining(",")).getBytes());
    }

}
