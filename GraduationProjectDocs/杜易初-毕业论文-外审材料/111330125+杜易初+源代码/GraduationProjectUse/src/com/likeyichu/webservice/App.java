package com.likeyichu.webservice;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {
	public  App() {
		//向jersey框架注册资源类，凡完全限定名是以指定字符串开头的类，都将包含
		packages("com.likeyichu.webservice");
		register(JacksonFeature.class);
	}
}
