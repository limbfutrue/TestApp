package lm.com.testapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by PSBC-26 on 2021/3/8.
 */

public class MyViewPager extends ViewPager {
    private final String TAG = "callback";

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int oneHeight;//记录第一页的高度
    private int twoHeight;//记录第二页的高度
    private int threeHeight;//记录第三页的高度
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            int currentItem = getCurrentItem();
//            View child = null;
//            if (currentItem == 2){
//                child = getChildAt(i);
//            } else {
//                child = getChildAt(currentItem);
//            }
//            try {
//                child.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
//                int h = child.getMeasuredHeight();
//                if (h > height){
//                    height = h;
//                }
//            } catch (Exception e){
//                Log.d(TAG, "onMeasure-Exception: " + currentItem);
//            }
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        View child = null;
        int currentItem = getCurrentItem();

        //如果第一页高度有值，并且当前显示的为第一页，则使用第一次加载计算第一页的缓存记录的viewpage高度
        if ((currentItem ==0 && oneHeight != 0)){
            child = getChildAt(currentItem);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(oneHeight, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        } else if ((currentItem ==1 && twoHeight != 0)){
            //如果第二页高度有值，并且当前显示的为第二页，则使用第一次加载计算第二页的缓存记录的viewpage高度
            child = getChildAt(currentItem);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(twoHeight, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        } else if ((currentItem ==2 && threeHeight != 0)){
            //如果第三页高度有值，并且当前显示的为第三页，则使用第一次加载计算第三页的缓存记录的viewpage高度
            child = getChildAt(1);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(threeHeight, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        //viewpage加载时 会缓存加载左右两个页面，如果显示的时第一页，则会缓存加载第二页并且这时获取的getChildCount()是2
        //getChildCount() 获取的数量为加载的当前页加上左右缓存的数量。例如如果只有三页，则在显示第一页时获取的getChildCount()=2
        //在显示第二页时获取的getChildCount()=3，在显示第最后一页页时获取的getChildCount()=2
        if (currentItem == 2) {
            //如上注释，则当滑动切换到最后一页时getChildCount()=2 这时再用child = getChildAt(currentItem); 则会空指针找不到child。所以默认写死最后一页
            child = getChildAt(1);
        } else {
            child = getChildAt(currentItem);
        }
        try {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            switch (getCurrentItem()){
                case 0:
                    oneHeight = child.getMeasuredHeight();
                    height = oneHeight;
                    break;
                case 1:
                    twoHeight = child.getMeasuredHeight();
                    height = twoHeight;
                    break;
                case 2:
                    threeHeight = child.getMeasuredHeight();
                    height = threeHeight;
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, "onMeasure-Exception: " + currentItem);
        }
        Log.d(TAG, "onMeasure-height: " + height);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
