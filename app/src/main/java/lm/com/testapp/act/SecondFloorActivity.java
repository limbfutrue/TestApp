package lm.com.testapp.act;

import android.os.Bundle;
import android.widget.Toast;

import lm.com.testapp.BaseActivity;
import lm.com.testapp.R;
import lm.com.testapp.view.PullLoadMoreView;

public class SecondFloorActivity extends BaseActivity {

    private PullLoadMoreView pullLoadMoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_floor);

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        pullLoadMoreView = (PullLoadMoreView) findViewById(R.id.pullLoadMoreView);

        //添加头部布局
        pullLoadMoreView.addHeadView(R.layout.top_layout);
        //添加监听open/close
        pullLoadMoreView.setViewStateListener(new PullLoadMoreView.ViewStateListener() {
            @Override
            public void onViewState(PullLoadMoreView.VIewState viewState) {
                if (viewState == PullLoadMoreView.VIewState.OPEN) {
                    Toast.makeText(SecondFloorActivity.this, "Open", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondFloorActivity.this, "Close", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
