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


@Path("predictService")
public class PredictService {
	private Logger logger=Logger.getLogger(PredictService.class);
	public static 	ContentDeal contentDeal=new ContentDeal();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PredictResponse fun()  {
		logger.info("web请求结果");
		//Jsoup->IKAnalyzer->featureList
		contentDeal.deal();
		PredictResponse response=new PredictResponse();
		SVMUse sVmUse=new SVMUse();
		//将文档信息与预测结果拼装成返回结果
		response.calcInfo(contentDeal.doc, sVmUse.predict(contentDeal.doc.featureVectorList));
		response.setStatus("ok");
		return response;
	}
	
	@Path("predictUrl")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String fun2(@QueryParam("url") String url){
		logger.info("请求判断的url是："+url);
		contentDeal.url=url;
		contentDeal.isUrl=true;
		return "ok";
	}
}
