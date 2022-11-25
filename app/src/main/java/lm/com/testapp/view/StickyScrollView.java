package lm.com.testapp.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by PSBC-26 on 2021/3/3.
 */

public class StickyScrollView extends ScrollView {
    public static final int PAGE_TOP = 0;
    public static final int PAGE_BOTTOM = 1;
    public static final double PERCENT = 1.4;
    public static final int ANIMATION_DURATION = 180;
    public static final int TOUCH_DURATION = 150;

    private ViewGroup mChildLayout;
    private View mTopChildView;

    private Context mContext;
    private OnPageChangeListener onPageChangeListener;

    private boolean isScrollAuto;
    private Scroller mScroller;
    private int screenHeight;
    private int offsetDistance;
    private int topChildHeight;
    private boolean isTouch;
    private int currentPage;
    private long downTime;
    private long upTime;
    private int downY;
    private int upY;
    private boolean isPageChange;
    private int changePageDistance = 300;
    /**
     * 是否是手指滑动触发的显示二楼页面
     */
    private boolean isScrollToTopPage = false;
    private View mBottomChildView;
    private int bottomChildHeight;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideTwoPage();
        }
    };
    public StickyScrollView(Context context) {
        super(context);
        this.mContext = context;
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mChildLayout = (ViewGroup) getChildAt(0);
        mTopChildView = mChildLayout.getChildAt(0);
        topChildHeight = mTopChildView.getMeasuredHeight();
        mBottomChildView = mChildLayout.getChildAt(1);
        bottomChildHeight = mBottomChildView.getMeasuredHeight();
        screenHeight = getMeasuredHeight();
        mBottomChildView.setMinimumHeight(screenHeight);
        mTopChildView.setMinimumHeight(screenHeight);
        offsetDistance = topChildHeight - screenHeight;
        //防止手动滑动到顶部二楼页面时，刷新布局重新走onLayout,造成再次滚动到一楼主页页面
        if (!isScrollToTopPage){
            isScrollToTopPage = true;
            initScrollBottomPage();
        }
    }

    /**
     * 默认开始显示第二页
     */
    public void initScrollBottomPage(){
        isPageChange = true;
        mScroller = new Scroller(mContext);
        currentPage = PAGE_BOTTOM;
        int delta = getScrollY() - topChildHeight;
        mScroller.startScroll(0,0,0,-delta,0);
        postInvalidate();
    }

    /**
     * 显示第一页
     */
    public void initScrollTopPage(){
        mScroller = new Scroller(mContext);
        currentPage = PAGE_TOP;
        int delta = getScrollY() - offsetDistance;
        mScroller.startScroll(0, 0, 0, -delta, isScrollAuto == true ? ANIMATION_DURATION : (int)(Math.abs(delta) * 0.4));
        isPageChange = true;
        postInvalidate();
    }

    @Override
    public void fling(int velocityY) {
        if (getScrollY() < topChildHeight && getScrollY() > topChildHeight -changePageDistance){
            return;
        } else {
            super.fling(velocityY/4);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                downY = (int)ev.getY();
                downTime = System.currentTimeMillis();
                if (mScroller != null){
                    mScroller.forceFinished(true);
                    mScroller = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                upY = (int)ev.getY();
                upTime = System.currentTimeMillis();
                //用户手指在屏幕上的时间
                long duration = upTime - downTime;
                //这里要确保点击时间不失效
                if (Math.abs(upY - downY) > 50){
                    if (currentPage == PAGE_TOP){
                        //回到第二页
                        if (offsetDistance < 0){
                            break;
                        }
                        if (getScrollY() >= offsetDistance + changePageDistance){
                            isScrollAuto = duration < TOUCH_DURATION ? true : false;
                            initScrollBottomPage();
                            return false;
                        } else if (getScrollY() > offsetDistance && getScrollY() < offsetDistance + changePageDistance){
                            isPageChange = false;
                            mScroller = new Scroller(mContext);
                            scrollToTarget(PAGE_TOP);
                        }


                    } else if (currentPage == PAGE_BOTTOM){
                        //回到副页
                        if (getScrollY() < topChildHeight - changePageDistance){

                            TranslateAnimation anim = new TranslateAnimation(0, 0,-topChildHeight + upY - downY,0);
                            anim.setDuration(300);
                            mChildLayout.startAnimation(anim);

                            TranslateAnimation anim2 = new TranslateAnimation(0, 0,0,bottomChildHeight);
                            anim2.setDuration(300);
                            mBottomChildView.startAnimation(anim2);

                            handler.sendEmptyMessageDelayed(0,300);
                            isScrollToTopPage = true;
                            isScrollAuto = duration < TOUCH_DURATION ? true : false;
                            initScrollTopPage();
                            return false;
                        } else if (getScrollY() < topChildHeight && getScrollY() > topChildHeight -changePageDistance){
                            //停留在主页
                            isPageChange = false;
                            mScroller = new Scroller(mContext);
                            scrollToTarget(PAGE_BOTTOM);
                        }
                    }

                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 隐藏主页
     */
    public void hideTwoPage(){
        mChildLayout.getChildAt(1).setVisibility(GONE);
    }

    /**
     * 显示主页
     */
    public void showTwoPage(){
        mChildLayout.getChildAt(1).setVisibility(VISIBLE);
        initScrollBottomPage();
    }

    /**
     * 滚动到指定页面
     * @param currentPage
     */
    private void scrollToTarget(int currentPage){
        int delta;
        if (currentPage == PAGE_TOP){
            delta = getScrollY() - offsetDistance;
            mScroller.startScroll(0,0,0,-delta,isScrollAuto == true ? ANIMATION_DURATION : (int)(Math.abs(delta) * 0.4));
        } else if(currentPage == PAGE_BOTTOM){
            delta = getScrollY() - topChildHeight;
            mScroller.startScroll(0,getScrollY(),0,-delta,isScrollAuto == true ? ANIMATION_DURATION : (int)(Math.abs(delta) * 0.4));
        }
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller == null){
            return;
        }
        if (mScroller.computeScrollOffset()){
            this.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished()){
                mScroller = null;
                if (onPageChangeListener != null && isPageChange){
                    onPageChangeListener.onPageChange(currentPage);
                }
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (currentPage == PAGE_TOP){
            if (getScrollY() > offsetDistance && !isTouch){
                if (mScroller == null){
                    //用于控制当滑动到分界线时停止滚动
                    scrollTo(0,offsetDistance);
                } else {
                    scrollToTarget(PAGE_TOP);
                }
            }
        } else if (currentPage == PAGE_BOTTOM){
            if (getScrollY() < topChildHeight && !isTouch){
                if (mScroller == null){
                    scrollTo(0,topChildHeight);
                } else {
                    scrollToTarget(PAGE_BOTTOM);
                }
            }
        }
    }

    public interface OnPageChangeListener{
        void onPageChange(int currentPage);
    }
}
