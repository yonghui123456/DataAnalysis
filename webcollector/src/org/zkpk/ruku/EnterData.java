package org.zkpk.ruku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnterData {
	static Connection connection=null;
	static PreparedStatement pstmt=null;
	/*
	 * 入库
	 */
	public static void insertData(String platform,String xinhao, String title,
			String content, String memberlevel, String fromplatform, String area,
			String userimpression,String color,String price,String productSize,
			String creationTime,String zhuaqutime,String label_result) throws SQLException{
		
		/*这里的jd_db.spider是我的jd_db数据库中的spider表，
		 * 这里需要改为你自己的数据中对应的表*/
		String sql = "insert into jd_db.spider(platform,xinhao,title,content,memberlevel,fromplatform,area,userimpression," +
					"color,price,productSize,creationTime,zhuaqutime,lable) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		pstmt=connection.prepareStatement(sql);
		pstmt.setString(1,platform);
		pstmt.setString(2,xinhao);
		pstmt.setString(3,title);
		pstmt.setString(4,content);
		pstmt.setString(5,memberlevel);
		pstmt.setString(6,fromplatform);
		pstmt.setString(7,area);
		pstmt.setString(8,userimpression);
		pstmt.setString(9,color);
		pstmt.setString(10,price);
		pstmt.setString(11,productSize);
		pstmt.setString(12,creationTime);
		pstmt.setString(13,zhuaqutime);
		pstmt.setString(14,label_result);
		pstmt.executeUpdate();
	}
	public static void getconnection() throws SQLException{
		connection = DataBase.getConnection();
	}
	public static void close() throws SQLException{
		connection.close();
		pstmt.close();
	}
}
