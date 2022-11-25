package test;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by PSBC-26 on 2021/11/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnEventType(listener = View.OnClickListener.class,setListener = "setOnClickListener")
public @interface OnClick {
    int[] value();
}
