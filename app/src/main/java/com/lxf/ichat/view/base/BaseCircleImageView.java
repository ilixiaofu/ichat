package com.lxf.ichat.view.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.snowdream.android.widget.SmartImageView;
import com.lxf.ichat.R;

public class BaseCircleImageView extends SmartImageView {

    private int outCircleWidth;

    private int outCircleColor = Color.WHITE;

    private Paint paint;

    private int viewWidth;
    private int viewHeight;

    private Bitmap bitmap;


    public BaseCircleImageView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public BaseCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public BaseCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes( attrs, R.styleable.BaseCircleImageView);
            int len = typedArray.length();

            for (int i = 0; i<len; i++) {
                int attr = typedArray.getIndex(i);

                switch (attr) {
                    case R.styleable.BaseCircleImageView_outCircleColor:
                        this.outCircleColor = typedArray.getColor(attr, Color.WHITE);
                        break;

                    case  R.styleable.BaseCircleImageView_outCircleWidth:
                        this.outCircleWidth = (int) typedArray.getDimension(attr, 5);
                        break;
                }
            }
        }

        paint = new Paint();
        paint.setColor(outCircleColor);
        paint.setAntiAlias(true);

    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        viewWidth = width - outCircleWidth * 2;
        viewHeight = height - outCircleWidth * 2;
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        loadImage();
        if (bitmap != null) {
            int min = Math.min(viewHeight, viewWidth);
            int circleCenter = min/2;
            bitmap = Bitmap.createScaledBitmap(bitmap,min,min,false);
            canvas.drawCircle(circleCenter + outCircleWidth,circleCenter + outCircleWidth ,circleCenter + outCircleWidth,paint);
            canvas.drawBitmap(createBitmap(bitmap,min),outCircleWidth,outCircleWidth,null);
        }
    }

    private Bitmap createBitmap(Bitmap bitmap, int min) {
        Bitmap bitmap1 = null;

        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);

        bitmap1 = Bitmap.createBitmap(min,min,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        canvas.drawCircle(min/2,min/2,min/2,paint1);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint1);

        return bitmap1;
    }

    private void loadImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }

    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result =size;
        } else {
            result =viewWidth;
        }

        return  result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result =size;
        } else {
            result =viewHeight;
        }

        return  result;
    }
}
