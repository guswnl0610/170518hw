package com.example.guswn_000.a170518hw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by guswn_000 on 2017-05-18.
 */

public class Mypainter extends View {
    int oldX,oldY = -1;
    Bitmap mbitmap;
    Canvas mcanvas;
    Paint mpaint = new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    boolean check;

    public Mypainter(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(2);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public Mypainter(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(2);
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

//        drawStamp();

    }

    private void drawStamp(int X, int Y)
    {
            mcanvas.drawBitmap(img,X,Y,mpaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mbitmap != null)
            canvas.drawBitmap(mbitmap,0,0,null);

    }



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
                if(!check) //체크안됐음
                {
                    mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                }
                invalidate();
                oldX = X; oldY = Y;
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if(oldX != -1)
            {
                if(check)
                {
                    drawStamp(X,Y);
                }
                else
                {
                    mcanvas.drawLine(oldX,oldY,X,Y,mpaint);
                }
            }
            invalidate();
            oldX = -1; oldY = -1;
        }
        return true;
    }

    public void checkboxcheck(boolean checked)
    {
        check = checked;
    }

    public void Eraser()
    {
        mbitmap.eraseColor(Color.WHITE);
        invalidate();
    }
    public void rotatestamp()
    {
        mcanvas.rotate(45,mcanvas.getWidth()/2,mcanvas.getHeight()/2);
        invalidate();
    }

    public void blur(boolean onoff)
    {
        if(onoff == true)
        {
            BlurMaskFilter blur = new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER);
            mpaint.setMaskFilter(blur);
        }
        else
        {
            mpaint.setMaskFilter(null);
        }
    }
    public void color(boolean onoff)
    {
        if(onoff == true)
        {
            float[] array = {
                    2f,0,0,0,-10f,
                    0,2f,0,0,-10f,
                    0,0,2f,0,-10f,
                    0,0,0,2f,0
            };
            ColorMatrix matrix = new ColorMatrix(array);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            mpaint.setColorFilter(filter);
        }
        else
        {
            mpaint.setColorFilter(null);
        }
    }

    public void penwidth(boolean onoff)
    {
        if(onoff == true)
        {
            mpaint.setStrokeWidth(5);
        }
        else
        {
            mpaint.setStrokeWidth(2);
        }
    }
    public void penRed()
    {
        mpaint.setColor(Color.RED);
    }
    public void penBlue()
    {
        mpaint.setColor(Color.BLUE);
    }
}
