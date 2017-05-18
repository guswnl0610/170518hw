package com.example.guswn_000.a170518hw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                break;

            case R.id.menucolor://컬러 필터 체크메뉴

                break;
            case R.id.menupenwb: //펜 굵기 체크메뉴

                break;
            case R.id.menured:

                break;
            case R.id.menublue:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
