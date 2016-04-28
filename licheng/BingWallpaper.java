package org.lic.applet;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;


public class BingWallpaper {
	
	public static void main (String args[])
	{
		String imgSrc=null;
		//获取源代码
		String jsonResource=
				getJsonResourceByURL("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1","utf-8");
		
		//解析源代码 
		imgSrc = getImageUrl(jsonResource);
		
		//获取网页图片
		downImages(imgSrc,"./img");
		
		System.out.println("图片下载成功");

	}
	
	/**
	 * 获取网页源码
	 * @param url
	 * @param encoding
	 * @return
	 */
	private static String getJsonResourceByURL(String url,String encoding)
	{
		//声明一个存储网页源代码的容器——字符缓冲区
		StringBuffer buffer=new StringBuffer();

		//注意涉及流的地方要给其一个初始值
		URL urlObj=null;
		URLConnection uc=null;
		InputStreamReader in=null;
		BufferedReader reader=null;

		//因为存在网络等原因的连接失败，所以要try,catch抛出异常
		try {
			//建立网络连接
			urlObj=new URL(url);
			//打开网络连接
			uc=urlObj.openConnection();
			//建立网络的输入流
			in=new InputStreamReader(uc.getInputStream(),encoding);
			//缓冲写入文件流
			reader=new BufferedReader(in);
			//临时变量
			String tempLine=null;
			//一行一行的读取文件流
			while((tempLine=reader.readLine())!=null)
			{
				//将数据放入缓冲区
				buffer.append(tempLine+"\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("连接超时！");
		}
		//随手关闭流
		finally{
			if(in !=null)
			{
				try
				{
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.print("关闭出错");
				}
			}
		}
		//将buffer(Stringbuffer类型转化为String类型)
		return buffer.toString();
	}

	/**
	 * 根据一个图片URL地址，批量下载图片到服务器磁盘filePath
	 * @param imgURL 要下载的图片服务器地址
	 * @param filePath 下载保存图片地址
	 */
	private static void downImages(String imgURL,String filePath)
	{
		//根据指定路径创建目录
		File parent = new File(filePath);
		//判断文件夹是否存在
		if(!parent.exists()){
			parent.mkdirs();
		}
		//获取图片名字，即截取图片路径最后一个/后面的内容
		String fileName=imgURL.substring(imgURL.lastIndexOf("/"));
		String imgType = "png";
		try {
			//使用java的ImageIO读取指定URL下的图片
			BufferedImage image = ImageIO.read(new URL(imgURL));
			System.out.println(fileName+","+imgType+","+image);
			//在parent目录下创建指定文件名称文件file
			File  file = new File(parent,fileName);
			System.out.println(file.getAbsolutePath());
			ImageIO.write(image, imgType, file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取图片地址
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	private static String getImageUrl(String jsonResource){
		// JSON解析为Map
		Map<String,Object> resourceMap = (Map<String, Object>)JSONObject.fromObject(jsonResource);
		List<Map<String,String>> imagesList = (List<Map<String,String>>)resourceMap.get("images");
		Map<String,String> t = (Map<String,String>)imagesList.get(0);
		String imgSrc = (String)t.get("url");
		return imgSrc;
	}
	
}
