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

    String operation = "";
    Bitmap mbitmap;
    Canvas mcanvas;
    Paint mpaint = new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

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

//        drawStamp();

    }

    private void drawStamp(int X, int Y)
    {
        if(operation.equals("Blur"))
        {
            BlurMaskFilter blur = new BlurMaskFilter(1000, BlurMaskFilter.Blur.INNER);
            mpaint.setMaskFilter(blur);
            mcanvas.drawBitmap(img,X,Y,mpaint);
        }
        if(operation.equals("Color"))
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
            mcanvas.drawBitmap(img,X,Y,mpaint);
        }
        else
        {
            mpaint.setColorFilter(null);
            mpaint.setMaskFilter(null);
            mcanvas.drawBitmap(img,X,Y,mpaint);
        }

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
                drawStamp(X,Y);
                invalidate();
            }
            oldX = -1; oldY = -1;
        }
        return true;
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

    public void setOperationtype(String operationtype)
    {
        this.operation = operationtype;
//        invalidate();
    }


}
