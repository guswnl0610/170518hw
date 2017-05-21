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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guswn_000 on 2017-05-18.
 */

public class Mypainter extends View {
    int oldX,oldY = -1;
    Bitmap mbitmap;
    Canvas mcanvas;
    Paint mpaint = new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    boolean check,scaled;

    public Mypainter(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public Mypainter(Context context)
    {
        super(context);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
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
        if(scaled==true)
        {
            Bitmap bigimg = Bitmap.createScaledBitmap(img,img.getWidth()*2,img.getHeight()*2,false);
            mcanvas.drawBitmap(bigimg,X,Y,mpaint);

        }
        else
        {
            mcanvas.drawBitmap(img,X,Y,mpaint);
        }

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


    //menu
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
            mpaint.setStrokeWidth(3);
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

    //button
    public void Eraser()
    {
        mbitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void Open(String filename)
    {
        try
        {
            FileInputStream in = new FileInputStream(filename);
            mcanvas.scale(0.5f,0.5f);
            mcanvas.drawBitmap(BitmapFactory.decodeStream(in),mcanvas.getWidth()/2,mcanvas.getHeight()/2,mpaint);
            mcanvas.scale(2.0f,2.0f);
            in.close();
            invalidate();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(),"저장된 파일이 없습니다.",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(),"IO Exception",Toast.LENGTH_SHORT).show();
        }
    }

    public void Save(String filename)
    {
        try
        {
            FileOutputStream out = new FileOutputStream(filename);
            mbitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(),"File not found",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(),"IO Exception",Toast.LENGTH_SHORT).show();
        }
    }

    public void rotatestamp()
    {
        mcanvas.rotate(30,mcanvas.getWidth()/2,mcanvas.getHeight()/2);
    }
    public void move()
    {
        mcanvas.translate(10,10);
    }
    public void scale()
    {
        scaled = true;
//        mcanvas.scale(1.5f,1.5f);
    }
    public void skew()
    {
        mcanvas.skew(0.2f,0.2f);
    }


}
