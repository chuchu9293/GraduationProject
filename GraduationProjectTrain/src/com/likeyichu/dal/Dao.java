package com.likeyichu.dal;

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

import com.likeyichu.doc.Doc;
import com.likeyichu.doc.WebPage;
import com.likeyichu.spring.AboutSpring;
import com.likeyichu.token.TokenStatistics;

/**单例模式*/
public class Dao {
	final static Logger logger = Logger.getLogger(Dao.class);
	
	static DataSource dataSource;
	static Connection connection;

	private static Dao dao=null;
	
	/**在私有构造函数中初始化数据库信息，*/
	private Dao(){
		if(dataSource==null)
			dataSource=new AboutSpring().getDataSource();
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
	/**从数据库中读取所有的数据*/
	public List<WebPage> generateWebPageList() throws SQLException{
		List<WebPage> webPageList=new ArrayList<WebPage>();
		checkConnection();
		Statement sm = connection.createStatement();
		int i=0;
			//ResultSet rs1 = sm.executeQuery("select * from `collect_positive_table` where no between 1 and 100");//2000条数据时，这里会耗时100秒
		ResultSet rs1 = sm.executeQuery("select * from `collect_positive_table` ");//2000条数据时，这里会耗时100秒
			while(rs1.next()){
				WebPage webPage=new WebPage();
				webPage.id=rs1.getInt("id");
				webPage.title=rs1.getString("title");
				webPage.content=rs1.getString("content");
				webPage.isPositive=true;
				webPageList.add(webPage);
				System.out.println(i++);
			}
			rs1.close();
		
		int positiveNum=webPageList.size();
		logger.info("从collect_positive_table拿到数据个数："+positiveNum);
		sm = connection.createStatement();
		ResultSet rs = sm.executeQuery("select * from `collect_negative_table`");
			while(rs.next()){
				WebPage webPage=new WebPage();
				webPage.id=rs.getInt("id");
				webPage.title=rs.getString("title");
				webPage.content=rs.getString("content");
				webPage.isPositive=false;
				webPageList.add(webPage);
			}
			rs.close();
			logger.info("从collect_negative_table拿到数据个数："+(webPageList.size()-positiveNum));
		return webPageList;
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
	public void insertPositiveToken(int id, String title, String tokenList) {
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
	public void insertNegativeToken(int id, String title, String tokenList)  {
		checkConnection();
		String sql = "insert into `token_negative_table` (no,id,title,tokenList,time) values (?,?,?,?,?) ";
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
	
	public void insertVectorList(int id,String title, String vectorList,boolean isPositive) {
		if (isPositive)
			insertPositiveVectorList(id, title, vectorList);
		else
			insertNegativeVectorList(id, title, vectorList);
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
	
	public List<Doc>   getDocListFromTokenTable() throws SQLException{
		 List<Doc> docList =new ArrayList<Doc>();
		 checkConnection();
			Statement sm = connection.createStatement();
			ResultSet rs = sm.executeQuery("select * from `token_positive_table`");
			while(rs.next()){
				Doc doc=new Doc();
				doc.id=rs.getInt("id");
				doc.title=rs.getString("title");
				doc.tokenTokenList=doc.transferTokenListStringToList(rs.getString("tokenList"));
				doc.tokenSet.addAll(doc.tokenTokenList);
				doc.isPositive=true;
				docList.add(doc);
			}
			rs.close();
			int positiveNum=docList.size();
			logger.info("从token_positive_table拿到数据个数："+positiveNum);
			sm = connection.createStatement();
			 rs = sm.executeQuery("select * from `token_negative_table`");
				while(rs.next()){
					Doc doc=new Doc();
					doc.id=rs.getInt("id");
					doc.title=rs.getString("title");
					doc.tokenTokenList=doc.transferTokenListStringToList(rs.getString("tokenList"));
					doc.tokenSet.addAll(doc.tokenTokenList);
					doc.isPositive=false;
					docList.add(doc);
				}
				rs.close();
				logger.info("从token_negative_table拿到数据个数："+(docList.size()-positiveNum));
		 return docList;
	}
	/**提取后的特征空间，插入数据库*/
	public  void insertFeatureSortedTokenStringListToTable(List<String> list){
		 checkConnection();
			String sql = "insert into `non_relational_table` (theKey,theValue,time) values (?,?,?) ";
			PreparedStatement ps;

			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sqlDate = format1.format(new Date());
			try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, "featureList");
				ps.setString(2, list.toString());
				ps.setString(3, sqlDate);
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				logger.error("插入non_relational_table失败" + e.toString());
			}
	}
	
	/**数据库中读取训练集数据
	 * @throws SQLException */
	public void transformVectorListFromTable(List<String> positiveList,List<String> negativeList) throws SQLException{
		 checkConnection();
			Statement sm = connection.createStatement();
			sm.setQueryTimeout(60*10);
			ResultSet rs = sm.executeQuery("select vector from `vector_positive_table` limit 0,1000");
			 int i=0;
			while(rs.next()){
				System.out.println(i++);
				String str=rs.getString("vector");
				positiveList.add(str);
			}
			rs.close();
			logger.info("从vector_positive_table拿到数据个数："+positiveList.size());
			
			 rs = sm.executeQuery("select * from `vector_negative_table`");
			
				while(rs.next()){
					System.out.println(i++);
					String str=rs.getString("vector");
					negativeList.add(str);
				}
				rs.close();
				logger.info("从vector_negative_table拿到数据个数："+negativeList.size());
	}
	
}
