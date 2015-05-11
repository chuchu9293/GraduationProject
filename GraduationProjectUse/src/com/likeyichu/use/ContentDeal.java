package com.likeyichu.use;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.likeyichu.use.dal.Dao;
import com.likeyichu.doc.Doc;
import com.likeyichu.ikanalyzer.AboutIKAnalyzer;
import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.local.LocalPage;
import com.likeyichu.token.TokenStatistics;

public class ContentDeal {
	final static Logger logger = Logger.getLogger(ContentDeal.class);
	public String url;
	public String filePath;
	public boolean isUrl;
	public Doc doc=new Doc();
	
	PredictResult predictResult=new PredictResult();
	
	void urlDeal(){
		StringBuilder sb=new StringBuilder();
		try {
			doc.content=AboutJsoup.getTextFromURL(url, sb);
			doc.title=sb.toString();
		} catch (IOException e) {
			logger.error("urlDeal() error"+e.toString());
		}
	}
	
	
	void fileDeal(){
		try {
			LocalPage localPage=AboutJsoup.getLocalPage(new File(filePath));
			doc.title=localPage.title;
			doc.content=localPage.content;
		} catch (IOException e) {
			logger.error("fileDeal() error"+e.toString());
		}
	}
	
	public void deal(){
		if(isUrl)
			urlDeal();
		else 
			fileDeal();
		
		try {
			doc.tokenList=AboutIKAnalyzer.getTokenList(doc.content);
		} catch (IOException e) {
			logger.error("AboutIKAnalyzer.getTokenList(doc.content) error"+e.toString());
		}
		generateFeatureTokenStringListFromTable();
		doc.getFeatureVectorList();
	}
	
	/**从数据库中读取特征空间，赋值给TokenStatistics.featureSortedTokenStringList*/
	void generateFeatureTokenStringListFromTable(){
		Dao dao=Dao.getDao();
		List<String> list=new ArrayList<String>();
		String str;
		try {
			str = dao.generateFeatureTokenStringListStringFromTable();
		} catch (SQLException e) {
			logger.error("generateFeatureTokenStringListFromTable"+e.toString());
			return;
		}
		//大括号去掉
		str=str.substring(1,str.length()-1);
		String[] strArray=str.split(",");
		for (String string : strArray) 
			list.add(string);
		TokenStatistics.featureSortedTokenStringList=list;
	}
}
