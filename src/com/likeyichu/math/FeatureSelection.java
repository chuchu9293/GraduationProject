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

	private List<Term> termList = new ArrayList<Term>();
	/** 卡方检验的结果 */
	private Map<String, Double> chiSquareValueMap = new HashMap<String, Double>();

	public void chiSquaretest() {
		// 计算公式为 (AD-BC)*(AD-BC)/((A+B)*(C+D))
		DocStatistics.getStatistics();
		for (String str : DocStatistics.totalTermSet){
			Term term=new Term(str);
			calcTermInfo(term);
			termList.add(term);
		}
		Collections.sort(termList);
	}
	public void showTopNTerm(int n){
		for(int i=0;i<n;i++)
			System.out.println(termList.get(i));
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
		term.chiSquareValue = (term.A * term.D - term.B * term.C)
				* (term.A * term.D - term.B * term.C)
				/ ((term.A + term.B) * (term.C + term.D));

	}
}
