package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by PSBC-26 on 2021/11/18.
 */

public class ListenerInvocationHandler<T> implements InvocationHandler {
    private T target;
    private Method method;
    public ListenerInvocationHandler(T target,Method method){
        this.target = target;
        this.method = method;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.method.invoke(target,args);
    }
}
