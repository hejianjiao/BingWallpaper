package com.hejianjiao;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

public class JavaBingWallpaper {

	public static void main(String[] args) {
		JavaBingWallpaper obj = new JavaBingWallpaper();
		System.out.println("开始,请稍候");
		for(int n = 0;n < 20; n ++){
			String str = "http://www.bing.com/HPImageArchive.aspx?format=js&idx="+ n +"&n=1&mkt=en-US";
			String json = obj.getDoc(str);
			String url = obj.getUrl(json);
			if(url == ""){
				break;
			}
			String name = url.substring(36);
			obj.downJPG(url,name);
		}
		System.out.println("完成\n图片成功下载到当前工作空间下的BingWallpaper文件夹");
	}

	//获取url的DOCUMENT内容
	public String getDoc(String url){
		String result="";
		try {
			URL url1 = new URL(url);
			HttpURLConnection urlcon = (HttpURLConnection)url1.openConnection();
			urlcon.connect();
			InputStream is = urlcon.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
			StringBuffer bs = new StringBuffer();
			String s = null;
			while((s = buffer.readLine()) != null){
				bs.append(s).append("\n");
			}
			result = bs.toString();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	//从获取到的字符串中找到目标url
	public String getUrl(String json){
		if(json.indexOf("{") == -1){
			return "";
		}else{
			JSONObject jsonobject = new JSONObject(json);
			JSONArray jsonarray = jsonobject.getJSONArray("images");
			String url = jsonarray.getJSONObject(0).getString("url");
			return url;
		}
	}

	//从url下载jpg图片
	public void downJPG(String url,String name){
		String filepath = "./BingWallpaper";
		File parent = new File(filepath);
		if(!parent.exists()){
			parent.mkdir();
		}
		try {
			BufferedImage image = ImageIO.read(new URL(url));

			File file = new File(parent,name + ".jpg");
			ImageIO.write(image, "jpg", file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
