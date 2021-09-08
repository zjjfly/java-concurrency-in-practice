package com.github.zjjfly.concurrency.ch3;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author zjjfly
 */
@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger number, BigInteger[] factors) {
        this.lastNumber = number;
        //必须要拷贝数组,否则这个对象就不是不可变的了
        this.lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (null == lastNumber || !i.equals(lastNumber)) {
            return null;
        } else {
            //必须要拷贝数组,否则这个对象就不是不可变的了
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}
