package com.example.proxy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    private static Object object1 = new Object();
    private static Object object2 = new Object();
    private static Lock Lock1 = new ReentrantLock();
    private static Lock Lock2 = new ReentrantLock();
    public static void main(String[] args){
        System.out.println("----------------");
        new Thread(new MyRunnable2()).start();
        new Thread(new MyRunnable1()).start();
        System.out.println("----------------");
        new Thread(new MyRun()).start();
//        TestProxy.create(new MyTest2());
    }

    static class MyRun implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("21112----------------" + i);
                i++;
                System.out.println("----------------" + i);
            }

        }
    }


    private static void test3(String threadName) throws InterruptedException{
        while (true){
            try {
                if (Lock1.tryLock()){
                    System.out.println(threadName + "----- Lock1");
                    try {
                        if (Lock2.tryLock()){
                            System.out.println(threadName + "----- Lock2");
                            break;
                        }
                    } finally {
                        Lock2.unlock();
                    }
                }
            } finally {
                Lock1.unlock();
            }
            Thread.sleep(3);
        }
    }

    private static void test4(String threadName) throws InterruptedException{
        while (true){
            try {
                if (Lock1.tryLock()){
                    System.out.println(threadName + "----- Lock1");
                    try {
                        if (Lock2.tryLock()){
                            System.out.println(threadName + "----- Lock2");
                            break;
                        }
                    } finally {
                        Lock2.unlock();
                    }
                }
            } finally {
                Lock1.unlock();
            }
            Thread.sleep(3);
        }
    }

    private static void test1() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (object1) {
            System.out.println(threadName + "----- object1");
            Thread.sleep(100);
            synchronized (object2){
                System.out.println(threadName + "----- object2");
            }
        }
    }

    private static void test2() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (object2) {
            System.out.println(threadName + "----- object2");
            Thread.sleep(100);
            synchronized (object1){
                System.out.println(threadName + "----- object1");
            }
        }
    }

    static class MyRunnable2 implements Runnable{

        @Override
        public void run() {
            try {
                String threadName = Thread.currentThread().getName();
                test4(threadName);
//                test2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyRunnable1 implements Runnable{

        @Override
        public void run() {
            try {
                String threadName = Thread.currentThread().getName();
                test3(threadName);
//                test1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
