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
	public QueryAZ(String host, int port, String sID, String username, String passwd) throws SQLException {
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
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String[]> queryOne() throws SQLException {
		String str = "SELECT first_name,last_name " + "FROM Person NATURAL JOIN Job " + "ORDER BY last_name ASC";
		ArrayList<String[]> al = new ArrayList<String[]>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		while (rs.next()) {
			String[] line = new String[2];
			line[0] = rs.getString("first_name");
			line[1] = rs.getString("last_name");
			al.add(line);
		}
		return al;
	}

	/**
	 * Query 2 setup
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String[]> queryTwo() throws SQLException {
		String str = "SELECT first_name, last_name, pay_rate " + "FROM Person NATURAL JOIN Job "
				+ "WHERE pay_type='salary' " + "ORDER BY pay_rate DESC";
		ArrayList<String[]> al = new ArrayList<String[]>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		while (rs.next()) {
			String[] line = new String[3];
			line[0] = rs.getString("first_name");
			line[1] = rs.getString("last_name");
			line[2] = Float.toString(rs.getFloat("pay_rate"));
			al.add(line);
		}
		return al;
	}

	/**
	 * Query 3 setup TODO: fix with clause issue
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String[]> queryThree() throws SQLException {
		String str = "WITH salary AS (\n" + "WITH salary AS (\n" + "SELECT  store_id, AVG (pay_rate) AS avg_pay\n"
				+ "FROM Job NATURAL JOIN Store\n" + "WHERE pay_type = 'salary'\n" + "GROUP BY store_id\n" + "),\n"
				+ "\n" + "hours AS (\n" + "SELECT store_id, AVG (pay_rate*1920) AS avg_pay "
				+ "	FROM Job NATURAL JOIN Store " + "WHERE pay_type = ‘wage’ " + "GROUP BY store_id " + ") "
				+ "labor_cost  AS ( " + "SELECT * FROM salary " + "UNION " + "SELECT * FROM hours " + ") "
				+ "SELECT store_id, avg_pay " + "FROM labor_cost " + "GROUP BY store_id " + "ORDER BY avg_pay DESC ";
		ArrayList<String[]> al = new ArrayList<String[]>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		while (rs.next()) {
			String[] line = new String[2];
			line[0] = rs.getString("store_id");
			line[1] = Float.toString(rs.getFloat("avg_pay"));
			al.add(line);
		}
		return al;
	}

	/**
	 * Query 4 setup
	 * 
	 * @param pos_code
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String[]> queryFour(String pos_code) throws SQLException {
		String str = "SELECT pos_code, sk_code " + "FROM Require " + "WHERE pos_code = ? "
				+ "GROUP BY pos_code, sk_code ";
		ArrayList<String[]> al = new ArrayList<String[]>();
		PreparedStatement pStmt = conn.prepareStatement(str);
		pStmt.setString(1, pos_code);
		ResultSet rs = pStmt.executeQuery();
		while (rs.next()) {
			String[] line = new String[2];
			line[0] = rs.getString("pos_code");
			line[1] = rs.getString("sk_code");
			al.add(line);
		}
		return al;
	}
	
	public ArrayList<String[]> queryFive(String per_id) throws SQLException {
		String str = "SELECT title, sk_code " + 
				"FROM Has_skill NATURAL JOIN Skill " + 
				"WHERE per_id = ?;";
		ArrayList<String[]> al = new ArrayList<String[]>();
		PreparedStatement pStmt = conn.prepareStatement(str);
		pStmt.setString(1, per_id);
		ResultSet rs = pStmt.executeQuery();
		while (rs.next()) {
			String[] line = new String[2];
			line[0] = rs.getString("title");
			line[1] = rs.getString("sk_code");
			al.add(line);
		}
		return al;
	}

	/**
	 * tester Run Query here!!!
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
		while (!quit) {
			System.out.println("\n\n*****JJT AZ Database Java Query Runner*****\n\n");
			System.out.println("Please enter a query number (1-12) or 0 to QUIT: ");
			// give user choice option
			try {
				int choice = scanner.nextInt();

				if (choice > 0 && choice > 13) {
					System.out.println("ERROR>>>>> You have entered the value is not in range!");
				} else if (choice == 1) {
					System.out.println("Running Query 1");
					System.out.println("List the workers by name in the alphabetical order of last names");
					System.out.println("first_name\t\tlast_name");
					ArrayList<String[]> str = sqObj.queryOne();
					for (String[] line : str) {
						System.out.printf("%s\t\t%s\n\n", line[0], line[1]);
					}
				} else if (choice == 2) {
					System.out.println("Running Query 2");
					System.out.println("List the staff (salary workers) by salary in descending order");
					System.out.println("frsit_name\t\tlast_name\t\tpay_rate");
					ArrayList<String[]> str = sqObj.queryTwo();
					for (String[] line : str) {
						System.out.printf("%s\t\t%s\t\t%s\n\n", line[0], line[1], line[2]);
					}
				} else if (choice == 3) {
					System.out.println("Running Query 3");
					System.out.println(
							"List the average annual pay (the salary or wage rates multiplying by 1920 hours) of each stores in descending order");
					System.out.println("fac_id\t\tfac_name\t\t\tavg_pay");
					ArrayList<String[]> str = sqObj.queryThree();
					for (String[] line : str) {
						System.out.printf("%s\t\t%s\n\n", line[0], line[1]);
					}
				} else if (choice == 4) {
					System.out.println("Running Query 4");
					System.out.println("List the required skills of a given pos_code in a readable format.");
					System.out.println("Enter a position code: ");
					String ans = getAnswerString();
					System.out.println("pos_code\tsk_code");
					ArrayList<String[]> str = sqObj.queryFour(ans);
					for (String[] line : str) {
						System.out.printf("%s\t\t%s\t\t", line[0], line[1]);
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
