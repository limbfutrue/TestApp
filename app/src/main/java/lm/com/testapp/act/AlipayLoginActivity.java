package lm.com.testapp.act;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.utils.alipay.AlipayLoginUtil;
import lm.com.testapp.utils.alipay.AuthResult;

public class AlipayLoginActivity extends BaseActivity {

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AlipayLoginUtil.SDK_AUTH_FLAG:

                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    tv1.setText(authResult.toString());
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Log.e("callback","成功" + authResult.toString());
//                        obtainUserId(authResult.getAuthCode());
                    } else {
                        // 其他状态值则为授权失败
                        Log.e("callback","失败" + authResult.toString());
                    }
                    tv3.setText(authResult.toString());
                    break;
                default:
                    break;
            }
        };
    };

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;

    @Override
    public int getLayoutId() {
        return R.layout.activity_alipay_login;
    }

    @Override
    public void initView() {
        super.initView();
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlipayLoginUtil.openAuthScheme(AlipayLoginActivity.this);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlipayLoginUtil.authV2(AlipayLoginActivity.this,mHandler);
            }
        });
    }
}
