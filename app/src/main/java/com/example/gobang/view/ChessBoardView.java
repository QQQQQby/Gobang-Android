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

    private static final int DEFAULT_ROW = 15, DEFAULT_COLUMN = 15;
    private static final int DEFAULT_BLOCK_WIDTH = 60, DEFAULT_BLOCK_HEIGHT = 60;
    private static final int DEFAULT_PIECE_SIZE = 25, DEFAULT_LINE_WIDTH = 5;

    private final Paint paint;
    private final int blockWidth, blockHeight;
    private final int row, col;
    private final int lineWidth, pieceRadius;
    private final int width, height;
    private final int paddingLeft, paddingRight, paddingTop, paddingBottom;
    private int candidateC, candidateR;
    Piece[][] board;
    private Piece currentPiece;

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
        pieceRadius = ta.getInteger(R.styleable.ChessBoardView_piece_radius, DEFAULT_PIECE_SIZE);
        ta.recycle();

        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        width = blockWidth * (col - 1) + paddingLeft + paddingRight;
        height = blockHeight * (row - 1) + paddingTop + paddingBottom;

        paint = new Paint();
        board = new Piece[row][col];

        clearCandidate();
        setCurrentPiece(Piece.BLACK);

        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX(), y = (int) motionEvent.getY();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.d("haha", "UP");
                //
                if (isValidCoordinate(candidateR, candidateC))
                    setPiece(candidateR, candidateC, currentPiece);
                clearCandidate();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.d("haha", "MOVE");
                setCandidateOnTouch(x, y);
                break;
        }
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
                canvas.drawCircle(paddingLeft + j * blockWidth, paddingTop + i * blockHeight, pieceRadius, paint);
            }
        }

        // Draw the candidate.
        if (isValidCoordinate(candidateR, candidateC)) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(paddingLeft + candidateC * blockWidth, paddingTop + candidateR * blockHeight, pieceRadius, paint);
        }

    }

    public boolean isValidCoordinate(int i, int j) {
        return i >= 0 && i < row && j >= 0 && j < col;
    }

    public void setPiece(int i, int j, Piece piece) {
        board[i][j] = piece;
        postInvalidate();
    }

    public void removePiece(int i, int j) {
        setPiece(i, j, null);
    }

    public void setCandidate(int i, int j) {
        candidateR = i;
        candidateC = j;
        postInvalidate();
    }

    public void setCandidateOnTouch(int x, int y) {
        // Fix coordinate.
        x += blockWidth / 2 - paddingLeft;
        y += blockHeight / 2 - paddingTop;
        // Prune.
        int r = Math.min(Math.max(y / blockHeight, 0), row - 1);
        int c = Math.min(Math.max(x / blockWidth, 0), col - 1);
        if (board[r][c] == null)
            setCandidate(r, c);
    }

    public void clearCandidate() {
        setCandidate(-1, -1);
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

}
