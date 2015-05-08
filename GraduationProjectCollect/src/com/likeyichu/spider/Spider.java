package com.likeyichu.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.likeyichu.jsoup.AboutJsoup;

/**爬虫，当urlList中未读的数目小于阀值threshold时继续工作*/
public class Spider {
	final static Logger logger=Logger.getLogger(Spider.class);
	String initialUrl;
	List<String> urlList=new ArrayList<String>();
	int hasRead=0;
	int unread=0;
	int threshold=100;
	int workIndex=0;

	public String getInitialUrl() {
		return initialUrl;
	}
	public void setInitialUrl(String initialUrl) {
		this.initialUrl = initialUrl;
		urlList.add(initialUrl);
		work();
	}
	void work(){
		while(unread<threshold){
			if(workIndex>=urlList.size())
				workIndex--;
			getUrl(urlList.get(workIndex++));
		}
	}
	void getUrl(String url){
		if(urlList.contains(url)&&hasRead!=0){
			hasRead++;
			return;
		}
		try {
			List<String> tmpList=AboutJsoup.getLinkList(url);
			Iterator<String> iterator=tmpList.iterator();
			//有些href不以“http://”开头，那么jsoup解析就会出错，所以需要把它们删了
			while(iterator.hasNext()){
				String str=iterator.next();
				if(!str.startsWith("http://"))
					iterator.remove();
			}
			urlList.addAll(tmpList);
			unread+=tmpList.size();
		} catch (IOException e) {
			logger.error("jsoup获取<a>标签中的url集合失败"+e.toString());
		}
	}
	/**读取已经爬取到的urlList中的下一条未读内容*/
	public String next(){
		//若未读的低于阀值，继续工作
		work();
		return urlList.get(hasRead++);
	}
	@Test
	public void test(){
		setInitialUrl("http://www.csdn.net");
		logger.info("urlList.size():"+urlList.size());
	}
}
