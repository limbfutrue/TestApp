package com.fe.home;

/**
 * Created by PSBC-26 on 2022/2/24.
 * 双重检查锁定单例
 * volatile 保证可见性，是为了禁止初始化实例时的重排序
 * synchronized 同步锁，保证只有一个线程在操作
 * 初始化一个实例在java字节码中会有4个步骤：
 * 1. 申请内存空间
 * 2. 初始化默认值（区别于构造器方法的初始化）
 * 3. 执行构造器方法
 * 4. 连接引用和实例
 */

public class SingleInstanceDemo {
    private static volatile SingleInstanceDemo singleInstanceDemo;

    private SingleInstanceDemo() {
    }

    public static SingleInstanceDemo getInstance() {
        if (singleInstanceDemo == null) {
            synchronized (SingleInstanceDemo.class) {
                if (singleInstanceDemo == null) {
                    singleInstanceDemo = new SingleInstanceDemo();
                }
            }
        }
        return singleInstanceDemo;
    }
}
