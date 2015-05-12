package com.likeyichu.use;

import com.likeyichu.doc.Doc;
import com.likeyichu.train.libsvm.PredictResult;

public class PredictResponse {
	private String title;
	private String content;
	private String token;
	private String vector;
	
	private String result;
	private String degree;
	
	private String status;
	
	
	public void calcInfo(Doc doc,PredictResult predictResult){
		title=doc.title;
		content=doc.content;
		token=doc.tokenList.toString();
		vector=doc.featureVectorList.toString();
		
		result=predictResult.isPositive?"yes":"no";
		degree=String.valueOf(predictResult.degree);
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getVector() {
		return vector;
	}
	public void setVector(String vector) {
		this.vector = vector;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}