package lm.com.testapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import lm.com.testapp.R;

/**
 * Created by Libaoming on 2019/12/30.
 */

public class FlowLayout extends ViewGroup {
    private int maxLine = 4;
    //行间距
    private int verSpacing = 10;
    //列间距
    private int horSpacing = 10;
    private OnFlowLayoutItemClickListener itemClickListener;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horSpacing = (int) ta.getDimension(R.styleable.FlowLayout_horSpacing,10);
        verSpacing = (int) ta.getDimension(R.styleable.FlowLayout_verSpacing,10);
        maxLine = ta.getInteger(R.styleable.FlowLayout_maxLine,4);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount > 0) {
            //总行高
            int totalH = getChildAt(0).getPaddingTop() + getChildAt(0).getMeasuredHeight() + getChildAt(0).getPaddingBottom()+ verSpacing;
            int width = 0;
            //行宽
            int totalChildWidth = 0;
            //行数
            int line = 1;
            int parentWidth = getMeasuredWidth();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //子view左上坐标点
                int childL = childView.getPaddingLeft() + childView.getMeasuredWidth() + childView.getPaddingRight() + horSpacing;
                //如果单个子View宽度大于父view宽度，则使子view宽度等于父view宽度
                if (childL >= parentWidth){
                    childL = parentWidth;
                }
                int childT = childView.getPaddingTop() + childView.getMeasuredHeight() + childView.getPaddingBottom() + verSpacing;
                totalChildWidth = childL + width;
//                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                //如果子view宽度 大于 父宽度，换行
                if (totalChildWidth > parentWidth) {
                    //重置宽初始值，重新计算
                    width = 0;
                    //累加行
                    line++;
                    //总行高
                    totalH += childT;
                }
                //限制显示行数
                if (line <= (maxLine == 1 ? 1 : (maxLine - 1))) {
                    measureChild(childView, childL, childT);
                    width = width + childL;
                } else {
                    break;
                }
            }
            setMeasuredDimension(widthMeasureSpec, totalH);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {
            int left = 0;
            int lastChildT = 0;
            int parentWidht = getMeasuredWidth();
            int totalChildWidth = 0;
            int totalChildHeight = getChildAt(0).getPaddingTop() + getChildAt(0).getMeasuredHeight() + getChildAt(0).getPaddingBottom()+verSpacing;
            int line = 1;
            for (int i = 0; i < getChildCount(); i++) {
                final View childView = getChildAt(i);
                int childL = childView.getPaddingLeft() + childView.getMeasuredWidth() + childView.getPaddingRight() + horSpacing;
                //如果单个子View宽度大于父view宽度，则使子view宽度等于父view宽度
                if (childL >= parentWidht){
                    childL = parentWidht;
                }
                int childT = childView.getPaddingTop() + childView.getMeasuredHeight() + childView.getPaddingBottom() + verSpacing;
                totalChildWidth = childL + left;
                if (totalChildWidth > parentWidht) {
                    if (i != 0) {
                        totalChildWidth = childL;
                        totalChildHeight += childT;
                        left = 0;
                        lastChildT = totalChildHeight - childT;
                        line++;
                    }
                }
                final int finalI = i;
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null){
                            itemClickListener.onItemClickListener(finalI);
                        }
                    }
                });
                if (line <= maxLine) {
                    childView.layout(left + horSpacing, lastChildT+verSpacing, totalChildWidth, totalChildHeight);
                    left = left + childL;
                } else {
                    break;
                }
            }
        }
    }

    public void setItemClickListener(OnFlowLayoutItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnFlowLayoutItemClickListener{
        void onItemClickListener(int positon);
    }
}
