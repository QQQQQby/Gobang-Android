package com.byqi.gobang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.byqi.gobang.view.ChessBoardView;

public class MainActivity extends AppCompatActivity {

    ChessBoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.chessBoard);
    }
}
