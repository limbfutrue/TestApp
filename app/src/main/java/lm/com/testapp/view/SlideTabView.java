package lm.com.testapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lm.com.testapp.R;

/**
 * 注释：顶部滑动标签栏
 */
public class SlideTabView extends HorizontalScrollView {
    //标签布局容器
    private LinearLayout linearLayout;
    //指示器画笔
    private Paint paint;
    //tab容器
    private List<String> list;
    //text容器
    private List<TextView> textViews;
    //当前位置
    private int currIndex = 0;
    //满屏显示数量
    private float maxCount = 4f;
    //偏移百分比
    private float offSet;
    //非选中字体颜色
    private int noCurrColor;
    //选中字体颜色
    private int currColor;
    //指示器颜色
    private int offLineColor;
    //背景色
    private int background = R.color.white;
    //分割线颜色
    private int splitLineColor;
    //字体大小
    private int textSize;
    //上下文
    private Context context;
    private int screenWidth;
    private boolean isEdit;

    public SlideTabView(Context context) {
        this(context, null);
    }

    public SlideTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideTabView, defStyleAttr, 0);
        textSize = a.getInteger(R.styleable.SlideTabView_tabTextSize, 18);
        offLineColor = a.getColor(R.styleable.SlideTabView_offLineColor, Color.BLACK);
        splitLineColor = a.getColor(R.styleable.SlideTabView_splitLineColor, Color.WHITE);
        noCurrColor = a.getColor(R.styleable.SlideTabView_noCurrColor, Color.GRAY);
        currColor = a.getColor(R.styleable.SlideTabView_currColor, Color.BLACK);
        a.recycle();
        init();
    }

    //初始化View
    private void init() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        textViews = new ArrayList<>();

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        setBackgroundResource(background);
        addView(linearLayout);
    }

    //初始化tab数据
    public void initTab(List<String> list) {
        this.list = list;
        addTab();
    }

    //添加tab 默认选中第一个
    private void addTab() {
        textViews.clear();
        linearLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TextView textView = new TextView(context);
            if (i == 0) {
                textView.setTextColor(currColor);
            } else {
                textView.setTextColor(noCurrColor);
            }
            int pd = getResources().getDimensionPixelSize(R.dimen.dip_12);
            int pd2 = getResources().getDimensionPixelSize(R.dimen.dip_15);
            textView.setPadding(pd2, pd, pd2, pd);//设置字体padding
//            layoutParams.width = (int) (screenWidth / maxCount);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(textSize);
            textView.setText(list.get(i));
            textView.setSingleLine(true);

            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编辑状态下第一个tab无法选中
                    if (isEdit && finalI == 0) {
                        return;
                    }
                    if (currIndex != finalI) { //点击的不是当前tab
                        if (onTabChangeListern != null) {
                            onTabChangeListern.change(finalI);
                        }
                        scrollToChild(finalI);
                    }
                }
            });
            textViews.add(textView);
            linearLayout.addView(textView);
            if (i == 0) {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        }
        invalidate();
    }

    private void changeTextStyle(View v) {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            if (v == textViews.get(i)) {
                textViews.get(i).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        }
    }

    private void refresh(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i != index) {
                textViews.get(i).setTextColor(noCurrColor);
            } else {
                textViews.get(i).setTextColor(currColor);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h = getHeight();
        float w = getWidth();
        paint.setColor(splitLineColor);
        canvas.drawRect(0, h - 2, w, h, paint);//底部分割线

        paint.setColor(offLineColor);
        View view = linearLayout.getChildAt(currIndex);

        if (view == null) {
            return;
        }
        float lineLeft = view.getLeft();
        float lineRight = view.getRight();

        if (offSet > 0f) {
            View nextTab = linearLayout.getChildAt(currIndex + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (offSet * nextTabLeft + (1f - offSet) * lineLeft);
            lineRight = (offSet * nextTabRight + (1f - offSet) * lineRight);
        }
        float linePx = dip2px(context, 30);//底部标示线最长30dp
        float tabPx = lineRight - lineLeft;//tab的宽
        if (tabPx < linePx) {
            canvas.drawRect(lineLeft, h - 6, lineRight, h, paint);
        } else {
            float pd = (tabPx - linePx) / 2f;
            canvas.drawRect(lineLeft + pd, h - 6, lineRight - pd, h, paint);
        }
//        canvas.drawRect(lineLeft+30, h - 6, lineRight-30, h, paint);//标示线的左右间距30px
//        canvas.drawRect(lineLeft, h - 6, lineRight, h, paint);//标示线和此tab宽度一致
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public void scrollToChild(int position) {
        changeTextStyle(textViews.get(position));
        if (currIndex == position || position < 0 || position >= list.size() || (isEdit && position == 0)) {
            return;
        }
        currIndex = position;
        refresh(currIndex);
        invalidate();
        int width = linearLayout.getChildAt(position).getMeasuredWidth();
        int left = linearLayout.getChildAt(position).getLeft();
        int toX = left + width / 2 - screenWidth / 2;
        smoothScrollTo(toX, 0);
    }

    private OnTabChangeListern onTabChangeListern;

    public void setOnTabChangeListern(OnTabChangeListern onTabChangeListern) {
        this.onTabChangeListern = onTabChangeListern;
    }

    public interface OnTabChangeListern {
        void change(int postion);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public void setEditState(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public int getItemCount() {
        return (list == null ? 0 : list.size());
    }

    public TextView getItem(int postion) {
        if (textViews == null || textViews.size() <= postion) {
            return null;
        }
        return textViews.get(postion);
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
