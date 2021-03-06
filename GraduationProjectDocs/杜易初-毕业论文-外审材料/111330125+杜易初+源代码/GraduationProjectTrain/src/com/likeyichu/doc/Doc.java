package com.likeyichu.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.likeyichu.ikanalyzer.AboutIKAnalyzer;
import com.likeyichu.token.Token;
import com.likeyichu.token.TokenStatistics;

/** 代表着网页中得到的正文文本 */
public class Doc {

	/** 文章id */
	public int id;

	/** 提取的title */
	public String title;

	/** 提取的正文 */
	public String content;

	/** 是否为 算法实践 相关文章 */
	public boolean isPositive = false;

	/** 中文分词结果，自然顺序，可以重复 */
	public List<String> tokenList = new ArrayList<String>();

	/** 数据库中读String恢复为Token。中文分词结果，自然顺序，可以重复 */
	public List<Token> tokenTokenList = new ArrayList<Token>();

	/** 中文分词结果的set，用于卡方检验中的快速检索 */
	public Set<Token> tokenSet = new HashSet<Token>();

	/** 词频的映射,termToFrequencyMap.keySet()为抽样后的特征词 */
	public Map<String, Integer> termToFrequencyMap = new HashMap<String, Integer>();

	/** 特征向量 */
	public List<Double> featureVectorList = new ArrayList<Double>();

	public static Doc generateDocFromWebPage(WebPage webPage)
			throws IOException {
		Doc doc = new Doc();
		doc.id = webPage.id;
		doc.title = webPage.title==null?"无标题": webPage.title;
		doc.content = webPage.content==null?"无内容": webPage.content;
		doc.isPositive = webPage.isPositive;

		doc.tokenList = AboutIKAnalyzer.getTokenList(doc.content);
		return doc;
	}

	void transformTokenListToTokenStringList() {
		//USE模块会复用
		if(tokenTokenList==null||tokenTokenList.size()==0)
			return;
		tokenList.clear();
		for (Token token : tokenTokenList) {
			tokenList.add(token.text.trim());
		}
	}

	public List<Double> getFeatureVectorList() {
		transformTokenListToTokenStringList();
		// 被除数，除数，特征向量的模 计算用以归一化
		double dividend = 0, divisor, module;
		for (String str : TokenStatistics.featureSortedTokenStringList) {
			termToFrequencyMap.put(str, Collections.frequency(tokenList, str));
			featureVectorList.add((double) termToFrequencyMap.get(str));
		}
		for (Double double1 : featureVectorList)
			dividend += double1 * double1;
		divisor = Math.sqrt(dividend);
		module = dividend / divisor;
		for (int i = 0; i < featureVectorList.size(); i++) {
			if (dividend == 0) // 可以==直接判断
				break;
			double tmp = featureVectorList.get(i) / module;
			featureVectorList.set(i, tmp);
		}
		return featureVectorList;
	}

	/**
	 * 数据库中读到的tokenList，由String类型转换为List
	 * 
	 * @param str
	 *            形如 [你好,哈哈,不错] 符号都为英文
	 * @return
	 */
	public List<Token> transferTokenListStringToList(String str) {
		List<Token> list = new ArrayList<Token>();
		// 大括号去掉
		str = str.substring(1, str.length() - 1);
		String[] strArray = str.split(",");
		for (String string : strArray) {
			list.add(new Token(string.trim()));
		}
		return list;
	}

	@Override
	public String toString() {
		return tokenSet.toString();
	}
}
