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


@Path("urlService")
public class UrlService {
	private Logger logger=Logger.getLogger(UrlService.class);
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UrlResponse urlResponseFun(@QueryParam("url") String url) throws IOException {
		StringBuilder sb=new StringBuilder();
		String content=AboutJsoup.getTextFromURL(url, sb);
		UrlResponse response=new UrlResponse();
		response.setContent(content);
		response.setTitle(sb.toString());
		logger.info("UrlService请求的url为："+url);
		logger.info("UrlService返回的doc标题为："+response.getTitle());
		return response;
	}
	
	@Path("post")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)  //因为这行，urlResponseFun()的形参request会被jersey注入
	public UrlResponse urlResponseFun2 ( UrlRequest request) throws IOException {
		
		StringBuilder sb=new StringBuilder();
		String content=AboutJsoup.getTextFromURL(request.getUrl(), sb);
		UrlResponse response=new UrlResponse();
		response.setContent(content);
		response.setTitle(sb.toString());
		logger.info("UrlService请求的url为："+request.getUrl());
		logger.info("UrlService返回的doc标题为："+response.getTitle());
		return response;
	}
}
