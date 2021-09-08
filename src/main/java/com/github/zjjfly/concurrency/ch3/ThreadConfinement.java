package com.github.zjjfly.concurrency.ch3;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 线程封闭,实现线程安全的一种简单方式
 *
 * @author zjjfly
 */
public class ThreadConfinement {
    private static ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    });

    /**
     * 第三种线程封闭的方法是ThreadLocal,这种方法相对前两种更好,但要防止滥用.
     */
    public static Connection getConnection() {
        return connectionHolder.get();
    }

    /**
     * 第一种线程封闭的方法是ad-hoc线程封闭,实际只是通过一些特定的写法而不是靠可见性修饰符或局部变量来保证变量不被暴露到外部
     * 这种方法提供的封闭性很脆弱,不推荐使用
     */
    public int countLions(Collection<Animal> animals) {
        int count = 0;
        for (Animal animal : animals) {
            if ("Lion".equals(animal.getType())) {
                count++;
            }
        }
        return count;
    }

    /**
     * 第二种是栈封闭,实际上就是利用局部变量,因为局部变量本来就是封闭在执行线程中,位于执行线程的栈中,所以叫栈封闭
     */
    public int findMate(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;
        animals = new TreeSet<>(Comparator.comparing(Animal::getType));
        animals.addAll(candidates);
        for (Animal animal : animals) {
            if (candidate == null || !candidate.isPotentialMate(animal)) {
                candidate = animal;
            } else {
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }

    @Data
    static class Animal {
        private String type;

        boolean isPotentialMate(Animal a) {
            return a.type.equals(this.type);
        }

    }

}
