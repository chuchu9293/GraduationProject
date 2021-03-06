package com.likeyichu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.likeyichu.webservice.StatisticsResponse;

@Component
public class Dao implements BeanFactoryAware {
	final static Logger logger = Logger.getLogger(Dao.class);
	private static DataSource dataSource;
	
	private static Connection connection;
	public  static Set<String> urlSet = new HashSet<String>();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		dataSource = beanFactory.getBean("dataSource", DataSource.class);
		logger.info("setBeanFactory被触发，得到bean：dataSource");
		try {
			initSet();
			logger.info("初始化urlSet成功");
		} catch (SQLException e) {
			logger.error("初始化urlSet失败" + e.toString());
		}
	}
	public  DataSource getDataSource() {
		return dataSource;
	}
	public  void setDataSource(DataSource dataSource) {
		Dao.dataSource = dataSource;
	}
	void checkConnection(){
		try {
			if(connection==null||connection.isClosed())
				connection = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("得到connection失败" + e.toString());
		}
	}

	void initSet() throws SQLException {
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm
				.executeQuery("select URL from `collect_positive_table`");
		while (rs.next()) {
			urlSet.add(rs.getString("URL"));
		}
		rs.close();
		rs = sm.executeQuery("select URL from `collect_negative_table`");
		while (rs.next()) {
			urlSet.add(rs.getString("URL"));
		}
		logger.info("urlSet的尺寸为"+urlSet.size());
		rs.close();
	}

	int maxNo(String tableName) throws SQLException {
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm.executeQuery("select max(no) from " + tableName);
		rs.next();// 这行不能少
		int result = rs.getInt(1);
		rs.close();
		sm.close();
		return result;
	}

	int maxId() throws SQLException {
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm
				.executeQuery("select max(id) from `collect_positive_table`");
		rs.next();
		int result1 = rs.getInt(1);
		rs = sm.executeQuery("select max(id) from `collect_negative_table`");
		rs.next();
		int result2 = rs.getInt(1);
		rs.close();
		sm.close();
		return Math.max(result1, result2);
	}

	public int insert(String url, String title, String content,
			boolean isPositive) {
		int id = -1;
		// 若有重复url，不予插入
		if (urlSet.contains(url))
			return -1;
		urlSet.add(url);
		try {
			id=maxId()+1;
		} catch (SQLException e) {
			logger.error("查询maxId()失败:"+e.toString());
		}
		if (isPositive)
			insertPositive(id, url, title, content);
		else
			insertNegative(id, url, title, content);
		return id;
	}

	void insertPositive(int id, String url, String title, String content) {
		checkConnection();
		String sql = "insert into `collect_positive_table` (no,id,title,isURL,URL,isLocal,content,time) values (?,?,?,?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("collect_positive_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setBoolean(4, true);
			ps.setString(5, url);
			ps.setBoolean(6, false);
			ps.setString(7, content);
			ps.setString(8, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入collect_positive_table失败" + e.toString());
		}
	}
	public Set<String> localPathathSet() throws SQLException{
		Set<String> set=new HashSet<String>();
		checkConnection();
		Statement sm = connection.createStatement();
		ResultSet rs = sm
				.executeQuery("select path from `collect_positive_table`");
		while(rs.next()){
			set.add(rs.getString("path"));
		}
		return set;
	}
	public void insertPositiveLocal(String title, String path,  String content) {
		checkConnection();
		String sql = "insert into `collect_positive_table` (no,id,title,isURL,isLocal,path,content,time) values (?,?,?,?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("collect_positive_table") + 1);
			ps.setInt(2, maxId()+1);
			ps.setString(3, title);
			ps.setBoolean(4, false);
			ps.setBoolean(5, true);
			ps.setString(6, path);
			ps.setString(7, content);
			ps.setString(8, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("(Local)插入collect_positive_table失败" + e.toString());
		}
	}

	void insertNegative(int id, String url, String title, String content) {
		checkConnection();
		String sql = "insert into `collect_negative_table` (no,id,title,isURL,URL,isLocal,content,time) values (?,?,?,?,?,?,?,?)";
		PreparedStatement ps;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("collect_negative_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setBoolean(4, true);
			ps.setString(5, url);
			ps.setBoolean(6, false);
			ps.setString(7, content);
			ps.setString(8, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入collect_negative_table失败" + e.toString());
		}
	}
	public StatisticsResponse statistics(){
		StatisticsResponse response=new StatisticsResponse();
		checkConnection();
		try{
		Statement sm = connection.createStatement();
		
		//查询collect_positive_table
		String sql = "select count(id) from `collect_positive_table` ";
		ResultSet rs = sm.executeQuery(sql);
		rs.next();
		response.setPositiveTotal(rs.getInt(1));
		rs.close();
		
		sql=sql+"where isURL=1";
		rs = sm.executeQuery(sql);
		rs.next();
		response.setPositiveUrl(rs.getInt(1));
		rs.close();
		response.setPositiveLocal(response.getPositiveTotal()-response.getPositiveUrl());
		
		
		//查询collect_negative_table
		sql="select count(id) from `collect_negative_table` ";
		rs = sm.executeQuery(sql);
		rs.next();
		response.setNegativeTotal(rs.getInt(1));
		rs.close();
		sql=sql+"where isURL=1";
		rs = sm.executeQuery(sql);
		rs.next();
		response.setNegativeUrl(rs.getInt(1));
		rs.close();
		response.setNegativeLocal(response.getNegativeTotal()-response.getNegativeUrl());
		}catch(SQLException e){
			logger.error("统计collect_positive_table与collect_negative_table失败");
		}
		response.setTotal(response.getNegativeTotal()+response.getPositiveTotal());
		return response;
	}
}
