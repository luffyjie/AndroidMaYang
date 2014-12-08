package com.mayang.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AssetHelper {
	private Context ctx;
	public AssetHelper(Context context)
	{
		ctx=context;
	}
	public void copyToSD()
	{
		AssetManager assetManager = ctx.getAssets();
		
		 try {
			InputStream is=assetManager.open("db/data.sqlite");
			//sd卡路径  sqlite:sdcard/tourdata/data.sqlite
			String pathdir=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
					 +"/museumdata";
			 File dir = new File(pathdir);
            String path=pathdir+ "/data.sqlite";
            if (!dir.exists())
                dir.mkdir();//创建目录
            if (!(new File(path)).exists())
            {
            	FileOutputStream fos = new FileOutputStream(path);
            	byte[] buffer = new byte[700000];
            	int count = 0;
            	while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            	}
            	fos.close();
            	is.close();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Bitmap getAssetImage(String imgname)
	{
		AssetManager assetManager = ctx.getAssets();
		
		 try {
			InputStream is=assetManager.open("images/"+imgname+".png");
			//Drawable da = Drawable.createFromStream(is, null);
			//获得Bitmap
			Bitmap bitmap=BitmapFactory.decodeStream(is);
			return bitmap;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return null;
	}
	/**
	 * 
	 * @param path asset下文件或文件夹
	 * @param sdpath sd卡tourdata下路径
	 */
	public void copyFileToSD(String assetpath)
	{
		// copyFileToSD("uploadfiles");
		String pathdir=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
				 +"/museumdata/";
		 try { 
	           String fileStr[] = ctx.getAssets().list(assetpath);
	           File file = new File(pathdir+assetpath);
	           if(file.exists()) return;
	          if (fileStr.length > 0) {//如果是目录 
	        	  file.mkdirs(); 
	              for (String str : fileStr) { 
	            	  String path = assetpath + "/" + str; 
	                 	copyFileToSD(path); 
	                   
	              } 
	           } else {//如果是文件 
	        	   if(!file.exists()){
	               InputStream is = ctx.getAssets().open(assetpath);
	               File tofile=new File(pathdir + assetpath);
	               if(!tofile.exists()){
	                FileOutputStream fos = new FileOutputStream(tofile); 
	              byte[] buffer = new byte[1024]; 
	              int count = 0; 
	                while (true) { 
	                  count++; 
	                  int len = is.read(buffer); 
	                   if (len == -1) { 
	                      break; 
	                 } 
	                  fos.write(buffer, 0, len); 
	             } 
	             is.close(); 
	             fos.close(); 
	         }
	        }
	        }
	      } catch (IOException e) { 
	           // TODO Auto-generated catch block 
	           e.printStackTrace(); 
	       } 
	} 
}
