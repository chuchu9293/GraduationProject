package com.likeyichu.train.libsvm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.likeyichu.dal.Dao;
import com.likeyichu.doc.DocStatistics;

public class LinuxLibsvm {
	final static Logger logger = Logger.getLogger(LinuxLibsvm.class);

	public static PredictResult predict() throws IOException {
		// 参数依次是 test_file model_file output_file
		String[] testArgs = { "-b", "1", "~/libsvm/gp.predict.txt",
				"d:/libsvm/gp.model", "~/libsvm/gp.predict.result.txt" };
		svm_predict.main(testArgs);
		PredictResult pr = new PredictResult();
		Scanner scanner = new Scanner(new File(
				"~/libsvm/gp.predict.result.txt"));
		/*
		 * 预测结果的形式 labels 1 -1 1.0 0.8527222772187009 0.14727772278129886
		 */
		// 先过滤一行
		scanner.nextLine();
		String str = scanner.nextLine();
		String[] strArr = str.split(" ");
		pr.degree = Double.valueOf(strArr[1]);// 是正样例的概率
		if (strArr[0].contains("-1")) {
			pr.isPositive = false;
			pr.degree = 1 - pr.degree;
		} else
			pr.isPositive = true;

		scanner.close();
		return pr;
	}


	/** 数据库中读取训练集数据,保存为svm可读的格式 */
	public static void transformVectorListFromTableToFile() {
		Dao dao = Dao.getDao();
		// [0.0, 0.0, 0.0, 0.11785113019775793] 这样的形式
		List<String> positiveList = new ArrayList<String>();
		List<String> negativeList = new ArrayList<String>();
		try {
			dao.transformVectorListFromTable(positiveList, negativeList);
		} catch (SQLException e) {
			logger.error("dao.transformVectorListFromTable(positiveList, negativeList);出错"
					+ e.toString());
			return;
		}
		FileWriter fw;
		try {
			fw = new FileWriter("d:/libsvm/gp.train.txt");

			for (String string : positiveList) {
				fw.write(addLabel(string, true));
			}
			for (String string : negativeList) {
				fw.write(addLabel(string, false));
			}
			fw.close();
			logger.info("写入  d:/libsvm/gp.train.txt 成功");
		} catch (IOException e) {
			logger.error("写入 d:/libsvm/gp.train.txt  失败" + e.toString());
		}
	}

	/**
	 * 把[0.0, 0.0, 0.0, 0.11785113019775793] 这样的形式转化为 label 1:0.0 2:0.0 3:0.0 4:
	 * 0.1178 \r\n
	 */
	public static String addLabel(String str, boolean isPositive) {
		String[] strArr = str.substring(1, str.length() - 1).split(",");
		StringBuilder sb = new StringBuilder();
		if (isPositive)
			sb.append("1 ");
		else
			sb.append("-1 ");
		for (int i = 0; i < strArr.length; i++) {
			sb.append(i + 1 + ":" + strArr[i] + " ");
		}
		sb.append("\r\n");
		return sb.toString();
	}

}
