package com.likeyichu.predict;

import java.io.IOException;

import com.likeyichu.jsoup.AboutJsoup;

public class Predict {
	/**从网页中获取文档，然后预测*/
	public static boolean predictFromUrl(String url){
		boolean bool=false;
		try {
			StringBuilder title=new StringBuilder();
			String text=AboutJsoup.getTextFromURL(url,title);
			System.out.println("html标题为:title");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("联网得到html失败");
		}
		return bool;
	}
	/**从本地获取文档，然后预测*/
	public static boolean predictFromLocal(String path){
		boolean bool=false;
		
		return bool;
	}
}
