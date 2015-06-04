package com.likeyichu.webservice;

import java.util.List;

import com.likeyichu.use.PredictResponse;

public class predictUrlListResponse {
	public List<PredictResponse> getUrlListResponseList() {
		return urlListResponseList;
	}

	public void setUrlListResponseList(List<PredictResponse> urlListResponseList) {
		this.urlListResponseList = urlListResponseList;
	}

	List<PredictResponse> urlListResponseList;
}
