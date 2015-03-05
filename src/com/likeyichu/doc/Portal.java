package com.likeyichu.doc;

import com.likeyichu.math.FeatureSelection;

public class Portal {

	public static void main(String[] args) {
		FeatureSelection f=new FeatureSelection();
		f.chiSquaretest();
		f.getFeatureSortedTermSet(4);
		f.showTopNTerm(400);
		for (Doc doc : DocStatistics.docList) {
			System.out.println(doc.getFeatureVectorList());
		}
	}

}
