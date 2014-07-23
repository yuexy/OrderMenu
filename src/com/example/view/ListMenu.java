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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import com.example.base.AppBasicInfo;
import com.example.ordermenu.R;

public class ListMenu extends ListView
{
	private List<Map<String, Object>> listItems;
	private List<Bitmap> images;
	private String res;
	private List<String> menus;
	private List<String> des;
	private List<String> price;
	private Context mContext;
	private SimpleAdapter simpleAdapter;
	
	public ListMenu(Context context)
	{
		this(context,null);
	}
	public ListMenu(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
	}
	public ListMenu(Context context, AttributeSet attrs, int defStyle) 
	{
		this(context, null);
	}

	public void init(String menu_name)
	{
		listItems = new ArrayList<Map<String, Object>>();
		images = new ArrayList<Bitmap>();
		menus = new ArrayList<String>();
		des = new ArrayList<String>();
		price = new ArrayList<String>();
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AppBasicInfo.DIR + menu_name + ".txt");
		try 
		{
			FileInputStream fis = new FileInputStream(f);
			int length = fis.available();
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
			Pattern p = Pattern.compile(type);
			Matcher m = p.matcher(res);
			while(m.find())
			{
				menus.add(m.group(1));
			}
			
			String pri = "<price>(.*?)</price>";
			p = Pattern.compile(pri);
			m = p.matcher(res);
			while(m.find())
			{
				price.add(m.group(1));
			}
			
			String de = "<des>(.*?)</des>";
			p = Pattern.compile(de);
			m = p.matcher(res);
			while(m.find())
			{
				des.add(m.group(1));
			}

			System.out.println("des.size()-->" + des.size());
			System.out.println("price.size()-->" + price.size());
			for(int i = 0; i < menus.size(); i++)
			{
				System.out.println("menus[i]" + menus.get(i));
				Bitmap img = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + AppBasicInfo.DIR_IMG + menus.get(i) + ".jpg");
				images.add(img);
			}
			for(int j = 0; j < menus.size(); j++)
			{
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("name", menus.get(j));
				System.out.println("menus[i]" + menus.get(j));
				listItem.put("image", images.get(j));
				listItem.put("info", des.get(j));
				listItem.put("price", price.get(j));
				listItems.add(listItem);
			}
			simpleAdapter = new SimpleAdapter(mContext, listItems, R.layout.list, new String[] {"image" ,"name" ,"info","price"} , new int[]{R.id.hander, R.id.name ,R.id.info,R.id.price});

			simpleAdapter.setViewBinder(new ListViewBinder());

			setAdapter(simpleAdapter);

		}
		else
		{
			Toast.makeText(mContext, "Êý¾Ý´íÎó", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class ListViewBinder implements ViewBinder
	{
		@Override
		public boolean setViewValue(View view, Object data, String arg2) 
		{
			if((view instanceof ImageView) && (data instanceof Bitmap))
			{
				ImageView mImageView = (ImageView)view;
				Bitmap mBitmap = (Bitmap)data;
				mImageView.setImageBitmap(mBitmap);
				return true;
			}
			return false;
		}
	}
	
	public String getName(int id)
	{
		return menus.get(id);
	}
	
	public String getPrice(int id)
	{
		return price.get(id);
	}
}
