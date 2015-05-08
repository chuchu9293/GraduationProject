package com.likeyichu.spring;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/*读取spring配置得到数据源*/
public class AboutSpring {
	@Test
	public DataSource getDataSource(){
	 ApplicationContext ctx =new FileSystemXmlApplicationContext("beans.xml");  
	 DataSource datasource=ctx.getBean("dataSource", DataSource.class);
	 return datasource;
 }
}
