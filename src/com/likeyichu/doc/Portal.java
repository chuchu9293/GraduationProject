package com.likeyichu.doc;

import com.likeyichu.math.FeatureSelection;

public class Portal {

	public static void main(String[] args) {
		FeatureSelection f=new FeatureSelection();
		f.chiSquaretest();
		f.showTopNTerm(4);
	}

}
