package com.likeyichu.doc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DocStatistics {
	/**�ĵ��б�  */
	public static List<Doc> docList=Doc.generateDocsTest();
	/** ���ĵ����� */
	public static int totalDocNumber;
	/** ����ĵ����� */
	public static int relativeDocNumber;
	/** ���еķִʽ��,����*/
	public static Set<String> totalTermSet=new HashSet<String>();
	
	public static void getStatistics(){
		for (Doc doc : docList) {
			if(doc.isRelative)
				relativeDocNumber++;
			totalTermSet.addAll(doc.termSet);
		}
		totalDocNumber=docList.size();
	}
}
