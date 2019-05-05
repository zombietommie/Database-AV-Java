/**
 * Josh Tran
 * Jenny Le
 * Tommy Tran
 */

package main;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataParser {

	static void test1(String input) {
		System.out.printf("input string: %s%n",input);
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate date = LocalDate.parse(input, formatter);
		    System.out.printf("LocalDate: %s%n", date);
		    java.sql.Date sqlDate = java.sql.Date.valueOf(date); 
		    System.out.printf("java.sql.Date: %s%n", sqlDate);
		}
		catch(DateTimeParseException exc) {
			System.out.printf("%s is not parsable!%n", input);
		    throw exc; 
		}
	}
	
	static public void main(String [] args) {
		test1(args[0]);
	}
}
