package com.example.gobang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ChessBoardView extends View implements View.OnTouchListener {

    private static final int BLOCK_WIDTH = 45, BLOCK_HEIGHT = 45;

    private final Paint paint;
    private int row, col;
    private int width, height;

    public ChessBoardView(Context context) {
        this(context, null, 0);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChessBoardView);
        row = ta.getInteger(R.styleable.ChessBoardView_row, 15);
        col = ta.getInteger(R.styleable.ChessBoardView_col, 15);
        ta.recycle();
        //initialize
        paint = new Paint();
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = BLOCK_WIDTH * col;
        height = BLOCK_HEIGHT * row;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX(), y = motionEvent.getY();
        if (x < 0 || y < 0 || x >= width || y >= height)
            return true;
        Log.d("haha", x+","+y);
        return true;
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawText();
//    }
}
