import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {
	/*
	 * private static final	String DRIVER="com.mysql.jdbc.Driver";
			//urlָ��Ҫ���ʵ���ݿ���jdwx
		private static final	String URL="jdbc:mysql://localhost:3306/paqu?useUnicode=true&characterEncoding=utf-8";
			//mysql ����ʱ����û���
		private static final	String USER="root";
			//mysql���õ�����
		private static final	String PASSWORD="selsm9wna5001654j0m9";
		
		private static Connection connection;  
	    private static java.sql.PreparedStatement pstmt;  
	    private static ResultSet resultSet;  
	 */
	//驱动
	private static final	String DRIVER="com.mysql.jdbd.Driver";
	private static final	String URL="jdbc:mysql://183.2.170.38:3306/paqu?useUnicode=true&characterEncoding=utf-8";
	private static final	String USER="root";
	private static final	String PASSWORD="selsm9wna5001654j0m9";
	
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
