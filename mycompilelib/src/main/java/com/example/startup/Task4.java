package com.example.startup;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;

/**
 * Created by PSBC-26 on 2022/2/15.
 */

public class Task4 extends AndroidStartUp<Void> {
    static List<Class<? extends StartUp<?>>> depends;
    static {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }
    @Override
    public Void create(Context context) {
        System.out.println("任务4开始执行-----------------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务4执行结束-----------------------------");
        return null;
    }

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return depends;
    }
}
