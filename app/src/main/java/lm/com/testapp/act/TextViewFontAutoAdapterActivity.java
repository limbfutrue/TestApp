package lm.com.testapp.act;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;

/**
 * 自适应字体
 */
public class TextViewFontAutoAdapterActivity extends BaseActivity {

    private TextView tvText;
    private TextView bt1;
    private TextView bt2;
    private StringBuffer sb = new StringBuffer();
    private final String TAG = "callback";

    int defaultTextSize = 23;


    @Override
    public int getLayoutId() {
        return R.layout.activity_font_adapter;
    }

    @Override
    public void initView() {
        super.initView();
        tvText = (TextView) findViewById(R.id.tvText);
        bt2 = (TextView) findViewById(R.id.bt2);
        sb.append("123");
        tvText.setText(sb.toString());
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sb.length() == 0){
                    return;
                }
                sb.append("6");
                tvText.setText(sb.toString());
                tvText.post(new Runnable() {
                    @Override
                    public void run() {
                        Layout layout = tvText.getLayout();
                        int elli = layout.getEllipsisCount(tvText.getLineCount()-1);
                        if (elli != 0){
                            tvText.setTextSize(defaultTextSize--);
                            Log.d(TAG, defaultTextSize + "onClick2: " + elli);
                        }
                        Log.d(TAG, "onClick2: " + elli);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
