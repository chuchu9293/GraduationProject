package com.likeyichu.train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.likeyichu.doc.Doc;
import com.likeyichu.ikanalyzer.AboutIKAnalyzer;
import com.likeyichu.math.FeatureSelection;

public class Train {
	static int fileNo = 0;

	/** 从文件夹中得到若干个gbk编码的训练样本，每个文件为一篇文档。第一行+1表示内容相关；-1表示不相关。第二行起为正文。 */
	public static void getTrainingSample(String folderPath) {

	}

	/** 从文本文件中读相关url列表 */
	public static List<String> getRelativeUrlsToDownLoad(String filePath)
			throws IOException {
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		List<String> relativeUrlList = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			relativeUrlList.add(scanner.nextLine());
		}
		scanner.close();
		return relativeUrlList;
	}

	/** 不相关的url列表 */
	public static List<String> getIrrelevantUrlsToDownLoad(String filePath)
			throws IOException {

		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		List<String> relativeUrlList = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			relativeUrlList.add(scanner.nextLine());
		}
		scanner.close();
		return relativeUrlList;
	}




}
