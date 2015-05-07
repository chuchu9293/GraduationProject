package com.likeyichu.webservice;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;


@Path("collectService")
public class CollectService {
	private Logger logger=Logger.getLogger(CollectService.class);
	@Path("post")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)  
	public  CollectResponse  collectFun ( CollectRequest request) throws IOException {
		CollectResponse response=new CollectResponse();
		logger.info("collectService请求的文档title为："+request.getTitle());
		logger.info("collectService收集成功"+response.getDocId());
		return response ;
	}
}
