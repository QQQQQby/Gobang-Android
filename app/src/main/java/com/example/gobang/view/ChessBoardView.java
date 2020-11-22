package com.example.gobang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.gobang.R;
import com.example.gobang.model.Piece;

public class ChessBoardView extends View implements View.OnTouchListener {

    private static final int DEFAULT_BLOCK_WIDTH = 50, DEFAULT_BLOCK_HEIGHT = 50;
    private static final int DEFAULT_PIECE_SIZE = 20;
    private static final int DEFAULT_ROW = 15, DEFAULT_COLUMN = 15;
    private static final int DEFAULT_LINE_WIDTH = 5;

    private final Paint paint;
    private int blockWidth, blockHeight;
    private int row, col;
    private int lineWidth;
    private int piece_size;
    private int width, height;
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;
    Piece[][] board;

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

        board = new Piece[row][col];
//        setPiece(5, 5, Piece.BLACK);
//        setPiece(5, 6, Piece.WHITE);
//        setPiece(6, 6, Piece.BLACK);
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
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.d("haha", "UP");
            case MotionEvent.ACTION_DOWN:
                Log.d("haha", "DOWN");
        }
        if (x < 0 || y < 0 || x >= width || y >= height)
            return true;
        Log.d("haha", x + "," + y);
        this.postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the board.
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.BLACK);
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

        // Draw pieces.
        paint.setStrokeWidth(1);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == null)
                    continue;
                switch (board[i][j]) {
                    case BLACK:
                        paint.setColor(Color.BLACK);
                        break;
                    case WHITE:
                        paint.setColor(Color.WHITE);
                        break;
                }
                canvas.drawCircle(paddingLeft + j * blockWidth, paddingTop + i * blockHeight, piece_size, paint);
            }
        }

    }

    public void setPiece(int i, int j, Piece piece) {
        board[i][j] = piece;
        this.postInvalidate();
    }

}
