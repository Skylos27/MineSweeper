package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView isLose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView isLose = findViewById(R.id.youlost);

        //setContentView(new MineView(this));
        TextView upFlag = findViewById(R.id.Flags);
        TextView upMines = findViewById(R.id.remainMines);
        MineView.btn_reset = (Button) findViewById(R.id.resetbutton);
        MineView.btn_reset.setOnClickListener(new View.OnClickListener()
        {public void onClick(View arg0) {
            MineView.start = true;

            MineView.flag.setBackgroundColor(0x6200EE);
            MineView.retry();}
        });

        MineView.flag = (Button) findViewById(R.id.flagbutton);
        MineView.flag.setOnClickListener(new View.OnClickListener()
        {public void onClick(View arg0) {
            if (MineView.flagon == true) {
                MineView.flagon = false;
                MineView.flag.setBackgroundColor(0x6200EE);
                upFlag.setText(MineView.stringFlag);
                upMines.setText("Bomb remaining : " +MineView.remainingMines);
            }
            else {
                MineView.flagon = true;
                MineView.flag.setBackgroundColor(Color.YELLOW);
                upFlag.setText(MineView.stringFlag);
                upMines.setText("Bomb remaining : " +MineView.remainingMines);
            }}
        });


    }
}