package com.likeyichu.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.likeyichu.doc.Doc;
import com.likeyichu.token.Token;
import com.likeyichu.token.TokenStatistics;

/** 基于卡方检验的特征选择 */
public class FeatureSelection {
	final static Logger logger = Logger.getLogger(FeatureSelection.class);
	
	/**结构体token的List，无重复，有序，从它里面筛选得到特征空间*/
	private List<Token> tokenList = new ArrayList<Token>();

	/**计算公式为 (AD-BC)*(AD-BC)/((A+B)*(C+D))
	 * A与B不可能同时为0，但C与D可以同时为0，处理方法为此时返回0
	 */
	public void chiSquaretest() {
		
		for (Token token : TokenStatistics.totalTokenSet){
			calcTokenInfo(token);
			tokenList.add(token);
		}
		Collections.sort(tokenList,Collections.reverseOrder());//从大到小排序
	}
	public void showTopNtoken(int n){
		if(tokenList.size()<n)
			logger.info("tokenList.size()<n!");
		for(int i=0;i<n&&i<tokenList.size();i++)
			System.out.println(tokenList.get(i));
	}
	public void getFeatureSortedTokenSet(int n){
		for(int i=0;i<n&&i<tokenList.size();i++)
			TokenStatistics.featureSortedTokenStringList.add(tokenList.get(i).text);
	}
	//calculate Token's information
	private void calcTokenInfo(Token token) {
		for (Doc doc : TokenStatistics.docList) {
			if (doc.tokenSet.contains(token)) {
				if (doc.isPositive)
					token.A++;
				else
					token.B++;
			}
		}
		token.C = TokenStatistics.positiveDocNumber - token.A;
		token.D = TokenStatistics.totalDocNumber - TokenStatistics.positiveDocNumber
				- token.B;
		if (token.C+token.D==0){
			token.chiSquareValue=0;
			return ;
		}
		token.chiSquareValue = (token.A * token.D - token.B * token.C)
				* (token.A * token.D - token.B * token.C)
				/ ((token.A + token.B) * (token.C + token.D));
	}
	
	public static void main(String[] args) {
		TokenStatistics.generateDocListFromTable();
		TokenStatistics.getTotalTokenSet();
		FeatureSelection featureSelection=new FeatureSelection();
		featureSelection.chiSquaretest();
		featureSelection.showTopNtoken(20);
	}
}
