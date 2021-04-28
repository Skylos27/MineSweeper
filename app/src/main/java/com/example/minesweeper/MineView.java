package com.example.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MineView extends View {
    public static Button btn_reset;
    public static Button flag;
    public static TextView flags;

    public static boolean isLose = false;
    public static boolean isWin =false;
    public static String stringFlag ="Flag : 0";

    public static int flagPut;
    public static int remainingMines;
    private Paint rectPaint;
    private Paint touchedPaint;
    private Paint bombPaint;
    private Paint flagPaint;
    private TextPaint Mine;

    public static boolean start = true;
    public static boolean flagon ;
    private static boolean[][] isbomb = new boolean[10][10];
    private static boolean[][] isCovered = new boolean[10][10];
    private static int[][] neighbors = new int[10][10];
    private static boolean[][] isFlag = new boolean[10][10];

    Rect square;
    int sideLength;

    private int xtouch;
    private int ytouch;
    public static boolean touched =false;

    public MineView(Context context) {
        super(context);
        init(null, 0);
    }

    public MineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        //Set the background color.
        setBackgroundColor(Color.WHITE);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }//init()

    private void invalidateTextPaintAndMeasurements() {

    }//invalidateTextPaintAndMeasurements()
    public static void retry(){
        isbomb = new boolean[10][10];
        isCovered = new boolean[10][10];
        neighbors = new int[10][10];
        isFlag = new boolean[10][10];
        start = true;
        flagon = false;
        isLose = false;
        isWin=false;
        flagPut = 0;
    }

    private OnTouchListener handleTouch= new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int contentWidth = getWidth() - paddingLeft - paddingRight;

            //Bounds of squares to be drawn.
            int rectBounds = contentWidth / 10;

            if(event.getAction() == MotionEvent.ACTION_DOWN && !isLose){
                xtouch = x / rectBounds;
                ytouch = y / rectBounds;

                if(ytouch<10 && xtouch<10 && flagon == false){
                    if(isFlag[xtouch][ytouch] == false){
                        isCovered[xtouch][ytouch] = false;
                        if (isbomb[xtouch][ytouch] == true)isLose = true;}
                }
                if(ytouch<10 && xtouch<10 && flagon == true){
                    if (isCovered[xtouch][ytouch]){
                        if(isFlag[xtouch][ytouch] == true)
                            isFlag[xtouch][ytouch] = false;
                        else isFlag[xtouch][ytouch] = true;
                    }
                }
                touched = true;
                Log.i("TAG", "touched down on " + x / rectBounds + ", " + y / rectBounds+ " flagon = "+flagon + " is lose :" +isLose);


                invalidate();
            }
            flagPut=0;
            remainingMines = 20;
            for(int i = 0;i<10;i++){
                for(int j = 0;j<10;j++){
                    if(isFlag[i][j]) {
                        flagPut +=1;
                    }
                    if(isFlag[i][j] && isbomb[i][j]) remainingMines--;
                }
            }
            stringFlag = "Flag : "+ flagPut;

            return true;
        }
    };



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        //Bounds of squares to be drawn.
        int rectBounds = contentWidth/10;

        //Side length of the square.
        int sideLength = rectBounds -10 ;

        Mine = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        Mine.setColor(Color.BLACK);
        Mine.setTextSize(100);


        //Paint instance for drawing the squares.
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.BLACK);
        touchedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        touchedPaint.setColor(Color.GRAY);
        bombPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bombPaint.setColor(Color.RED);
        flagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        flagPaint.setColor(Color.YELLOW);
        //Create a rect which is actually a square.
        square = new Rect(10,10, sideLength, sideLength);

        // On Start
        if(start){
            for(int i=0; i<10; i++) {
                for (int j = 0; j < 10; j++) {
                    isCovered[i][j] = true;
                    isFlag[i][j] = false;
                    isbomb[i][j] = false;
                    neighbors[i][j]=0;
                    canvas.save();
                    canvas.translate(i * rectBounds, j * rectBounds);
                    canvas.drawRect(square, rectPaint);
                    canvas.restore();
                }
            }
            for(int k=0; k<20; k++) {
                Random rand = new Random();
                int rand1 = rand.nextInt(9);
                int rand2 = rand.nextInt(9);
                if(isbomb[rand1][rand2] == false) isbomb[rand1][rand2] = true;
                else k --;
            }
            for(int i=0; i<10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (isbomb[i][j] == true){
                        if(i-1>=0 && j-1>=0) neighbors[i-1][j-1]+=1;
                        if(i>=0 && j-1>=0) neighbors[i][j-1]+=1;
                        if(i+1<=9 && j-1>=0) neighbors[i+1][j-1]+=1;
                        if(i+1<=9 && j>=0) neighbors[i+1][j]+=1;
                        if(i+1<=9 && j+1<=9) neighbors[i+1][j+1]+=1;
                        if(i<=9 && j+1<=9) neighbors[i][j+1]+=1;
                        if(i-1>=0 && j+1<=9) neighbors[i-1][j+1]+=1;
                        if(i-1>=0 && j+1<=9) neighbors[i-1][j]+=1;

                    }

                }
            }
            start = false;
        }


        //Draw a row of squares.

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++) {

                //Preliminary save of the drawing origin to the stack.
                canvas.save();

                //Translate to draw a row of squares. First will be at (0,0).
                canvas.translate(i * rectBounds, j * rectBounds);
                //Draw it.
                canvas.drawRect(square, rectPaint);

                //Restore. Back to the origin.
                canvas.restore();

            }// for j
        }//for i
        if(touched){
            for(int i =0; i < 10; i++) {
                for (int j=0;j<10;j++) {
                    if (isCovered[i][j] == false && isFlag[i][j] == false ) {

                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();
                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, touchedPaint);
                        String neigh = String.valueOf(neighbors[i][j]);
                        canvas.drawText(neigh, 25, sideLength - 5, Mine);
                        //Restore. Back to the origin.
                        canvas.restore();
                    }
                    else if (isCovered[i][j] == true && isFlag[i][j] == false) {
                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();

                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, rectPaint);

                        //Restore. Back to the origin.
                        canvas.restore();
                    }
                    else if (isCovered[i][j] && isFlag[i][j]) {
                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();

                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, flagPaint);

                        //Restore. Back to the origin.
                        canvas.restore();
                    }
                    if (!isCovered[i][j] && !isFlag[i][j]  && isbomb[i][j] ) {
                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();

                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, bombPaint);
                        canvas.drawText("M", 9, sideLength - 5, Mine);


                        //Restore. Back to the origin.
                        canvas.restore();

                    }

                }
            }
            touched = false;
        }//for i

        setOnTouchListener(handleTouch);


    }//onDraw()

}//class

