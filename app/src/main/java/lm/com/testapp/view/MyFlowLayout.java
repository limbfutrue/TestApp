package lm.com.testapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PSBC-26 on 2021/11/29.
 */

public class MyFlowLayout extends ViewGroup {

    private Scroller mScroller;
    //每行的子view数量
    private List<View> lineViews;
    //所有行
    private List<List<View>> lines;
    //每行最高行高集合
    private List<Integer> heights;
    //上次手指滑动坐标Y
    private float mLastY;

    private int measureHeight = 0;
    private int realHeight = 0;

    public MyFlowLayout(Context context) {
        this(context,null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }
    private void init(){
        lineViews = new ArrayList<>();
        lines = new ArrayList<>();
        heights = new ArrayList<>();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //每行的行宽
        int lineWidth = 0;
        //每行最大行高
        int lineHeight = 0;
        //每行的最大高度和
        int totalHeight = 0;
        //所有行中的宽的最大宽
        int totalWidth = 0;
        init();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (lineWidth + childWidth > widthSize){
                lines.add(lineViews);
                lineViews = new ArrayList<>();
                //取出最大宽度
                totalWidth = Math.max(totalWidth,lineWidth);
                heights.add(lineHeight);
                //计算总高度
                totalHeight += lineHeight;
                lineWidth = 0;
                lineHeight = 0;
            }
            //当前View的宽度
            lineWidth += childWidth;
            lineViews.add(child);
            //当前行的最大的高度
//            if (heightMode != MeasureSpec.EXACTLY) {
//                lineHeight = Math.max(childHeight, lineHeight);
//            }
            lineHeight = Math.max(childHeight, lineHeight);
            if(i == childCount - 1){
                lines.add(lineViews);
                heights.add(lineHeight);
                totalWidth = Math.max(totalWidth,lineWidth);
                totalHeight += lineHeight;
            }
        }
        realHeight = totalHeight;
        measureHeight = heightSize;
        reMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : totalWidth,totalHeight);
    }

    private void reMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = currY;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - currY;//手指滑动的距离
                int oldScrollY = getScrollY();//已经滑动距离的偏移量
                //当前滑动的距离
                int scrollY = (int) (dy + oldScrollY);
//                //处理上边界
//                if (scrollY < 0){
//                    scrollY = 0;
//                }
//                //处理下边界
//                if (scrollY > realHeight - measureHeight){
//                    scrollY = realHeight - measureHeight;
//                }
//                scrollTo(0,scrollY);

                mScroller.startScroll(0,mScroller.getFinalY(),0, (int) dy);
                invalidate();
                mLastY = currY;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        int currY = mScroller.getCurrY();
        if (currY < 0){
            currY = 0;
        }

        if (currY > realHeight - measureHeight){
            currY = realHeight - measureHeight;
        }
        if (mScroller.computeScrollOffset()){//是否滑动结束
            scrollTo(0,currY);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childSize = lines.size();
        int currX = 0;
        int currY = 0;
        for (int i = 0; i < childSize; i++) {
            //取出每行的子view
            List<View> lineChildViews = lines.get(i);
            int lineHeight = heights.get(i);

            int size = lineChildViews.size();
            for (int j = 0; j < size; j++) {
                View childView = lineChildViews.get(j);
                int left = currX;
                int top = currY;
                int right = left + childView.getMeasuredWidth();
                int bottom = top + childView.getMeasuredHeight();

                childView.layout(left,top,right,bottom);
                currX += childView.getMeasuredWidth();
            }
            currY += lineHeight;
            currX = 0;
        }
    }
}
