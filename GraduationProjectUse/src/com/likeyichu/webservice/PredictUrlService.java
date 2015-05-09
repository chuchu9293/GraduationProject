package com.likeyichu.webservice;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.use.ContentDeal;
import com.likeyichu.use.PredictResult;
import com.likeyichu.use.SVMUse;


@Path("predictUrlService")
public class PredictUrlService {
	private Logger logger=Logger.getLogger(PredictUrlService.class);
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UrlResponse urlResponseFun(@QueryParam("url") String url) throws IOException {
		StringBuilder sb=new StringBuilder();
		String content=AboutJsoup.getTextFromURL(url, sb);
		UrlResponse response=new UrlResponse();
		response.setTitle(sb.toString());
		response.setContent(content);
		
		logger.info("predictUrlService请求的url为："+url);
		logger.info("该url对应的标题为："+response.getTitle());
		
		
		ContentDeal contentDeal=new ContentDeal();
		PredictResult svmResult=new SVMUse().predict(contentDeal.getVectorList());
		response.setResult(svmResult.isPositive?"是":"否");
		response.setDegree(String.valueOf(svmResult.degree));
		
		return response;
	}
	
}
