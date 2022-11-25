package com.example.proxy.realobject;

import com.example.proxy.ITest;

/**
 * Created by PSBC-26 on 2021/11/18.
 */

public class MyTest2 implements ITest {
    @Override
    public void test(String name) {
        System.out.println("执行-------MyTest2" + name);
    }
}
