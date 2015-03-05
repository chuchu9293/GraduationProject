package com.likeyichu.doc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.likeyichu.aboutikanalyzer.AboutIKAnalyzer;

public class DocStatistics {
	/**文档列表  */
	public static List<Doc> docList=new  ArrayList<Doc>();
	/** 总文档个数 */
	public static int totalDocNumber;
	/** 相关文档个数 */
	public static int relativeDocNumber;
	/** 所有的分词结果,有序*/
	public static Set<String> totalTermSet=new HashSet<String>();
	
	public static List<String> urlList=new ArrayList<String>();
	public static void getStatistics(){
		//docList=Doc.generateDocsTest();
		urlList.add("http://blog.csdn.net/chuchus/article/details/23205283");
		urlList.add("http://blog.chinaunix.net/uid-10508451-id-2950845.html");
		docList=Doc.generateDocListFromUrlList(urlList);
		docList.get(0).isRelative=true;
		for (Doc doc : docList) {
			if(doc.isRelative)
				relativeDocNumber++;
			totalTermSet.addAll(doc.termSet);
		}
		totalDocNumber=docList.size();
	}
}
