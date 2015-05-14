package com.likeyichu.doc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.likeyichu.dal.Dao;
import com.likeyichu.doc.Doc;
import org.junit.Test;

public class DocStatistics {
	final static Logger logger = Logger.getLogger(DocStatistics.class);
	
	/**文档列表  */
	public static List<Doc> docList=new  ArrayList<Doc>();
		
	//@Test
	public void  generateDocList(){
		Dao dao=Dao.getDao();
		List<WebPage> webPageList;
		try {
			webPageList=dao.generateWebPageList();
		} catch (Exception e) {
			logger.error("从数据库中读取所有的数据失败"+e.toString());
			return;
		}
		for (WebPage webPage : webPageList) {
			try {
				docList.add(Doc.generateDocFromWebPage(webPage));
			} catch (IOException e) {
				logger.error("Doc.generateDocFromWebPage(webPage)失败"+e.toString());
			}
		}
		logger.info("得到文档列表成功");
	}
	
	
	public void insertDocListIntoTokenTable(){
		Dao dao=Dao.getDao();
		for (Doc doc : docList) {
			if(doc.isPositive)
				dao.insertPositiveToken(doc.id,doc.title,doc.tokenList.toString());
			else
				dao.insertNegativeToken(doc.id,doc.title,doc.tokenList.toString());
		}
	}
	@Test
	public void test(){
		generateDocList();
		insertDocListIntoTokenTable();
	}
	
	/**读collect表，写token表*/
	public static void main(String[] args) {
		DocStatistics docStatistics=new DocStatistics();
		docStatistics.generateDocList();
		docStatistics.insertDocListIntoTokenTable();
		
	}
	

}
