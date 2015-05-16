package com.likeyichu.use.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.likeyichu.doc.Doc;
import com.likeyichu.doc.WebPage;
import com.likeyichu.token.TokenStatistics;
import com.likeyichu.use.spring.AboutSpring;
import com.likeyichu.webservice.PredictService;
import com.likeyichu.webservice.predictUrlListRequest;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**单例模式*/
@Component
public class Dao implements BeanFactoryAware { 
	final static Logger logger = Logger.getLogger(Dao.class);
	
	public static 	 ComboPooledDataSource dataSource;
	static Connection connection;

	private static Dao dao=null;
	
	/**在私有构造函数中初始化数据库信息，*/
	private Dao(){
	}
	
	static void checkConnection(){
		try {
			if(connection==null||connection.isClosed())
				connection = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("得到connection失败" + e.toString());
		}
	}
	public static Dao getDao(){
		if(dao==null)
			dao=new Dao();
		return dao;
	}
	
	/**从数据库中读取特征空间，为String格式*/
	public String generateFeatureTokenStringListStringFromTable() throws SQLException{
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm.executeQuery("select * from `non_relational_table`");
		while(rs.next()){
			if(rs.getString("theKey").equalsIgnoreCase("featureList"))
				return rs.getString("theValue");
		}
		rs.close();
		return null;
	}
	

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		dataSource = beanFactory.getBean("dataSource", 	 ComboPooledDataSource.class);
		logger.info("setBeanFactory被触发，得到bean：dataSource");
	
	}
	
	public void acuracyTest(	List<String> urlList,	List<Integer> labelList) throws SQLException{
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm.executeQuery("select URL from `collect_positive_table` limit 0,20");
		while(rs.next()){
			urlList.add(rs.getString("URL"));
			labelList.add(1);
		}
		rs = sm.executeQuery("select URL from `collect_negative_table` limit 0,20");
		while(rs.next()){
			urlList.add(rs.getString("URL"));
			labelList.add(-1);
		}
	}
	
	
}
