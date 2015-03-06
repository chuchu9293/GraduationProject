package com.likeyichu.aboutjsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AboutJsoup {



	public static String getText(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		System.out.println(doc.title());
		String text = doc.text();
//		String[] texts = text.split("。|\\.");// 以中文句号或英文句号作为分割
//		for (String string : texts) {
//			System.out.println(string);
//		}
		return text;
	}
}