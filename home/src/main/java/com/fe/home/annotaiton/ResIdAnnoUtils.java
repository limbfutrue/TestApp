package com.fe.home.annotaiton;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by PSBC-26 on 2022/2/14.
 */

public class ResIdAnnoUtils {

    private static final String TAG = "ResIdAnnoUtils";

    public static void init(Activity act){
        //利用反射获取属性
        Class<? extends Activity> actClass = act.getClass();
        initFiled(act,actClass);
        initMethod(act,actClass);
    }

    /**
     * 初始化findViewById
     * @param act
     * @param actClass
     */
    private static void initFiled(Activity act, Class<? extends Activity> actClass) {
        //获取Activity所有成员变量属性
        Field[] actFields = actClass.getDeclaredFields();
        for (int i = 0; i < actFields.length; i++) {
            InitResId annotation = actFields[i].getAnnotation(InitResId.class);
            //如果annotation为空则代表未添加注解
            if (annotation != null) {
                try {
                    int resId = annotation.value();
                    // 方法1，反射findViewById方法获取实例
//                    Method findViewById = actClass.getMethod("findViewById", int.class);
//                    Object view = findViewById.invoke(act, resId);
                    // 直接通过activity的findViewById获取
                    View viewById = act.findViewById(resId);
                    actFields[i].setAccessible(true);
                    // 为属性设置值
                    actFields[i].set(act, viewById);
                } catch (Exception e) {
                    Log.d(TAG, "IllegalAccessException: ");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化控件点击监听
     * @param actClass
     */
    private static void initMethod(final Activity act, Class<? extends Activity> actClass) {
        try {
            final Method[] methods = actClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                // 获取每个方法的InitClickListener注解器
                InitClickListener annotation = methods[i].getAnnotation(InitClickListener.class);
                if (annotation != null){
                    //说明此方法添加了InitClickListener注解
                    int[] value = annotation.value();
                    for (int j = 0; j < value.length; j++) {
                        //获取注解器中的值
                        final View viewById = act.findViewById(value[j]);
                        if (viewById == null){
                            Log.d(TAG, "initMethod: 请检查view id 是否错误，请使用R.id.xxx");
                            continue;
                        }
                        final int finalI = i;
//                        方式1
                        viewById.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    methods[finalI].invoke(act,viewById);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        //方式二 动态代理方式
//                        // 获取View Class对象，然后获取View的setOnClickListener设置监听方法
//                        Class<? extends View> aClass = viewById.getClass();
//                        Method setOnClickListener = aClass.getMethod("setOnClickListener", View.OnClickListener.class);
//                        final int finalI = i;
//                        // 动态代理 OnClickListener 接口监听
//                        View.OnClickListener obj = (View.OnClickListener) Proxy.newProxyInstance(ResIdAnnoUtils.class.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
//                            @Override
//                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                                //调用注解器注解的监听方法
//                                return methods[finalI].invoke(act,viewById);
//                            }
//                        });
//                        设置监听
//                        setOnClickListener.invoke(viewById,obj);
                    }
                }
            }
        } catch (Exception e){
            Log.d(TAG, "initMethod: " + e.getMessage());
        }
    }
}
