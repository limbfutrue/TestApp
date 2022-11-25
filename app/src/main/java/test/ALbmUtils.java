package test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by PSBC-26 on 2021/11/17.
 */

public class ALbmUtils {
    public static void init(Activity activity){
        Class<? extends Activity> acls = activity.getClass();
        Field[] declaredFields = acls.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(ALbm.class)){
                Intent intent = activity.getIntent();
                Bundle extras = intent.getExtras();
                if (extras != null){
                    ALbm annotation = field.getAnnotation(ALbm.class);
                    String key = TextUtils.isEmpty(annotation.value()) ? field.getName() : annotation.value();
                    if (extras.containsKey(key)){
                        Object obj = extras.get(key);
                        //获取数据单个元素类型
                        Class<?> componentType = field.getType().getComponentType();
                        //当前属性是数组并且是Parcelable（子类）数组
                        if(field.getType().isArray() && Parcelable.class.isAssignableFrom(componentType)){
                            Object[] objs = (Object[]) obj;
                            //创建对应类型的数组并由objs拷贝
                            Object[] objects = Arrays.copyOf(objs,objs.length, (Class<? extends Object[]>) field.getType());
                            obj = objects;
                        }
                        field.setAccessible(true);
                        try {
                            field.set(activity,obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
