/**
 * Josh Tran
 * Jenny Le
 * Tommy Tran
 */

package main;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private String dataLocation;
	final String oracleProtocol = "jdbc:oracle:thin"; 
	
	/**
	 * Constructor
	 */
	public DatabaseConnection(String sId) {
		this.dataLocation = "@localhost:1521:" + sId;
	}
	
	public DatabaseConnection(String host, int port, String sId) {
		this.dataLocation = "@" + host + ":" + port + ":" + sId;
	}
	
	/**
	 * Create a connection JDBC
	 */
	public Connection getDatabaseConnection(String username, String password) throws SQLException {
		// Driver registration 
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		String url = oracleProtocol + ':' + dataLocation;
		System.out.println("[TableInfo:] url = " + url);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	
	public Connection getDatabaseConnection() throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Username: ");
		String username = input.nextLine();
		System.out.println("Password: ");
		String password = input.nextLine();
		input.close();
		return getDatabaseConnection(username, password);
	}
}
