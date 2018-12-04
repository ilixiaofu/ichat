package com.lxf.ichat.view.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lxf.ichat.R;

public class CustomTextView extends View {

    private String text;
    private int text_color;
    private int text_size;
    private Paint paint;

    //在代码里面new的时候调用
    public CustomTextView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    //在布局layout中使用(调用)
    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    //在布局layout中使用(调用)，但是会有style
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CostomTextView);
        text = array.getString(R.styleable.CostomTextView_text);
        text_color = array.getColor(R.styleable.CostomTextView_text_color, text_color);
        text_size = array.getDimensionPixelSize(R.styleable.CostomTextView_text_size, text_size);
        //  属性回收
        array.recycle();
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 方法用于测量计算自己的宽高，前提是继承自View，如果是继承自系统已有的 TextView , Button ,已经给你计算好了宽高
     * 1、MeasureSpec.AT_MOST : 在布局中指定了wrap_content
     * 2、MeasureSpec.EXACTLY : 在布居中指定了确切的值 100dp match_parent fill_parent
     * 3、MeasureSpec.UNSPECIFIED : 尽可能的大,很少能用到，ListView , ScrollView 在测量子布局的时候会用UNSPECIFIED
     * @Date        2018/12/2
     * @MethdName   onMeasure
     * @Param       [widthMeasureSpec, heightMeasureSpec]
     * @return      void
     * </pre>
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode =  MeasureSpec.getMode(widthMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST){

            Rect bounds = new Rect();
            paint.getTextBounds(text,0,text.length(),bounds);
            width = bounds.width() + getPaddingLeft() +getPaddingRight();
        }

        int heightMode =  MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            paint.getTextBounds(text,0,text.length(),bounds);
            height = bounds.height() + getPaddingTop() +getPaddingBottom();
        }

        // 设置控件的宽高
        setMeasuredDimension(width,height);
    }

    /**
     * <pre>
     * @Author      lxf
     * @Description 用于绘制自己的显示
     * @Date        2018/12/2
     * @MethdName   onDraw
     * @Param       [canvas]
     * @return      void
     * </pre>
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getPaddingLeft();

        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = getHeight()/2 + dy;

        canvas.drawText(text,x,baseLine,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
