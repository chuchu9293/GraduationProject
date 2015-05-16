package com.likeyichu.ikanalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AboutIKAnalyzer {
	
	public static List<String> getTokenList(String str) throws IOException {
		List<String> termList = new ArrayList<String>();
		if(str==null||str.length()==0)
			return termList;
		// 创建分词对象
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(str);
		// 分词
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		ts.reset();
		// 遍历分词数据
		while (ts.incrementToken()) {
			if (isChineseChar2(term.toString()))//只关注中文
				termList.add(term.toString().trim());//去掉前导、后置空格
		}
		reader.close();
		anal.close();
		//Collections.sort(termList);
		return termList;
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
	//isChineseChar2会快一点
	public static boolean isChineseChar2(String str) {
		boolean temp = false;
		if(str.length()>=1){
			 // int ch=str.indexOf(0);
			 char  ch=str.charAt(0);
			 if(ch<=128)
				 temp=false;
			 else
				 temp=true;
		}
		return temp;
	}

}
