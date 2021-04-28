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

    }
}
