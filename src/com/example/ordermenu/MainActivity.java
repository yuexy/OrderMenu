package com.example.ordermenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.view.LeftMenu;
import com.example.view.ListMenu;

public class MainActivity extends Activity 
{
	private ListMenu menu_center;
	private LeftMenu menu_left;
	private ListView menu_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
    	menu_center = (ListMenu)findViewById(R.id.menu_center);
    	menu_left = (LeftMenu)findViewById(R.id.menu_left);
    	menu_right = (ListView)findViewById(R.id.menu_right);
    	menu_left.init();
    	//menu_center.init("����1");
        
    	menu_left.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				menu_center.init(menu_left.getItem((arg2)));
			}
    	});
    	menu_center.setOnItemClickListener(new OnItemClickListener()
    	{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				customView_cancle(arg2);
			}
    	});
    }
    
    public void customView_cancle(int id)
	{
		//װ��/res/layout/login.xml���沼��
		final RelativeLayout submitForm = (RelativeLayout)getLayoutInflater()
			.inflate( R.layout.account_lay, null);
		final TextView numShow = (TextView)submitForm.findViewById(R.id.num);
		Button addButton = (Button)submitForm.findViewById(R.id.add);
		Button redButton = (Button)submitForm.findViewById(R.id.red);
		new AlertDialog.Builder(this)
			// ���öԻ����ͼ��
			//.setIcon(R.drawable.ic_launcher)
			// ���öԻ���ı���
			.setTitle("��������")
			// ���öԻ�����ʾ��View����
			.setView(submitForm)
			// Ϊ�Ի�������һ����ȷ������ť
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					int num = Integer.parseInt(numShow.getText().toString());
				}
			})
			.setNegativeButton("ȡ��", null)
			// ����������ʾ�Ի���
			.create().show();
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
