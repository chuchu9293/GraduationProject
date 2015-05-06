package com.likeyichu.train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.likeyichu.aboutikanalyzer.AboutIKAnalyzer;
import com.likeyichu.aboutjsoup.AboutJsoup;
import com.likeyichu.doc.Doc;
import com.likeyichu.doc.DocStatistics;
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

	public static void downLoadHtmlList(List<String> relativeUrlList,
			boolean isRelative) {
		for (String string : relativeUrlList) {
			try {
				downLoadHtml(string, isRelative);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("1篇下载失败");
			}
		}
	}

	/**
	 * 将url指定的网页保存到本地。第一行+1表示内容相关；-1表示不相关。第二行起为正文。
	 * 
	 * @throws IOException
	 */
	public static void downLoadHtml(String url, boolean isRelative)
			throws IOException {
		StringBuilder title = new StringBuilder();
		String text = AboutJsoup.getTextFromURL(url, title);
		if (isRelative)
			text = "+1\r\n" + text;
		else
			text = "-1\r\n" + text;
		createNewFile(title.toString(), text, isRelative);
	}

	/**
	 * 创建文本文件到c:\GP\
	 * 
	 * @throws IOException
	 */
	public static void createNewFile(String title, String text,
			boolean isRelative) throws IOException {
		File Folder;
		File file;
		if (isRelative) {
			Folder = new File("c:\\GP\\downloadFiles\\relative\\");
			file = new File("c:\\GP\\downloadFiles\\relative\\" + fileNo++
					+ "_" + title + ".txt");
		} else {
			Folder = new File("c:\\GP\\downloadFiles\\irrelevant\\");
			file = new File("c:\\GP\\downloadFiles\\irrelevant\\" + fileNo++
					+ "_" + title + ".txt");
		}
		// 如果文件夹不存在
		if (!Folder.exists()) {
			Folder.mkdirs();
		}
		// 创建文件
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(text + "\r\n");
		fw.close();
	}

	public static void main(String[] args) throws IOException {

	}

}
