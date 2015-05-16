package com.likeyichu.token;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Token implements Comparable<Token> {
	
	/**该单词内容*/
	public String text;
	
	/** 包含 该单词且 属于 相关文档的 文档个数*/
	public int A;
	/** 包含 该单词且 不属于 相关文档的 文档个数*/
	public int B;
	/** 不包含 该单词且 属于 相关文档的 文档个数*/
	public int C;
	/** 不包含 该单词且不 属于 相关文档的 文档个数*/
	public int D;
	
	/**该单词的卡方检验结果*/
	public double chiSquareValue;
	
	
	public Token(String str){
		this.text=str.trim();
	}

	public int compareTo(Token o) {
		return (int) (chiSquareValue-o.chiSquareValue);
	}
	
	@Override
	public String toString(){
		return new String("text="+text+" ,A="+A+" B="+B+" C="+C+" D="+D+" ,chiSquarevalue:"+chiSquareValue);
	}
	
	@Override
	public boolean equals(Object o){
		return o instanceof Token && text.equals(((Token)o).text);
	}
	@Override
	public int hashCode() {
		return text.hashCode();
	}
	
	public static void main(String[] args) {
		 Set<Token> totalTokenSet=new HashSet<Token>();
		 List<Token> list=new ArrayList<Token> ();
		 list.add(new Token("a")); list.add(new Token("b")); list.add(new Token("a"));
		 totalTokenSet.addAll(list);
		 
		 System.out.println(totalTokenSet.size());
	}
}
/**2
 */
