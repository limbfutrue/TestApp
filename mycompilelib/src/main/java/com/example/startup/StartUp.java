package com.example.startup;

import java.util.List;

import javax.naming.Context;

/**
 * Created by PSBC-26 on 2022/2/15.
 */

public interface StartUp<T> {
    T create(Context context);
    List<Class<? extends StartUp<?>>> dependencies();
    int getDependenciseCount();
}
