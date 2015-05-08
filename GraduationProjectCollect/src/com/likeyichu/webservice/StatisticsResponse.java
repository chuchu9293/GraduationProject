package com.likeyichu.webservice;

public class StatisticsResponse {
	int total;
	int positiveTotal,positiveUrl,positiveLocal;
	int negativeTotal,negativeUrl,negativeLocal;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPositiveTotal() {
		return positiveTotal;
	}
	public void setPositiveTotal(int positiveTotal) {
		this.positiveTotal = positiveTotal;
	}
	public int getPositiveUrl() {
		return positiveUrl;
	}
	public void setPositiveUrl(int positiveUrl) {
		this.positiveUrl = positiveUrl;
	}
	public int getPositiveLocal() {
		return positiveLocal;
	}
	public void setPositiveLocal(int positiveLocal) {
		this.positiveLocal = positiveLocal;
	}
	public int getNegativeTotal() {
		return negativeTotal;
	}
	public void setNegativeTotal(int negativeTotal) {
		this.negativeTotal = negativeTotal;
	}
	public int getNegativeUrl() {
		return negativeUrl;
	}
	public void setNegativeUrl(int negativeUrl) {
		this.negativeUrl = negativeUrl;
	}
	public int getNegativeLocal() {
		return negativeLocal;
	}
	public void setNegativeLocal(int negativeLocal) {
		this.negativeLocal = negativeLocal;
	}
	
}
