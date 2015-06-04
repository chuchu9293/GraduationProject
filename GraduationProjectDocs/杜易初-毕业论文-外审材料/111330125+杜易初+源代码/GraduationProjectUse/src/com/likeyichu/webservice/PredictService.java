package com.likeyichu.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.likeyichu.doc.Doc;
import com.likeyichu.ikanalyzer.AboutIKAnalyzer;
import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.token.TokenStatistics;
import com.likeyichu.train.libsvm.PredictResult;
import com.likeyichu.use.ContentDeal;
import com.likeyichu.use.PredictResponse;
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
	
	
	@Path("predictUrlList")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public predictUrlListResponse fun3(predictUrlListRequest  req) throws IOException{
		if(req.getLabelList()==null){
			List<Integer> list=new ArrayList<Integer>();
			for (String str : req.getUrlList()) {
				list.add(1);
			}
			req.setLabelList(list);
		}
		predictUrlListResponse response=new predictUrlListResponse();
		response.setUrlListResponseList(new ArrayList<PredictResponse>());
		Doc doc;StringBuilder sb;
		List<Doc> docList=new ArrayList<Doc>();
		List<List<Double>> vectorListList=new ArrayList<List<Double>>();
		for (String url  : req.getUrlList()) {
			doc=new Doc();sb=new StringBuilder();
			try{
			doc.content=AboutJsoup.getTextFromURL(url, sb);
			doc.tokenList=AboutIKAnalyzer.getTokenList(	doc.content);
			}catch(IOException e){
				doc.content="无内容";
			}
			doc.title=sb.toString();
			if(TokenStatistics.featureSortedTokenStringList==null||TokenStatistics.featureSortedTokenStringList.size()<1)
				ContentDeal.generateFeatureTokenStringListFromTable();
			vectorListList.add(doc.getFeatureVectorList());
			docList.add(doc);
		}
		List<PredictResult> predictResultList= SVMUse.predictBatch(vectorListList,req.getLabelList());
		int right=0;
		for(int i=0;i<req.getLabelList().size();i++){
			if(predictResultList.get(i).isPositive && req.getLabelList().get(i)==1)
				right++;
			if(!predictResultList.get(i).isPositive && req.getLabelList().get(i)==-1)
				right++;
		}
		double accuracy=(double)(right)/req.getLabelList().size();
		logger.info("预测的准确性为，"+accuracy);
		for(int i=0;i<docList.size();i++){
			doc=docList.get(i);
			PredictResponse responseSub=new  PredictResponse();
			responseSub.setTitle(doc.title);
			responseSub.setResult(predictResultList.get(i).isPositive?"yes":"no");
			responseSub.setDegree(String.valueOf(predictResultList.get(i).degree));
			response.getUrlListResponseList().add(responseSub);
		}
		return response;
	}

}
