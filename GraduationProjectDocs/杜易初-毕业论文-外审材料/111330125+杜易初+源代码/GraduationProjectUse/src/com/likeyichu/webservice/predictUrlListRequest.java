package com.likeyichu.webservice;

import java.util.List;

public class predictUrlListRequest {
	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	public List<Integer> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Integer> labelList) {
		this.labelList = labelList;
	}
	
	List<String> urlList;
	List<Integer> labelList;
}
