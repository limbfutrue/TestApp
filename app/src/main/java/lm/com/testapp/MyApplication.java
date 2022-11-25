package lm.com.testapp;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;


/**
 * Created by PSBC-26 on 2020/8/6.
 */

public class MyApplication extends Application{
    public static MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //初始化腾讯TBS
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d("callback", "onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //加载x5内核成功返回值为true
                Log.d("callback", "onViewInitFinished: "+b);
            }
        });
    }
}
