package com.example.startup;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;

/**
 * Created by PSBC-26 on 2022/2/15.
 */

public class Task5 extends AndroidStartUp<Void> {
    static List<Class<? extends StartUp<?>>> depends;
    static {
        depends = new ArrayList<>();
        depends.add(Task3.class);
        depends.add(Task4.class);
    }
    @Override
    public Void create(Context context) {
        System.out.println("任务5开始执行-----------------------------");
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务5执行结束-----------------------------");
        return null;
    }

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return depends;
    }
}
