/**
 * Josh Tran
 * Jenny Le
 * Tommy Tran
 */

package main;
import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;


public class QueryAZ {

	Connection conn; 
	final String host = "dbsvcs.cs.uno.edu"; 
	final int port = 1521;
	final String sID = "orcl";

	// Three database connection link constructors
	public QueryAZ(	String host, 
			int port, 
			String sID, 
			String username, 
			String passwd) throws SQLException { 
				conn = new DatabaseConnection(host, port, sID).getDatabaseConnection(username, passwd); 
			}
	public QueryAZ(String username, String passwd) throws SQLException { 
		this.conn = new DatabaseConnection(host, port, sID).getDatabaseConnection(username, passwd); 
	}

	public QueryAZ(Connection conn) throws SQLException { 
		this.conn = conn; 
	}
	// END OF connection constructors
	
	
	// Query Setup
		/**
		 * Query 1 Setup
		 * @return
		 * @throws SQLException
		 */
		public ArrayList<String[]> queryOne() throws SQLException {
			String str = "SELECT first_name, last_name " + 
					"FROM Person NATURAL JOIN Works  " + 
					"WHERE end_date IS NOT NULL " + 
					"ORDER BY last_name ASC";
			ArrayList<String[]> al = new ArrayList<String[]>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(str);
			while(rs.next()) {
				String[] line = new String[2];
				line[0] = rs.getString("first_name");
				line[1] = rs.getString("last_name");
				al.add(line);
			}
			return al;
		}
	
	
	
	
	
	
	/** tester
	 * Run Query here!!!
	 */
	public static void main(String[] args) throws SQLException {
		
		// Start of the connection to database by asking for username and password
		if (args.length == 1) {
			System.out.println("usage: java SampleQuery db-IP dp-SID"); 
			System.exit(1);
		} 
		DatabaseConnection dbc; 
		if (args.length == 0)
			dbc = new DatabaseConnection("dbsvcs.cs.uno.edu", 1521, "orcl"); 
		else 
			dbc = new DatabaseConnection(args[0], 1521, args[1]); 
		Scanner scanner = new Scanner(System.in);
		System.out.println("User Name: ");
		String username = scanner.nextLine();
		System.out.println("passcode: ");
		String dbpassword = scanner.nextLine(); 
		Connection conn = dbc.getDatabaseConnection(username, dbpassword); 
		QueryAZ sqObj = new QueryAZ(conn);
		// END OF database connection process
		
		// Variables in MAIN
		Boolean quit = false;
		
		
		/**
		 * Loop to run the program 
		 */
		while(!quit) {
			System.out.println("\n\n*****JJT AZ Database Java Query Runner*****\n\n");
			System.out.println("Please enter a query number (1-12) or 0 to QUIT: ");
			// give user choice option
			try {
				int choice = scanner.nextInt();
				
				if (choice > 0 && choice > 13) {
					System.out.println("ERROR>>>>> You have entered the value is not in range!");
				}
				else if (choice == 1) {
					ArrayList<String[]> str = sqObj.queryOne();
					for (String[] line : str) {
						System.out.printf("first_name\tlast_name\n%s\t\t%s\n\n", line[0], line[1]);
					}
				}
				// This else is to check if use want to QUIT
				else if (choice == 0) {
					System.out.println("Quiting program...");
					quit = true;
				}
				
			} catch (InputMismatchException e) {
				System.out.println("ERROR>>>> the value must be an integer\n");
				e.printStackTrace();
				quit = true;
			}
		}
		// Closes the Scanner
		scanner.close();
	}
	
	/**
	 * 
	 * @return answer: which is the user input to the query as String
	 */
	public static String getAnswerString() {
		// Create new Scanner 
		Scanner sc = new Scanner(System.in);
		String answer = sc.nextLine();
//		sc.close();
		return answer;
	}
	
	/**
	 * 
	 * @return answer: which is the user input to the query as integer
	 */
	public static int getAnswerInt() {
		// Create new Scanner 
		Scanner sc = new Scanner(System.in);
		int answer = sc.nextInt();
//		sc.close();
		return answer;
	}
}
