package com.example.gobang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ChessBoardView extends View implements View.OnTouchListener {

    private static final int DEFAULT_BLOCK_WIDTH = 50, DEFAULT_BLOCK_HEIGHT = 50;
    private static final int DEFAULT_PIECE_SIZE = 30;
    private static final int DEFAULT_ROW = 15, DEFAULT_COLUMN = 15;
    private static final int DEFAULT_LINE_WIDTH = 5;

    private final Paint paint;
    private int blockWidth, blockHeight;
    private int row, col;
    private int lineWidth;
    private int piece_size;
    private int width, height;
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;
    List<>

    public ChessBoardView(Context context) {
        this(context, null, 0);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChessBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChessBoardView);
        row = ta.getInteger(R.styleable.ChessBoardView_row, DEFAULT_ROW);
        col = ta.getInteger(R.styleable.ChessBoardView_col, DEFAULT_COLUMN);
        blockWidth = ta.getInteger(R.styleable.ChessBoardView_block_width, DEFAULT_BLOCK_WIDTH);
        blockHeight = ta.getInteger(R.styleable.ChessBoardView_block_height, DEFAULT_BLOCK_HEIGHT);
        lineWidth = ta.getInteger(R.styleable.ChessBoardView_line_width, DEFAULT_LINE_WIDTH);
        piece_size = ta.getInteger(R.styleable.ChessBoardView_piece_size, DEFAULT_PIECE_SIZE);
        ta.recycle();

        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();

        paint = new Paint();
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = blockWidth * (col - 1) + paddingLeft + paddingRight;
        height = blockHeight * (row - 1) + paddingTop + paddingBottom;
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX(), y = motionEvent.getY();
        if (x < 0 || y < 0 || x >= width || y >= height)
            return true;
        Log.d("haha", x + "," + y);
        this.postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(lineWidth);
        int x = paddingLeft;
        for (int i = 0; i < col; i++) {
            canvas.drawLine(x, paddingTop, x, height - paddingBottom, paint);
            x += blockWidth;
        }
        int y = paddingTop;
        for (int i = 0; i < row; i++) {
            canvas.drawLine(paddingLeft, y, width - paddingRight, y, paint);
            y += blockHeight;
        }

        paint.setStrokeWidth(1);

    }


}
