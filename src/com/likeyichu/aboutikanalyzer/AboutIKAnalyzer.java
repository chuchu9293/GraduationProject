package com.likeyichu.aboutikanalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AboutIKAnalyzer {
	public static void main(String[] args) throws IOException {
		String str = "匹配：二部图中没有公共顶点的边集。边数最多的匹配叫最大匹配。若图中每个顶点都被匹配边邻接，叫完美匹配。二部图最小覆盖：选择尽量少的点，使得图中每条边至少有一个端点被选中。可以证明，最小覆盖数=最大匹配数。二部图的独立集：该集合中任意两点不相邻接。独立集中顶点个数=总顶点数-最大匹配中边的数目。";
		// 创建分词对象
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(str);
		// 分词
		TokenStream ts = anal.tokenStream("", reader);
		CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
		ts.reset();
		// 遍历分词数据
		while (ts.incrementToken()) {
			System.out.print(term.toString() + "|");
		}
		reader.close();
		anal.close();
		System.out.println();
	}

	public static List<String> getTermList(String str) throws IOException {
		List<String> termList = new ArrayList<String>();
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
				termList.add(term.toString());
		}
		reader.close();
		anal.close();
		Collections.sort(termList);
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
/*
 * 加载扩展停止词典：stopword.dic
 * 搜索算法|是|利用|计算机|的|高性能|来|有|目的|的|穷举|一个|问|题解|空间|的|部分|或|所有|的|可能
 * |情况|从而|求出|问题|的|解|的|一种
 * |方法|搜索算法|实际上|是|根据|初始条件|和|扩展|规则|构造|一棵|解答|树|并|寻找|符合|目标|状态|的|节点|的|过程|
 */