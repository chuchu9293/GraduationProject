package com.likeyichu.doc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**代表着网页中得到的正文文本*/
public class Doc {
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
	
	public static List<Doc> generateDocs(){
		List<Doc> docList=new ArrayList<Doc> ();
		Doc doc1=new Doc();
		doc1.text="二叉树可以分为平衡二叉树与非平衡二叉树";
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
	public List<Double> getFeatureVectorList(){
		for(String str:Term.featureSortedTermSet)
			termToFrequencyMap.put(str,Collections.frequency(termList, str));
		for(String str:Term.featureSortedTermSet)
			featureVectorList.add((double)termToFrequencyMap.get(str));
		return featureVectorList;
	}
	@Override
	public String toString(){
		return termSet.toString();
	}
}
