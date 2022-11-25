package lm.com.testapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by PSBC-26 on 2021/5/7.
 */

public class ScrollTabView extends HorizontalScrollView{

    public ScrollTabView(Context context) {
        super(context);
    }

    public ScrollTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化变量
     *
     */
    private void init(){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
