package lm.com.testapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by PSBC-26 on 2021/6/25.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0及以上版本
            updateStatusColor();
        }
        initView();
        initAction();
        initData();
    }

    public abstract int getLayoutId();

    public void initView() {

    }

    public void initAction() {

    }

    public void initData() {

    }

    protected void updateStatusColor(){
        getWindow().setStatusBarColor(Color.WHITE);
        //状态栏修改为深色字体
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
