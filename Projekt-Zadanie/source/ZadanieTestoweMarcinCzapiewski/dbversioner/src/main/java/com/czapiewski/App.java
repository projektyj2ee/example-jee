package com.czapiewski;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Hello world!
 * 
 */
public class App {
	private static String dbURL = "jdbc:derby://localhost:1527/jdbc:derby:sun-appserv-samples;create=true;user=APP;password=APP";
	// jdbc Connection
	private static Connection conn = null;

	public static void main(String[] args) {
		createConnection();

		System.out.println("Hello World!");
	}

	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
}
