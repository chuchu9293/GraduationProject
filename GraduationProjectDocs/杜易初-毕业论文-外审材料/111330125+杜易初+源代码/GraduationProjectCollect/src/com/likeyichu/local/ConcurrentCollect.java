package com.likeyichu.local;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.likeyichu.jsoup.AboutJsoup;

public class ConcurrentCollect implements Runnable{
	final static Logger logger = Logger.getLogger(ConcurrentCollect.class);
	List<File> fileList;
	Queue<LocalPage> LocalPageQueue;
	public ConcurrentCollect(List<File> fileList,Queue<LocalPage> LocalPageQueue){
		this.fileList=fileList;
		this.LocalPageQueue=LocalPageQueue;
		
	}
	@Override
	public void run() {
		for (File file : fileList) {
			try {
				LocalPageQueue.add(AboutJsoup.getLocalPage(file));
			} catch (IOException e) {
				logger.error("Jsoup解析本地网页出错"+e.toString());
			}
		}
	}
}
