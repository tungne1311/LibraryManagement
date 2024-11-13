/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB implements DatabaseInfo{
	public static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("connect successfully");
		} catch(Exception e)
		{
			System.out.println("connect failure");
			e.printStackTrace();
		}
		return conn;
	}
}