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

import com.likeyichu.ikanalyzer.AboutIKAnalyzer;

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
