package lm.com.testapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import lm.com.testapp.lazy.LazyActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    intentAct();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        super.initView();
        handler.sendEmptyMessageDelayed(0,1000);
    }

    /**
     * 跳转首页
     */
    private void intentAct() {
        Intent intentMain = new Intent(this,LazyActivity.class);
        startActivity(intentMain);
        finish();
    }
}
