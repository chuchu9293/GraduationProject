package com.likeyichu.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.likeyichu.doc.Doc;
import com.likeyichu.doc.DocStatistics;
import com.likeyichu.doc.Term;

/** 基于卡方检验的特征选择 */
public class FeatureSelection {
	/**结构体Term的List*/
	private List<Term> termList = new ArrayList<Term>();

	/**计算公式为 (AD-BC)*(AD-BC)/((A+B)*(C+D))
	 * A与B不可能同时为0，但C与D可以同时为0，处理方法为此时返回0
	 */
	public void chiSquaretest() {
		
		for (String str : DocStatistics.totalTermSet){
			Term term=new Term(str);
			calcTermInfo(term);
			termList.add(term);
		}
		Collections.sort(termList,Collections.reverseOrder());//从大到小排序
	}
	public void showTopNTerm(int n){
		if(termList.size()<n)
			System.out.println("termList.size()<n!");
		for(int i=0;i<n&&i<termList.size();i++)
			System.out.println(termList.get(i));
	}
	public void getFeatureSortedTermSet(int n){
		for(int i=0;i<n&&i<termList.size();i++)
			Term.featureSortedTermSet.add(termList.get(i).text);
	}
	
	private void calcTermInfo(Term term) {
		for (Doc doc : DocStatistics.docList) {
			if (doc.termSet.contains(term.text)) {
				if (doc.isRelative)
					term.A++;
				else
					term.B++;
			}
		}
		term.C = DocStatistics.relativeDocNumber - term.A;
		term.D = DocStatistics.totalDocNumber - DocStatistics.relativeDocNumber
				- term.B;
		if (term.C+term.D==0){
			term.chiSquareValue=0;
			return ;
		}
		term.chiSquareValue = (term.A * term.D - term.B * term.C)
				* (term.A * term.D - term.B * term.C)
				/ ((term.A + term.B) * (term.C + term.D));
		

	}
}
