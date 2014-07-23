package com.example.ordermenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import com.example.view.LeftMenu;
import com.example.view.ListMenu;

public class MainActivity extends Activity 
{
	private LeftMenu tt;
	private ListMenu ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //tt = (LeftMenu)findViewById(R.id.test);
        //tt.init();
        ff = (ListMenu)findViewById(R.id.test2);
        ff.init("≤‚ ‘1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
