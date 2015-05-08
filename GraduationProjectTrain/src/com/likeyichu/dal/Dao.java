package com.likeyichu.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.likeyichu.spring.AboutSpring;

public class Dao {
	static DataSource dataSource;
	static Connection connection;
	final static Logger logger = Logger.getLogger(Dao.class);
	
	void initDataSource(){
		dataSource=new AboutSpring().getDataSource();
	}
	void checkConnection(){
		try {
			if(connection==null||connection.isClosed())
				connection = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("得到connection失败" + e.toString());
		}
	}
	public void insertTokenList(int id,String title, String tokenList,boolean isPositive) {
		if (isPositive)
			insertPositiveToken(id, title, tokenList);
		else
			insertPositiveToken(id, title, tokenList);
	}
	/**查询指定表中no列的最大值，*/
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
	/**positiveToken*/
	void insertPositiveToken(int id, String title, String tokenList) {
		checkConnection();
		String sql = "insert into `token_positive_table` (no,id,title,tokenList,time) values (?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("token_positive_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setString(4, tokenList);
			ps.setString(5, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入token_positive_table失败" + e.toString());
		}
	}
	/**negativeToken*/
	void insertNegativeToken(int id, String title, String tokenList)  {
		checkConnection();
		String sql = "insert into `token_positive_table` (no,id,title,tokenList,time) values (?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("token_negative_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setString(4, tokenList);
			ps.setString(5, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入token_negative_table失败" + e.toString());
		}
	}
	
	public void insertVectorList(int id,String title, String tokenList,boolean isPositive) {
		if (isPositive)
			insertPositiveToken(id, title, tokenList);
		else
			insertPositiveToken(id, title, tokenList);
	}
	/**positiveVector*/
	void insertPositiveVectorList(int id, String title, String vectorList) {
		checkConnection();
		String sql = "insert into `vector_positive_table` (no,id,title,vector,time) values (?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("vector_positive_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setString(4, vectorList);
			ps.setString(5, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入vector_positive_table失败" + e.toString());
		}
	}
	/**negativeVector*/
	void insertNegativeVectorList(int id, String title, String vectorList) {
		checkConnection();
		String sql = "insert into `vector_negative_table` (no,id,title,vector,time) values (?,?,?,?,?) ";
		PreparedStatement ps;

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format1.format(new Date());
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, maxNo("vector_negative_table") + 1);
			ps.setInt(2, id);
			ps.setString(3, title);
			ps.setString(4, vectorList);
			ps.setString(5, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.error("插入vector_negative_table失败" + e.toString());
		}
	}

}
