package com.likeyichu.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Doc {
	/**��ȡ������  */
	public String text;
	/**��Դ��ҳ��url  */
	public String url;
	/**�������е����ķִʽ���������ظ� */
	public List<String> termList=new ArrayList<String>();
	/**��Ƶ��ӳ��*/
	public Map<String,Integer> termToFrequencyMap=new HashMap<String,Integer>();
	/**���ķִʽ����set�����ڿ��������еĿ��ټ���*/
	public Set<String> termSet=new HashSet<String>();
	/**�Ƿ�Ϊ �㷨ʵ�� ������� */
	public boolean isRelative=false;
	/**��������*/
	public List<Double> featureVectorList=new ArrayList<Double>();
	
	public static List<Doc> generateDocs(){
		List<Doc> docList=new ArrayList<Doc> ();
		Doc doc1=new Doc();
		doc1.text="���������Է�Ϊƽ����������ƽ�������";
		doc1.termList.add("������");doc1.termList.add("����");
		doc1.termList.add("ƽ��");doc1.termList.add("ƽ��");
		doc1.termSet.addAll(doc1.termList);
		doc1.isRelative=true;
		
		Doc doc2=new Doc();
		doc2.text="�������ȸ���ڵ��߸��϶���";
		doc2.termList.add("����");doc2.termList.add("��ȸ");
		doc2.termList.add("���߸�");doc2.termList.add("����");
		doc2.termSet.addAll(doc2.termList);
		doc2.isRelative=false;
		
		docList.add(doc1); docList.add(doc2);
		return docList;
	}
	public static List<Doc> generateDocsTest(){
		List<Doc> docList=new ArrayList<Doc> ();
		Doc doc1=new Doc();
		doc1.text="���������Է�Ϊƽ����������ƽ�������";
		doc1.termList.add("������");doc1.termList.add("����");
		doc1.termList.add("ƽ��");doc1.termList.add("ƽ��");
		doc1.termSet.addAll(doc1.termList);
		doc1.isRelative=true;
		
		Doc doc2=new Doc();
		doc2.text="�������ȸ���ڵ��߸��϶���";
		doc2.termList.add("����");doc2.termList.add("��ȸ");
		doc2.termList.add("���߸�");doc2.termList.add("����");
		doc2.termSet.addAll(doc2.termList);
		doc2.isRelative=false;
		
		docList.add(doc1); docList.add(doc2);
		return docList;
	}
	public void getFeatureVectorList(){
		for(String str:Term.featureSortedTermSet)
			if(termSet.contains(str))
				featureVectorList.add(1D);
			else
				featureVectorList.add(0D);
	}
	@Override
	public String toString(){
		return termSet.toString();
	}
}
