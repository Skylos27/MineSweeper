package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class MineView extends View {
    private Paint rectPaint;
    private Paint touchedPaint;
    private Paint bombPaint;
    private Paint flagPaint;
    private TextPaint Mine;
    Rect square;
    int sideLength;




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

    }
}
