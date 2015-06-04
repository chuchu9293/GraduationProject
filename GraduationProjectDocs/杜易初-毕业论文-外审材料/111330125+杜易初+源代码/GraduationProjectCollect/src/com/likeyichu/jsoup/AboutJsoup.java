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

import com.likeyichu.local.LocalPage;

public class AboutJsoup {
	private static Logger logger=Logger.getLogger(AboutJsoup.class);
	/**根据指定的url得到网页，返回文本，第二个参数返回标题*/
	public static String getTextFromURL(String url,StringBuilder out_title) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER").get();
		String title=doc.title();
		if(title==null||title.length()<1)
			title="无标题";
		logger.info("得到一篇网络文档，标题：     "+title);
		if(out_title.toString().length()!=0)
			out_title.delete(0,out_title.toString().length()-1);
		out_title.append(title);
		String text = doc.text();
//		String[] texts = text.split("。|\\.");// 以中文句号或英文句号作为分割
//		for (String string : texts) {
//			System.out.println(string);
//		}
		if(text==null||text.length()<1){
			text="可能由于网页反爬虫策略，无内容";
		}
		return text;
	}
	
	/**根据指定的本地网页，返回解析后网页*/
	public static LocalPage getLocalPage(File file) throws IOException {
		LocalPage localPage=new LocalPage();
		Document doc = Jsoup.parse(file, null, "");//不需要设置编码，Jsoup会根据<meta>标签的charset属性自己决定，好省心 
		logger.info("得到一篇本地文档，标题：     "+doc.title());
		
		localPage.path=file.getAbsolutePath();
		localPage.title=doc.title();
		localPage.content = doc.text();
		
		return localPage;
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