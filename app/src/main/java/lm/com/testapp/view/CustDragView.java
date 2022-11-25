package lm.com.testapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by PSBC-26 on 2021/4/21.
 */

public class CustDragView extends FrameLayout{
    private TextView textView;

    private PointF initPoint;
    private PointF movePoint = new PointF();
    private boolean isClick =false;

    public CustDragView(Context context) {
        super(context);
        init();
    }

    public CustDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustDragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        initPoint = new PointF(100,100);
        textView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setBackgroundColor(Color.GREEN);
        textView.setPadding(10,10,10,10);
        textView.setText("100");
        textView.setLayoutParams(layoutParams);
        addView(textView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        if (isClick){
            textView.setX(movePoint.x - textView.getWidth()/2);
            textView.setY(movePoint.y - textView.getHeight()/2);
        } else {
            textView.setX(initPoint.x - textView.getWidth()/2);
            textView.setY(initPoint.y - textView.getHeight()/2);
        }

        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                movePoint.set(initPoint.x,initPoint.y);
                Rect rect = new Rect();
                int[] textViewLocation = new int[2];
                textView.getLocationOnScreen(textViewLocation);
                rect.left = textViewLocation[0];
                rect.top = textViewLocation[1];
                rect.right = rect.left + textView.getWidth();
                rect.bottom = rect.top + textView.getHeight();

                if (rect.contains((int)event.getRawX(),(int)event.getRawY())){
                    isClick = true;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                movePoint.set(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                isClick = false;
                break;
        }
        postInvalidate();
        return true;
    }

}
