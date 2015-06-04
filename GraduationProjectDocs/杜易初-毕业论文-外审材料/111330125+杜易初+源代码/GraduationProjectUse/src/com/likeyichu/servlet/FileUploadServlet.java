package com.likeyichu.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.likeyichu.webservice.PredictService;


/**
 * Servlet implementation class FileUpload
 */
public class FileUploadServlet extends HttpServlet {
	private Logger logger=Logger.getLogger(FileUploadServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			File htmlFile=dealUploadFile(request);
			PredictService.contentDeal.filePath=htmlFile.getAbsolutePath();
			PredictService.contentDeal.isUrl=false;
		} catch (Exception e) {
			 logger.error("获取上传文件失败"+e.toString());
		}
		response.sendRedirect("/GraduationProjectUse/views/use.html");
	}
	File dealUploadFile( HttpServletRequest request) throws Exception{
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
		File uploadedFile = new File("/root/gp/upload.html");//不限于jpg，可根据上传文件的名字的后缀来动态确定后缀
		while (iter.hasNext()) {
		    FileItem item = iter.next();
		    item.getSize();//文件的大小，字节
		    logger.info("predictFileService请求的file为："+item.getName());//文件名字
			//临时文件写入到我们指定的位置
			item.write(uploadedFile);
			
		}
		return uploadedFile;
	}
}
