package com.example.startup;

import java.util.List;

import javax.naming.Context;

/**
 * Created by PSBC-26 on 2022/2/15.
 */

public class Task extends AndroidStartUp<Void>{


    @Override
    public Void create(Context context) {
        System.out.println("任务1开始执行-----------------------------");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务1执行结束-----------------------------");
        return null;
    }

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return super.dependencies();
    }
}
