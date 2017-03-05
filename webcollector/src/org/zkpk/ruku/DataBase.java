package org.zkpk.ruku;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {
	
	/*这里给出jdbc驱动名，连接mysql的url,mysql用户名和密码
	 * 这里的url需要改为你的mysql的url,用户名和密码都要改为你的数据库用户名和密码*/
	private static final	String DRIVER="com.mysql.jdbc.Driver";
	private static final	String URL="jdbc:mysql://192.168.153.128:3306/jd_db?useUnicode=true&characterEncoding=utf-8";
	private static final	String USER="hadoop";
	private static final	String PASSWORD="hadoop";
	
	private static Connection connection;
	private static PreparedStatement pastemt;
	private static ResultSet resultSet;
	
	public static Connection getConnection() throws SQLException{
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}
	
	public static void closeConnection(Connection c,Statement s,ResultSet r) throws SQLException{
		c.close();
		s.close();
		r.close();
	}
}
