package edu.colostate.cs.cs414.a3.jam.db;

import java.sql.*;

// Base DB class provides common database functionality.
public class BaseDB 
{
	private Connection connection;
	
	public BaseDB() 
	{		
		this.connection = null;
		try {
			this.connection = getConnection();
			System.out.println("Constructor got connect");
		} catch (SQLException e) {
			connection = null;
		}
	}
	
	protected Connection getConnection() throws SQLException 
	{
		String workingDirectory = System.getProperty("user.dir");
		String dbURL = "jdbc:sqlite:" + workingDirectory + "\\src\\edu\\colostate\\cs\\cs414\\a3\\jam\\db\\GymManagementSystem.sqlite";
		System.out.println("Connecting to ..." + dbURL);
		return DriverManager.getConnection(dbURL);
	}  // end getCpnnection 
	
	public PreparedStatement getPreparedStatement (String sql) throws SQLException
	{
		if (this.connection == null)
		{
			connection = getConnection();
		}
		
		
		PreparedStatement ps = null;
		try {
			System.out.println("In prepare statement before connection.prepareStatement");
			ps = connection.prepareStatement(sql);
			System.out.println("After prepareStatement");
		} catch (SQLException e) {
			System.out.println("Error with prepareStatement");
			System.out.println(e);
		}
		return ps;
	}  // end getPreparedStatement

} // end BaseDB class
