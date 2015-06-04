package com.likeyichu.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.likeyichu.dao.Dao;
import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.spider.Spider;


@Path("spiderService")
public class spiderService {
	private static Logger logger=Logger.getLogger(spiderService.class);
	private static Spider spider=new Spider();
	
	@Path("setInitialUrl")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String fun1(@QueryParam("initialUrl") String initialUrl){
		spider.setInitialUrl(initialUrl);
		logger.info("spider.setInitialUrl()成功:"+initialUrl);
		return "true";
	}
	
	@Path("nextUrl")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String fun2(){
		String str=spider.next();
		logger.info("spider.next():"+str);
		return str;
	}
}
