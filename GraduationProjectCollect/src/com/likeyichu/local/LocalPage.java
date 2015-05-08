package com.likeyichu.local;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.likeyichu.dao.Dao;
import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.spider.Spider;


public class LocalPage  {
	final static Logger logger=Logger.getLogger(LocalPage.class);
	private static Dao dao=new Dao();
	public String path;
	public String title;
	public String content;
	/**
	 * 批量解析本地网页，写入数据库
	 * @param folderPath 文件所在文件夹
	 * @return
	 */
	void insertLocalPageList(String folderPath){
		File theFolderPath =new File(folderPath) ;
		File[] fileArr=theFolderPath.listFiles(new MyFileFilter());
		for (File file : fileArr) {
			LocalPage localPage;
			try {
				localPage = AboutJsoup.getLocalPage(file);
			} catch (IOException e) {
				logger.error("解析本地网页出错:"+e.toString());
				continue;
			}
			dao.insertPositiveLocal(localPage.title, localPage.path, localPage.content);
		}//for
	}
	
	class MyFileFilter implements FilenameFilter{
		public boolean accept(File dir, String name) {
			if(name.toLowerCase().endsWith("txt"))
				return true;
			return false;
		}
	}
	
}
