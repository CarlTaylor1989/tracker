package com.acolos.acolostracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class LoadDriver {

	public static void init(String[] args) throws SQLException, InterruptedException {
		Connection conn = null;
		Statement stmt = null;

		byte[] decodedBytes = Base64.getDecoder().decode(Reference.ENCODED_STRING);
		String decodedString = new String(decodedBytes);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        	System.out.println("Driver Load Failed: " + e.getMessage());
        }
        
        try {
        	System.out.println("Establishing a connection to tracker database...");
        	conn = DriverManager.getConnection(decodedString);
        } catch (SQLException e) {
        	System.out.println("Connection Failed!:\n" + e.getMessage());
        }
 
        if (conn != null) {
            System.out.println("Successfully connected to tracker database.");
            insertStatement(conn, args);
        } else {
            System.out.println("Failed to connect to tracker database.");
        }
    }

	private static void insertStatement(Connection conn, String[] args) throws SQLException {
		try {
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO playerDeaths (name, deathBy, day, time) VALUES ('" + args[0] + "', '" + args[1] + "', '" + args[2] + "', '" + args[3] + "')"; 

			stmt.executeUpdate(sql);
			System.out.println("Successfully saved data to tracker database.");
			conn.close();
		} catch (SQLException e) {
			System.out.println("Failed to save data to tracker database.\n" + e.getMessage());
			conn.close();
		}
	}

}
