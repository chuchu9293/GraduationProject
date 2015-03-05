package com.likeyichu.doc;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Term implements Comparable<Term> {
	
	/**�õ�������*/
	public String text;
	/** ���� �õ����� ���� ����ĵ��� �ĵ�����*/
	public int A;
	/** ���� �õ����� ������ ����ĵ��� �ĵ�����*/
	public int B;
	/** ������ �õ����� ���� ����ĵ��� �ĵ�����*/
	public int C;
	/** ������ �õ����Ҳ� ���� ����ĵ��� �ĵ�����*/
	public int D;
	/**�õ��ʵĿ���������*/
	public double chiSquareValue;
	/**��ȡ��������������*/
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
		return new String("text="+text+" A="+A+" B="+B+" C="+C+" D="+D);
	}
}
