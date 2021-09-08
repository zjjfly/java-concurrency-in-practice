package com.github.zjjfly.concurrency.ch4;

import lombok.Data;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * 实例封装是把一个线程不安全的类转换成一个线程安全类的最简单的方式
 * Java提供了不少把线程不安全的类转成线程安全类的方法,如Collections.synchronizedList
 *
 * @author zjjfly[https://github.com/zjjfly] on 2020/4/27
 */
@ThreadSafe
public class PersonSet {
    @GuardedBy("this")
    private final Set<Person> mySet=new HashSet<>();

    public synchronized void addPerson(Person p){
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p){
        return mySet.contains(p);
    }

    @Data
    static class Person{

    }

}


