package com.fe.home.annotaiton;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by PSBC-26 on 2022/2/14.
 *
 * Activity 属性 findViewById注解
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitResId {
    @IdRes
    int value();
}
