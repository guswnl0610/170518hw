package com.example.guswn_000.a170518hw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by guswn_000 on 2017-05-18.
 */

public class Mypainter extends View {

    Bitmap mbitmap;
    Canvas mcanvas;
    Paint mpaint = new Paint();

    public Mypainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public Mypainter(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();
        //빗맵과 캔버스 연결
        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.WHITE);

        drawStamp();

    }

    private void drawStamp()
    {
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mcanvas.drawBitmap(img,10,10,mpaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mbitmap != null)
            canvas.drawBitmap(mbitmap,0,0,null);
    }


    int oldX,oldY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int)event.getX();
        int Y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            oldX = X; oldY = Y;
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(oldX != -1)
            {
                mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
                oldX = X; oldY = Y;
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if(oldX != -1)
            {
                mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
            }
            oldX = -1; oldY = -1;
        }
        return true;
    }
}
