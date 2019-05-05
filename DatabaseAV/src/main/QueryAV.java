/**
 * Josh Tran
 * Jenny Le
 * Tommy Tran
 */

package main;
import java.awt.print.Printable;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;


public class QueryAV {

	Connection conn; 
	final String host = "dbsvcs.cs.uno.edu"; 
	final int port = 1521;
	final String sID = "orcl";

	// Three database connection link constructors
	public QueryAV(	String host, 
			int port, 
			String sID, 
			String username, 
			String passwd) throws SQLException { 
				conn = new DatabaseConnection(host, port, sID).getDatabaseConnection(username, passwd); 
			}
	public QueryAV(String username, String passwd) throws SQLException { 
		this.conn = new DatabaseConnection(host, port, sID).getDatabaseConnection(username, passwd); 
	}

	public QueryAV(Connection conn) throws SQLException { 
		this.conn = conn; 
	}
	// END OF connection constructors
	

	
	// SETUP of the QUERIES HERE
	
	
	
	
	
	
	
	/** tester
	 * Run Query here!!!
	 * WARNING THESE ARE HARDCODED NEED APP
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
		QueryAV sqObj = new QueryAV(conn);
		// END OF database connection process
		
		// Variables in MAIN
		Boolean quit = false;
		
		
		/**
		 * Loop to run the program 
		 */
		while(!quit) {
			System.out.println("\n\n*****JJT LD Database Java Query Runner*****\n\n");
			System.out.println("Please enter a query number (13-28) or 0 to QUIT: ");
			// give user choice option
			try {
				int choice = scanner.nextInt();
				
				if (choice > 0 && choice > 13) {
					System.out.println("ERROR>>>>> You have entered the value is out of range!");
				}
				else if (choice == ) {
					
				}
				else if (choice == ) {
					
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
		// Closes the scanner
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
