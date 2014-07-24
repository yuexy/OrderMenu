package com.example.ordermenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.view.LeftMenu;
import com.example.view.ListMenu;

public class MainActivity extends Activity 
{
	private ListMenu menu_center;
	private LeftMenu menu_left;
	private ListView menu_right;
	private List<Map<String, Object>> submitList;
	private List<String> submitListName;
	private List<String> submitListPrice;
	private List<String> submitListNum;
	private TextView sum_price_show;
	
	private SimpleAdapter simpleAdapter;
	
	private int num;
	private int ind;
	private int mid;
	private int sum_price = 0;
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
    	sum_price_show = (TextView)findViewById(R.id.sum);
    	
    	sum_price_show.setText(sum_price + "");
    	
    	submitList = new ArrayList<Map<String, Object>>();
    	submitListName = new ArrayList<String>();
    	submitListPrice = new ArrayList<String>();
    	submitListNum = new ArrayList<String>();
    	
    	simpleAdapter = new SimpleAdapter(MainActivity.this, submitList, R.layout.submit_menu,  new String[] {"name","count"} , new int[]{R.id.sub_name,R.id.sub_num});
    	menu_right.setAdapter(simpleAdapter);
    	
    	menu_left.init();
        
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
				customView_cancle(1,arg2);
			}
    	});
    	menu_right.setOnItemClickListener(new OnItemClickListener()
    	{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				customView_cancle(2,arg2);
			}
    	});
    }
    
    //显示AlertDialog
    public void customView_cancle(int co,int id)
	{
    	mid = id;
		//装载/res/layout/login.xml界面布局
		final RelativeLayout submitForm = (RelativeLayout)getLayoutInflater()
			.inflate( R.layout.account_lay, null);
		final TextView numShow = (TextView)submitForm.findViewById(R.id.num);
		final Button addButton = (Button)submitForm.findViewById(R.id.add);
		final Button redButton = (Button)submitForm.findViewById(R.id.red);
		if(co == 1)
		{
			ind = submitListName.indexOf((String)menu_center.getName(id));
		}
		else
		{
			ind = id;
		}
		System.out.println("ind-->" + ind);
		if(ind == -1)
		{
			num = Integer.parseInt(numShow.getText().toString());
		}
		else
		{
			num = Integer.parseInt(submitListNum.get(ind));
			numShow.setText("" + num);
		}
		redButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				if(num == 0)
				{
					Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_SHORT).show();
				}
				else
				{
					num--;
					numShow.setText("" + num);
				}
			}
		});
		addButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				num++;
				numShow.setText("" + num);
			}
		});
		new AlertDialog.Builder(this)
			// 设置对话框的图标
			//.setIcon(R.drawable.ic_launcher)
			// 设置对话框的标题
			.setTitle("订餐数量")
			// 设置对话框显示的View对象
			.setView(submitForm)
			// 为对话框设置一个“确定”按钮
			.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if(ind == -1)
					{
						if(num != 0)
						{
							submitListName.add(menu_center.getName(mid));
							submitListNum.add(String.valueOf(num));
							submitListPrice.add(menu_center.getPrice(mid));
							
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("name", menu_center.getName(mid));
							m.put("count", String.valueOf(num));
							
							submitList.add(m);
							refreshList();
						}
					}
					else
					{
						if(num != 0)
						{
							submitListName.add(submitListName.get(mid));
							submitListNum.add(String.valueOf(num));
							submitListPrice.add(submitListPrice.get(mid));
							
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("name", submitListName.get(mid));
							m.put("count", String.valueOf(num));
							submitList.add(m);
						}

							submitListName.remove(ind);
							submitListNum.remove(ind);
							submitListPrice.remove(ind);
							submitList.remove(ind);

						refreshList();
					}
				}
			})
			.setNegativeButton("取消", null)
			// 创建、并显示对话框
			.create().show();
	}
    
    private void refreshList()
    {
    	sum_price = 0;
    	for(int i = 0; i < submitListName.size(); i++)
    	{
    		sum_price += Integer.parseInt(submitListNum.get(i)) * Integer.parseInt(submitListPrice.get(i));
    	}
    	sum_price_show.setText("总价：" + sum_price + "元");
    	SimpleAdapter sAdapter = (SimpleAdapter)menu_right.getAdapter();
    	sAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
