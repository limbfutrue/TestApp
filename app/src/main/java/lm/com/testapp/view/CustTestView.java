package lm.com.testapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import lm.com.testapp.R;

/**
 * Created by PSBC-26 on 2020/8/31.
 */

public class CustTestView extends View {

    private String text;
    private float fondSize;
    private Paint textPaint;
    private Rect boundText = new Rect();
    private int width;
    private int height;

    public CustTestView(Context context) {
        this(context, null);
    }


    public CustTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        textPaint = new Paint();
        textPaint.setAntiAlias(false);
        textPaint.setColor(Color.RED);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustTestView,0,0);

        try {
            text = a.getString(R.styleable.CustTestView_text);
            fondSize = a.getDimension(R.styleable.CustTestView_fondSize,0);

            textPaint.setTextSize(fondSize);

            textPaint.getTextBounds(text,0,text.length(),boundText);

        }finally {
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        width = 0;
        height = 0;
        if (widthSpec == MeasureSpec.EXACTLY){
            width = widthSize + getPaddingLeft() + getPaddingRight();
        } else if (widthSpec == MeasureSpec.AT_MOST){
            width = Math.min(widthSize, boundText.width()) + getPaddingLeft() + getPaddingRight();
        }


        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSpec == MeasureSpec.EXACTLY){
            height = heightSize;
        } else if (heightSpec == MeasureSpec.AT_MOST){
            height = Math.min(heightSize, boundText.height());
        }

        setMeasuredDimension(width,height);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
        requestLayout();
    }

    public float getFondSize() {
        return fondSize;
    }

    public void setFondSize(float fondSize) {
        this.fondSize = fondSize;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
