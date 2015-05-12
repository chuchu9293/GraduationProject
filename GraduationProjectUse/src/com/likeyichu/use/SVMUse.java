package com.likeyichu.use;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.likeyichu.train.libsvm.AboutLibSvm;
import com.likeyichu.train.libsvm.PredictResult;



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
}
