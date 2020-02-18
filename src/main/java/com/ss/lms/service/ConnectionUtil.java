package com.ss.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true";
	private String username = "root";
	private String password = "Tq6~MM'b";

	public Connection connectDatabase() throws SQLException, ClassNotFoundException {
		// 1. Register Driver.
		Class.forName(driver);
		// 2. Connection
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(false);
		return conn;
	}
}
