package com.likeyichu.aboutjsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AboutJsoup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = null;
		try {
			doc = Jsoup
					.connect(
							"http://blog.csdn.net/chuchus/article/details/23205283").userAgent("Mozilla")
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(doc.title());
		String text=doc.text();
		String [] texts=text.split("。|\\.");//以中文句号或英文句号作为分割
		for (String string : texts) {
			System.out.println(string);
		}
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
}