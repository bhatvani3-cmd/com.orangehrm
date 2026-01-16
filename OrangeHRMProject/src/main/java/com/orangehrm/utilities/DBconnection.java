package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseClass;

public class DBconnection {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	private static final Logger logger = BaseClass.logger;

	public static Connection getDBconnection() {

		try {
			logger.info("Starting Database connection...");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info(" Database connection successful");
			return conn;
		} catch (SQLException e) {
			logger.info(" Database connection failed");
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, String> getEmployeeDetails(String empID) {
		String query = "SELECT emp_firstname,emp_middle_name,emp_lastname FROM hs_hr_employee WHERE employee_id="
				+ empID;

		Map<String, String> employeeDetails = new HashMap<String, String>();

		try (Connection conn = getDBconnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			logger.info("Executing query " + query);
			if (rs.next()) {
				String firstName = rs.getString("emp_firstname");
				String middleName = rs.getString("emp_middle_name");
				String lastName = rs.getString("emp_lastname");

				// store it in map
				employeeDetails.put("firstName", firstName);
				employeeDetails.put("middleName", middleName != null ? middleName : "");
				employeeDetails.put("lastName", lastName);
				logger.info("Query executed");
				logger.info("Exployee details fetched " + employeeDetails);
			} else {
				logger.error("Employee not found");
			}
		} catch (Exception e) {
			logger.error("Error while executing the query");
			e.printStackTrace();
		}
		return employeeDetails;
	}
}
