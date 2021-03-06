package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setContentView(new MineView(this));
        TextView upFlag = findViewById(R.id.Flags);
        TextView youWon = findViewById(R.id.youWon);
        TextView youLost = findViewById(R.id.youLost);
        TextView upMines = findViewById(R.id.remainMines);
        MineView.btn_reset = (Button) findViewById(R.id.resetbutton);
        // Listen the reset button
        MineView.btn_reset.setOnClickListener(arg0 -> {
            MineView.start = true;

            MineView.flag.setBackgroundColor(0x6200EE);
            MineView.retry();

            upFlag.setText("Flag : 0");
            upMines.setText("Bomb on field : " + MineView.remainingMines);
        });

        MineView.flag = (Button) findViewById(R.id.flagbutton);
        // Listen the Flag Button
        MineView.flag.setOnClickListener(arg0 -> {
            if (MineView.flagon == true) {
                MineView.flagon = false;
                MineView.flag.setBackgroundColor(0x6200EE);
            }
            else {
                MineView.flagon = true;
                MineView.flag.setBackgroundColor(Color.YELLOW);
            }});
        // Update the texts fields every 0.5 second
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                upFlag.setText(MineView.stringFlag);
                upMines.setText("Bomb on field : " + MineView.remainingMines);

            }
        }, 0, 500);


    }

}