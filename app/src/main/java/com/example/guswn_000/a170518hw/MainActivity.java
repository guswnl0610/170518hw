package com.example.guswn_000.a170518hw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Mypainter mypainter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init()
    {
        mypainter = (Mypainter)findViewById(R.id.painter);
    }


    //메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menublur://블러 체크메뉴
                if(item.isChecked())
                {
                    item.setChecked(false);
                    mypainter.setOperationtype("Blurx");
                }
                else
                {
                    item.setChecked(true);
                    mypainter.setOperationtype("Blur");
                }
                break;

            case R.id.menucolor://컬러 필터 체크메뉴
                if (item.isChecked())
                {
                    item.setChecked(false);
                    mypainter.setOperationtype("Colorx");
                }
                else
                {
                    item.setChecked(true);
                    mypainter.setOperationtype("Color");
                }
                break;
            case R.id.menupenwb: //펜 굵기 체크메뉴
                if (item.isChecked())
                {
                    item.setChecked(false);
                    mypainter.setOperationtype("Penwidthx");
                }

                else
                {
                    item.setChecked(true);
                    mypainter.setOperationtype("Penwidth");
                }


                break;
            case R.id.menured:

                break;
            case R.id.menublue:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnerase://지우긔
                mypainter.Eraser();
                break;
            case R.id.btnmove://사랑은 뭅뭅
                break;
            case R.id.btnopen:
                break;
            case R.id.btnsave:
                break;
            case R.id.btnrotate:
                mypainter.rotatestamp();
                break;
            case R.id.btnscale:
                break;
            case R.id.btnskew:
                break;
        }

    }

}
