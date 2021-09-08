package com.github.zjjfly.concurrency.ch3;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

/**
 * 对象发布和逸出
 * 发布指的是是对象能在当前作用域之外使用
 *
 * @author zjjfly
 */
public class PublishEscape {
    /**
     * 发布对象的最简单方式,把它设为公有的静态变量
     */
    public static Set<Secret> knownSecrets;
    private String[] states = new String[]{"AK", "AL"};

    public void initialize() {
        knownSecrets = new HashSet<>();
    }

    /**
     * 另一种发布方式,使用非私有的方法返回一个私有变量
     * 这个例子中,其实states已经了逸出它的作用域,因为它本应是私有的
     */
    public String[] getStates() {
        return states;
    }

    /**
     * 最后一种发布,发布一个内部类实例
     */
    class ThisEscape {
        public ThisEscape(Button btn) {
            btn.addActionListener(e -> {
                //可以调用say,说明其实ThisEscape的实例进入了这个ActionListener,而且这个时候发布的是一个尚未构造完成的实例
                //所以,不要在构造过程中使this逸出
                say();
                System.out.println("click");
            });
        }

        public void say() {
            System.out.println("haah");
        }
    }

    /**
     * 避免在构造时this逸出的办法:
     */
    static class SafeListener {
        private final ActionListener actionListener;

        private SafeListener() {
            actionListener = e -> System.out.println("click");
        }

        public static SafeListener newInstance(Button button) {
            SafeListener safeListener = new SafeListener();
            //构造好SafeListener之后再把ActionListener绑定到Button
            button.addActionListener(safeListener.actionListener);
            return safeListener;
        }
    }

    class Secret {
    }

}
