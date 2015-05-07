package com.likeyichu.doc;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.likeyichu.ikanalyzer.AboutIKAnalyzer;

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
	
	public static List<Doc> addDocListFromLocal(String folderPath){
		File theFolderPath =new File(folderPath) ;
		File[] fileArr=theFolderPath.listFiles(new MyFileFilter());
		for (File file : fileArr) {
			try {
				docList.add(Doc.generateDocFromFile(file));
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("解析一篇file出错");
			}
		}
		return docList;
	}
	public static void getStatistics(){
		for (Doc doc : docList) {
			if(doc.isRelative)
				relativeDocNumber++;
			totalTermSet.addAll(doc.termSet);
		}
		totalDocNumber=docList.size();
	}
}
class MyFileFilter implements FilenameFilter{

	@Override
	public boolean accept(File dir, String name) {
		if(name.toLowerCase().endsWith("txt"))
			return true;
		return false;
	}
	
}