package com.example.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.base.AppBasicInfo;
import com.example.ordermenu.R;

public class LeftMenu extends ListView
{
	private Context mContext;
	private List<Map<String,Object>> listItems;
	private String res;
	private SimpleAdapter simpleAdapter;
	private List<String> menus;
	
	public LeftMenu(Context context)
	{
		this(context,null);
	}
	public LeftMenu(Context context, AttributeSet attrs)
	{
		super(context , attrs);
		mContext = context;
	}
	public LeftMenu(Context context, AttributeSet attrs, int defStyle) 
	{
		this(context, null);
	}

	public void init()
	{
		listItems = new ArrayList<Map<String, Object>>();
		menus = new ArrayList<String>();
		
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AppBasicInfo.DIR + "菜单.txt");
		try 
		{
			FileInputStream fis = new FileInputStream(f);
			int length  = fis.available();
			byte[] buffer = new byte[length];
			fis.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			System.out.println("res-->" + res);
			fis.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(res != null)
		{
			String type = "<type>(.*?)</type>";
			Pattern p=Pattern.compile(type);
			Matcher m=p.matcher(res);
			while(m.find())
			{
				menus.add(m.group(1));
			}
			System.out.println("menus[" + menus.size());
			//menus = res.split(",");

			for(int i =0; i < menus.size(); i++)
			{
				//System.out.println("menus[" + i + "]-->" + menus.get(i));
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("name", menus.get(i));
				listItems.add(listItem);
			}
			simpleAdapter = new SimpleAdapter(mContext, listItems, R.layout.type_menu, new String[] {"name"} , new int[]{R.id.type_name});
			setAdapter(simpleAdapter);
		}
		else
		{
			Toast.makeText(mContext, "信息有误", Toast.LENGTH_SHORT).show();
		}
	}
	
	public String getItem(int i)
	{
		if(i<0 && i > menus.size())
		{
			return null;
		}
		else
		{
			return menus.get(i);
		}
	}
}
