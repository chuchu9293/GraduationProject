package com.likeyichu.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class IPUtil {
	static String utf8Deal(String str){
		//此时str是:{"country":"\u4e2d\u56fd" 这样的形式，需要转换
				Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
				Matcher m = reUnicode.matcher(str);
				StringBuffer sb = new StringBuffer(str.length());
				while (m.find()) {
					m.appendReplacement(sb,
							Character.toString((char) Integer.parseInt(m.group(1), 16)));
				}
				m.appendTail(sb);
				return sb.toString();
	}
	static String httpGet(String ip) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response1 = null;
		StringBuilder sb = new StringBuilder();
		try {
			HttpGet httpGet = new HttpGet(
					"http://api.map.baidu.com/location/ip?ak=3GFi2F04wXaVuwmGu8fN49kL&ip=" + ip);
			response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			InputStream instream = entity1.getContent();
			String s = null;

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			while ((s = reader.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			EntityUtils.consume(entity1);
			return sb.toString();
		} finally {
			response1.close();
		}
	}
	public static String getIpInfo(String ip) throws ClientProtocolException, IOException{
		String str=httpGet(ip);
		if(str.contains("\"status\":200"))
			return "error";
		int pos1=str.indexOf(":");
		int pos2=str.indexOf(",");
		str=str.substring(pos1+2, pos2-1);
		str=IPUtil.utf8Deal(str);
		String [] strArray=str.split("\\|");//注意regex里"|"也是特殊字符
		String country=strArray[0].equals("CN")?"中国":strArray[0];
		String province=strArray[1];
		String city=strArray[2];
		String isp=strArray[4];
		switch(isp){
		case "CMNET":
			isp="移动";
			break;
		case "CHINANET":
			isp="电信";
			break;
		case "UNICOM":
			isp="联通";
		}
		
		return country+","+province+","+city+","+isp;
	}
	public void test() throws ClientProtocolException, IOException{
		System.out.println(getIpInfo("120.218.27.2"));
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println(IPUtil.getIpInfo("120.218.27.2"));
	}
}
