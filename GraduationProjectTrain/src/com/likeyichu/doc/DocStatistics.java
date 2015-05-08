package com.likeyichu.doc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.likeyichu.doc.Doc;

public class DocStatistics {
	/**文档列表  */
	public static List<Doc> docList=new  ArrayList<Doc>();
	
	/** 总文档个数 */
	public static int totalDocNumber;
	
	/** 相关文档个数 */
	public static int positiveDocNumber;
	
	/** 所有的分词结果,有序*/
	public static Set<String> totalTermSet=new HashSet<String>();
	
	public static void getStatistics(){
		for (Doc doc : docList) {
			if(doc.isPositive)
				positiveDocNumber++;
			totalTermSet.addAll(doc.termSet);
		}
		totalDocNumber=docList.size();
	}
}
