package com.likeyichu.use.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/*读取spring配置得到数据源*/
public class AboutSpring {
	@Test
	public ComboPooledDataSource getDataSource(){
	 ApplicationContext ctx =new FileSystemXmlApplicationContext("beans.xml");  
	 ComboPooledDataSource datasource=ctx.getBean("dataSource", ComboPooledDataSource.class);
	 return datasource;
 }
}
