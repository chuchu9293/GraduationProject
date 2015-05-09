package com.likeyichu.local;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.likeyichu.dao.Dao;

public class LocalPage implements Runnable{
	final static Logger logger = Logger.getLogger(LocalPage.class);
	private static Dao dao = new Dao();
	public String path;
	public String title;
	public String content;
	
	//并发有关
	Semaphore semaphore=new Semaphore(0);
	static boolean hasWait=false;
	Queue<LocalPage> localPageQueue;
	
	public LocalPage(){
		if (dao.getDataSource() == null) {
			ApplicationContext ctx = new FileSystemXmlApplicationContext(
					"src/com/likeyichu/local/beans.xml");
			DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
			dao.setDataSource(dataSource);
		}
	}

	/**
	 * 批量解析本地网页，放入容器ConcurrentLinkedQueue
	 * 
	 * @param folderPath
	 *            文件所在文件夹
	 * @return
	 */
	 Queue<LocalPage> getLocalPageQueue(String folderPath,ExecutorService executorService) {
		
		localPageQueue = new ConcurrentLinkedQueue<LocalPage>();
		File theFolderPath = new File(folderPath);
		//只关注后缀为.html的文件
		File[] fileArr = theFolderPath.listFiles(new MyFileFilter());
		List<File> fileList=Arrays.asList(fileArr);
		
		//分成三等份并发处理
		int pos1=fileList.size()/3;
		int pos2=pos1*2;
		Future<?> f1=executorService.submit(new ConcurrentCollect(fileList.subList(0, pos1),localPageQueue));
		Future<?> f2=executorService.submit(new ConcurrentCollect(fileList.subList(pos1,pos2),localPageQueue));
		Future<?> f3=executorService.submit(new ConcurrentCollect(fileList.subList(pos2, fileList.size()),localPageQueue));
		executorService.shutdown();
		while(true){
			if(f1.isDone()&&f2.isDone()&&f3.isDone()&&hasWait==true){
				semaphore.release();
				logger.info("semaphore.release();");
				break;
			}
			try {
				logger.info("TimeUnit.SECONDS.sleep(2);");
				TimeUnit.SECONDS.sleep(2);
				Thread.yield();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return localPageQueue;
	}

	/** 批量插入数据库 */
	  void insertPositiveLocalBatch() {
		try {
			logger.info("即将wait();");
			//避免先wait再notify
			hasWait=true;
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Set<String> pathSet;
		try {
			pathSet = dao.localPathathSet();
		} catch (SQLException e) {
			logger.error("解析已在数据库中的本地网页pathSet出错:" + e.toString());
			return;
		}
		while (!localPageQueue.isEmpty()) {
			LocalPage localPage = localPageQueue.remove();
			// 若数据库中已有本条记录，则不重复添加，
			if (!pathSet.contains(localPage.path))
				dao.insertPositiveLocal(localPage.title, localPage.path,
						localPage.content);
			else
				logger.info("数据库中已有本条记录，添加请求不予执行");
		}
	}

	class MyFileFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.toLowerCase().endsWith("html"))
				return true;
			return false;
		}
	}

	public static void main(String[] args) {
		LocalPage localPage = new LocalPage();
		
		ExecutorService executorService= Executors.newCachedThreadPool();
		executorService.submit(localPage);
		logger.info("executorService.submit(new LocalPage(localPageQueue));");
		localPage.getLocalPageQueue("D:/东华的工作学习/毕业设计/localTest/",executorService);
	}
	

	@Override
	public void run() {
		insertPositiveLocalBatch();
	}
}
