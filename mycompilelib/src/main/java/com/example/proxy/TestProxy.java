package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by PSBC-26 on 2021/11/18.
 */

public class TestProxy {
    public static void create(final ITest iTest){
        Object o = Proxy.newProxyInstance(TestProxy.class.getClassLoader(), new Class[]{ITest.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return method.invoke(iTest,objects);
            }
        });
        ITest it = (ITest) o;
        it.test("1234");
    }
}
