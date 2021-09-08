package com.github.zjjfly.concurrency.ch3;

import net.jcip.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * 另一种保证线程安全的手段是不可变性
 *
 * @author zjjfly
 */
public class Immutability {
    /**
     * 不可变对象的要满足三个条件:
     * 1.对象创建以后其状态就不能修改
     * 2.对象的所有成员都是final的,但成员的类型不一定时不可变的
     * 3.对象是正确创建的(在初始化的时候,this引用没有逸出)
     */

    @Immutable
    final class ThreeStooges {
        /**
         * Set是可变类型,但不影响ThreeStooges的不可变性,因为它没有对外提供对其状态修改的方法
         */
        private final Set<String> stooges = new HashSet<>();

        public ThreeStooges() {
            stooges.add("Moe");
            stooges.add("Larry");
            stooges.add("Curly");
        }

        public boolean isStooge(String name) {
            return stooges.contains(name);
        }
    }

}
