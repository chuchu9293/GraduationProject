package com.likeyichu.use;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.likeyichu.train.libsvm.AboutLibSvm;
import com.likeyichu.train.libsvm.PredictResult;
import com.likeyichu.train.libsvm.svm_predict;
import com.likeyichu.use.dal.Dao;
import com.likeyichu.use.spring.AboutSpring;
import com.likeyichu.webservice.PredictService;
import com.likeyichu.webservice.predictUrlListRequest;



public class SVMUse {
	private static Logger logger=Logger.getLogger(SVMUse.class);
	
	public PredictResult predict(List<Double> doubleList){
		String str=AboutLibSvm.addLabel(doubleList.toString(),true);
		PredictResult predictResult=null;
		//写入到待预测文件中去
		FileWriter fw ;
		try {
			 fw = new FileWriter("d:/libsvm/gp.predict.txt");
			 fw.write(str);
			 fw.close();
		}
		catch (IOException e) {
			logger.error("写入 d:/libsvm/gp.predict.txt  失败"+e.toString());
		}
		try {
			predictResult=AboutLibSvm.predict();
		} catch (IOException e) {
			logger.error("AboutLibSvm.predict();失败"+e.toString());
		}
		return predictResult;
	}
	static String addLabel(List<Double> vectorList,boolean isPositive){
		StringBuilder sb=new StringBuilder();
		if(isPositive)
			sb.append("1 ");
		else
			sb.append("-1 ");
		for(int i=0;i<vectorList.size();i++){
			sb.append(i+1).append(":").append(vectorList.get(i)).append(" ");
		}
		sb.append("\r\n");
		
		return sb.toString();
	}
	/**批量预测*/
	public  static List<PredictResult> predictBatch(List<List<Double>> doubleListList,List<Integer> labelList) throws IOException {
		List<PredictResult> response=new ArrayList<PredictResult>();
		FileWriter fw;
		try {
			fw = new FileWriter("d:/libsvm/gp.predict.txt");
			for(int i=0;i<doubleListList.size();i++){
				fw.write(addLabel(doubleListList.get(i), labelList.get(i)==1));
			}
			fw.close();
			logger.info("批量写入  d:/libsvm/gp.predict.txt 成功");
		} catch (IOException e) {
			logger.error("批量写入 d:/libsvm/gp.predict.txt  失败" + e.toString());
		}
		// 参数依次是 test_file model_file output_file
		String[] testArgs = { "-b", "1", "d:/libsvm/gp.predict.txt",
				"d:/libsvm/gp.model", "d:/libsvm/gp.predict.result.txt" };
		svm_predict.main(testArgs);
		PredictResult pr = new PredictResult();
		Scanner scanner = new Scanner(new File(
				"d:/libsvm/gp.predict.result.txt"));
		/*
		 * 预测结果的形式 labels 1 -1 1.0 0.8527222772187009 0.14727772278129886
		 */
		// 先过滤一行
		scanner.nextLine();
		while(scanner.hasNextLine()){
			String str = scanner.nextLine();
			String[] strArr = str.split(" ");
			pr.degree = Double.valueOf(strArr[1]);// 是正样例的概率
			if (strArr[0].contains("-1")) {
				pr.isPositive = false;
				pr.degree = 1 - pr.degree;
			} else
				pr.isPositive = true;
			response.add(pr);
		}
		scanner.close();
		return response;
	}
	
	//批量预测，准确性统计
	public static void main(String[] args) throws IOException {
		Dao dao=Dao.getDao();
		dao.dataSource=new AboutSpring().getDataSource();
		predictUrlListRequest request=new predictUrlListRequest();
		List<String> urlList=new ArrayList<String>();
		List<Integer> labelList=new ArrayList<Integer>();
		request.setUrlList(urlList);
		request.setLabelList(labelList);
		try {
			dao.acuracyTest(urlList,labelList);
			
			PredictService obj=new PredictService();
			obj.fun3(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
