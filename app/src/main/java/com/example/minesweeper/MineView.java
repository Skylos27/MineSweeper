package com.example.minesweeper;

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

            if(event.getAction() == MotionEvent.ACTION_DOWN ){
                xtouch = x / rectBounds;
                ytouch = y / rectBounds;

                if(ytouch<10 && xtouch<10 ) {
                        isCovered[xtouch][ytouch] = false;
                }
                touched = true;
                Log.i("TAG", "touched down on " + x / rectBounds + ", " + y / rectBounds);


                invalidate();
            }


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
        int rectBounds = contentWidth / 10;

        //Side length of the square.
        int sideLength = rectBounds - 10;
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
        square = new Rect(10, 10, sideLength, sideLength);


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

            start = false;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

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

        if(touched) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {

                    if (isCovered[i][j] == true) {
                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();

                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, rectPaint);

                        //Restore. Back to the origin.
                        canvas.restore();
                    }
                    if (isCovered[i][j] == false) {

                        //Preliminary save of the drawing origin to the stack.
                        canvas.save();
                        //Translate to draw a row of squares. First will be at (0,0).
                        canvas.translate(i * rectBounds, j * rectBounds);
                        //Draw it.
                        canvas.drawRect(square, touchedPaint);
                        //Restore. Back to the origin.
                        canvas.restore();
                    }
                }
            }
            touched=false;
        }
        setOnTouchListener(handleTouch);

    }
}
