package com.likeyichu.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class Dao implements BeanFactoryAware{
	final static Logger logger=Logger.getLogger(Dao.class);
	private static DataSource  dataSource;
	private static Connection connection;
	void update(String ip) throws SQLException, ClientProtocolException, IOException{
		String sql = "update `qing-WebTable` set ip_info=? where ip="+"\""+ip+"\"";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		String str=IPUtil.getIpInfo(ip);
		preparedStatement.setString(1,str);
		preparedStatement.executeUpdate();
		logger.info(ip+str);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		dataSource=beanFactory.getBean("dataSource", DataSource.class);
		logger.info("setBeanFactory被触发，得到bean：dataSource");
	}
	 public static void main(String[] args) throws SQLException {
		 Dao dao=new Dao();
		 if(connection==null||connection.isClosed())
				connection =dataSource.getConnection();
		 Statement statement=connection.createStatement();
		 ResultSet rs=statement.executeQuery("SELECT * FROM `qing-WebTable`");
		 while(rs.next()){
			 String ip=rs.getString("ip");
			 String ipInfo=rs.getString("ip_info");
			 try {
				 //if(ipInfo==null||ipInfo.length()<2)
				dao.update(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
	} 
}
