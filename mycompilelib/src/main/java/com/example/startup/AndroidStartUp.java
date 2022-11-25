package com.example.startup;

import java.util.List;

/**
 * Created by PSBC-26 on 2022/2/15.
 */

public abstract class AndroidStartUp<T> implements StartUp<T> {

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciseCount() {
        List<Class<? extends StartUp<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }
}
