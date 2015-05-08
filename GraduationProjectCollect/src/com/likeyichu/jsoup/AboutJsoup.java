package com.likeyichu.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AboutJsoup {
	private static Logger logger=Logger.getLogger(AboutJsoup.class);
	/**根据指定的url得到网页，返回文本，第二个参数返回标题*/
	public static String getTextFromURL(String url,StringBuilder out_title) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER").get();
		logger.info("得到一篇网络文档，标题：     "+doc.title());
		if(out_title.toString().length()!=0)
			out_title.delete(0,out_title.toString().length()-1);
		out_title.append(doc.title());
		String text = doc.text();
//		String[] texts = text.split("。|\\.");// 以中文句号或英文句号作为分割
//		for (String string : texts) {
//			System.out.println(string);
//		}
		return text;
	}
	
	/**根据指定的本地网页，返回文本，第二个参数返回标题*/
	public static String getTextFromLocal(String filePath,StringBuilder out_title) throws IOException {
		File in = new File(filePath);
		Document doc = Jsoup.parse(in, "UTF-8", ""); 
		System.out.println("得到一篇本地文档，标题：     "+doc.title());
		if(out_title.toString().length()!=0)
			out_title.delete(0,out_title.toString().length()-1);
		out_title.append(doc.title());
		String text = doc.text();
//		String[] texts = text.split("。|\\.");// 以中文句号或英文句号作为分割
//		for (String string : texts) {
//			System.out.println(string);
//		}
		return text;
	}
	/**得到网页中的所有跳转链接*/
	public static List<String> getLinkList(String url) throws IOException{
		Map<String,String> map=new  HashMap<String,String>();
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER").get();
		Elements elements=doc.getElementsByTag("a");
		for (Element element : elements) {
			 String linkHref = element.attr("href");
			 String linkText = element.text();
			 map.put(linkText,linkHref);
		}
		ArrayList<String> urlList=new ArrayList<String>( map.values());
		return urlList;
	}
	
}