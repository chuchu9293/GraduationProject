package com.likeyichu.token;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.likeyichu.dal.Dao;
import com.likeyichu.doc.Doc;

public class TokenStatistics {
	private static final Logger logger=Logger.getLogger(TokenStatistics.class);
	
	/**从token表中拿到所有Doc的tokenList*/
	public static List<Doc> docList;
	
	/**所有token的集合*/
	public static Set<Token> totalTokenSet=new HashSet<Token>();
	
	/**提取出来的特征单词*/
	public static List<String> featureSortedTokenStringList=new ArrayList<String>();
	
	public static int positiveDocNumber=0,negativeDocNumber=0,totalDocNumber=0;
	
	public static void generateDocListFromTable(){
		Dao dao=Dao.getDao();
		try {
			docList=dao.getDocListFromTokenTable();
		} catch (SQLException e) {
			logger.error("读取token_positive/negative_table失败"+e.toString());
		}
		for (Doc doc : docList) {
			if(doc.isPositive)
				positiveDocNumber++;
			else
				negativeDocNumber++;
		}
		totalDocNumber=positiveDocNumber+negativeDocNumber;
	}
	
	public static void getTotalTokenSet(){
		for (Doc doc : docList) {
			totalTokenSet.addAll(doc.tokenTokenList);
		}
		logger.info("得到的totalTokenSet.size()="+totalTokenSet.size());
	}
	
}