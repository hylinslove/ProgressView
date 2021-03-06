package com.chinastis.progressviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyProgressView extends View {

    private int inColor;
    private int outColor;
    private float progress;

    private float width;
    private float height;

    private Paint paint;
    private float longTotal;

    public MyProgressView(Context context) {
        super(context);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray ta = context.obtainStyledAttributes(attrs, com.chinastis.progressviewdemo.R.styleable.MyProgressView);

        inColor = ta.getColor(com.chinastis.progressviewdemo.R.styleable.MyProgressView_in_color, Color.RED);
        outColor = ta.getColor(com.chinastis.progressviewdemo.R.styleable.MyProgressView_out_color, Color.BLUE);
        progress = ta.getInt(com.chinastis.progressviewdemo.R.styleable.MyProgressView_progress,0);

        ta.recycle();
        initPaint();
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 200;
        }

        if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 50;
        }
        setMeasuredDimension((int)width,(int)height);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {

        float r;
        float r2;

        r = (height-40) / 2;
        r2 = (height-50) /2;

        longTotal = width - 2*r-40;

        float radius =  progress/100;

        float longProgress = longTotal*radius;

        paint.setColor(outColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawArc(15,15,height-15,height-15,90,180,false,paint);

        canvas.drawArc(width-height+15,15,width-15,height-15,270,180,false,paint);


        paint.setStrokeWidth(10);
        canvas.drawLine(r+20,20,width-r-20,20,paint);

        canvas.drawLine(r+20,height-20,width-r-20,height-20,paint);

        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(height-50);
        canvas.drawArc(25,25,height-25,height-25,90,180,false,paint);

        canvas.drawArc(width-height+25,25,width-25,height-25,270,180,false,paint);
        canvas.drawLine(r+20,height/2,r+20+longTotal,height/2,paint);
        if(progress!=0) {
            paint.setColor(inColor);
            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawArc(25,25,height-25,height-25,90,180,false,paint);

            canvas.drawArc(25+longProgress,25,25+longProgress+r2+r2,height-25,270,180,false,paint);

            paint.setStrokeWidth(height-50);
            canvas.drawLine(r+20,height/2,r+20+longProgress+1,height/2,paint);
        }
    }

    public void updateProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}

