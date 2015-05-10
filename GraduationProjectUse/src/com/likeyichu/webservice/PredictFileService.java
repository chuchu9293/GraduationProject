package com.likeyichu.webservice;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.likeyichu.jsoup.AboutJsoup;
import com.likeyichu.use.ContentDeal;
import com.likeyichu.use.PredictResult;
import com.likeyichu.use.SVMUse;

@Path("predictFileService")
public class PredictFileService {
	private Logger logger=Logger.getLogger(PredictFileService.class);
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UrlResponse urlResponseFun(@Context HttpServletRequest request) throws Exception {
		UrlResponse response=new UrlResponse();
		dealUploadFile(request);
//		logger.info("该url对应的标题为："+response.getTitle());
//		
		
//		ContentDeal contentDeal=new ContentDeal();
//		PredictResult svmResult=new SVMUse().predict(contentDeal.getVectorList());
//		response.setResult(svmResult.isPositive?"是":"否");
//		response.setDegree(String.valueOf(svmResult.degree));
		response.setResult("ok");
		return response;
	}
	
	//使用apache-FileUpload工具处理上传文件请求
	void dealUploadFile( HttpServletRequest request) throws Exception{
		//开辟本地临时位置用来中转文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//用来解析HttpServletRequest中的文件
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> items = null;
		//下句执行后已经得到文件，放在临时位置
		items = upload.parseRequest(request);
		//允许上传多个文件，需要遍历
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = iter.next();
		    item.getSize();//文件的大小，字节
		    logger.info("predictUrlService请求的file为："+item.getName());//文件名字
			File uploadedFile = new File("d:/upload.jpg");//不限于jpg，可根据上传文件的名字的后缀来动态确定后缀
			//临时文件写入到我们指定的位置
			item.write(uploadedFile);
		}
	}
}
