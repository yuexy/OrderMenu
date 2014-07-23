package com.example.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private String[] menus;
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
			menus = res.split(",");
			for(int i = 0; i < menus.length; i++)
			{
				System.out.println("menus[i]" + menus[i]);
				Bitmap img = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + AppBasicInfo.DIR_IMG + menus[i] + ".jpg");

				images.add(img);
			}
			for(int j = 0; j < menus.length; j++)
			{
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("name", menus[j]);
				System.out.println("menus[i]" + menus[j]);
				listItem.put("image", images.get(j));
				listItem.put("info", "");
				listItems.add(listItem);
			}
			simpleAdapter = new SimpleAdapter(mContext, listItems, R.layout.list, new String[] {"image" ,"name" ,"info"} , new int[]{R.id.hander, R.id.name ,R.id.info});

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
}
