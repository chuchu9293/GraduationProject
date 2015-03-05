package com.likeyichu.doc;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Term implements Comparable<Term> {
	
	/**该单词内容*/
	public String text;
	/** 包含 该单词且 属于 相关文档的 文档个数*/
	public int A;
	/** 包含 该单词且 不属于 相关文档的 文档个数*/
	public int B;
	/** 不包含 该单词且 属于 相关文档的 文档个数*/
	public int C;
	/** 不包含 该单词且不 属于 相关文档的 文档个数*/
	public int D;
	/**该单词的卡方检验结果*/
	public double chiSquareValue;
	/**提取出来的特征单词*/
	public static Set<String> featureSortedTermSet=new TreeSet<String>();
	
	public Term(String test){
		this.text=test;
	}

	@Override
	public int compareTo(Term o) {
		return (int) (chiSquareValue-o.chiSquareValue);
	}
	@Override
	public String toString(){
		return new String("text="+text+" A="+A+" B="+B+" C="+C+" D="+D+"chiSquarevalue:"+chiSquareValue);
	}
}
