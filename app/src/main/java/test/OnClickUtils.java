package test;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by PSBC-26 on 2021/11/18.
 */

public class OnClickUtils {
    public static void initOnClick(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        //获取所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历方法
        for (Method method : declaredMethods) {
            //获取方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(OnEventType.class)){
                    //获取注解
                    OnEventType onEventType = annotationType.getAnnotation(OnEventType.class);
                    Class listener = onEventType.listener();
                    String setOnClickListener = onEventType.setListener();
                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);
                        method.setAccessible(true);
                        ListenerInvocationHandler<Activity> listenerInvocationHandler = new ListenerInvocationHandler<>(activity,method);
                        Object o = Proxy.newProxyInstance(OnClickUtils.class.getClassLoader(), new Class[]{listener}, listenerInvocationHandler);
                        for (int viewId : viewIds) {
                            View viewById = activity.findViewById(viewId);
                            //获取view的setOnClickListener方法
                            Method setter = viewById.getClass().getMethod(setOnClickListener, listener);
                            //执行setOnClickListener方法
                            setter.invoke(viewById,o);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
