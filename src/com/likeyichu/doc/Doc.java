package com.likeyichu.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.likeyichu.aboutikanalyzer.AboutIKAnalyzer;
import com.likeyichu.aboutjsoup.AboutJsoup;

/**代表着网页中得到的正文文本*/
public class Doc {
	/**提取的title  */
	public String title;
	/**提取的正文  */
	public String text;
	/**来源网页的url  */
	public String url;
	/**有序排列的中文分词结果，可以重复 */
	public List<String> termList=new ArrayList<String>();
	/**词频的映射,termToFrequencyMap.keySet()为抽样后的特征词*/
	public Map<String,Integer> termToFrequencyMap=new HashMap<String,Integer>();
	/**中文分词结果的set，用于卡方检验中的快速检索*/
	public Set<String> termSet=new HashSet<String>();
	/**是否为 算法实践 相关文章 */
	public boolean isRelative=false;
	/**特征向量*/
	public List<Double> featureVectorList=new ArrayList<Double>();
	
	
	public static List<Doc> generateDocsTest(){
		List<Doc> docList=new ArrayList<Doc> ();
		Doc doc1=new Doc();
		doc1.text="窗外的二叉树可以分为平衡二叉树与非平衡二叉树";
		doc1.termList.add("窗外");
		doc1.termList.add("二叉树");doc1.termList.add("可以");
		doc1.termList.add("平衡");doc1.termList.add("平衡");
		doc1.termSet.addAll(doc1.termList);
		doc1.isRelative=true;
		
		Doc doc2=new Doc();
		doc2.text="窗外的麻雀，在电线杆上多嘴";
		doc2.termList.add("窗外");doc2.termList.add("麻雀");
		doc2.termList.add("电线杆");doc2.termList.add("多嘴");
		doc2.termSet.addAll(doc2.termList);
		doc2.isRelative=false;
		
		docList.add(doc1); docList.add(doc2);
		return docList;
	}
	public static Doc generateDocFromFile(File file) throws IOException{
		Scanner scanner=new Scanner(file);
		StringBuilder sb=new StringBuilder();
		while(scanner.hasNextLine())
			sb.append(scanner.nextLine());
		Doc doc=new Doc();
		doc.text=sb.toString();
		doc.termList=AboutIKAnalyzer.getTermList(doc.text);
		doc.termSet.addAll(doc.termList);
		if(doc.text.startsWith("+1"))
			doc.isRelative=true;
		scanner.close();
		return doc;
	}
	/**从url得到Doc*/
	public static Doc generateDocFromUrl(String url,boolean isRelative) throws IOException{
		Doc doc=new Doc();
		StringBuilder title=new StringBuilder();
		doc.text=AboutJsoup.getText(url,title);
		doc.title=title.toString();
		doc.termList=AboutIKAnalyzer.getTermList(doc.text);
		doc.termSet.addAll(doc.termList);
		doc.isRelative=isRelative;
		return doc;
	}
	public List<Double> getFeatureVectorList(){
		//被除数，除数，特征向量的模  计算用以归一化
		double dividend=0,divisor,module;
		for(String str:Term.featureSortedTermList){
			termToFrequencyMap.put(str,Collections.frequency(termList, str));
			featureVectorList.add((double)termToFrequencyMap.get(str));
		}
		for (Double double1 : featureVectorList) 
			dividend+=double1*double1;
		divisor=Math.sqrt(dividend);
		module=dividend/divisor;
		for(int i=0;i<featureVectorList.size();i++){
			if(dividend==0) //可以==直接判断
				break;
			double tmp=featureVectorList.get(i)/module;
			featureVectorList.set(i,tmp);
		}
		return featureVectorList;
	}
	@Override
	public String toString(){
		return termSet.toString();
	}
}
