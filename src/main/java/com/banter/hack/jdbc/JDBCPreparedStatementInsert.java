package com.banter.hack.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.banter.hack.scrape.Project;


public class JDBCPreparedStatementInsert {

	public static void insertRecordIntoTable(Project projectObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO PROJECTS"
				+ "(ID,  ANTIBACKERSCOUNT, ANTIPLEDGED ) VALUES"
				+ "(?,?,?)";

		try {
			dbConnection = GetJDBCConnection.getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, projectObject.getId());
//			preparedStatement.setString(2, projectObject.getTitle());
//			preparedStatement.setString(3, projectObject.getDescription());
//			preparedStatement.setString(4, projectObject.getAuthor());
//			preparedStatement.setInt(5, projectObject.getBackers());
			preparedStatement.setInt(2, projectObject.getAntiBackers());
//			preparedStatement.setInt(7, projectObject.getPledge());
			preparedStatement.setInt(3, projectObject.getAntiPledge());
//			preparedStatement.setDouble(9, projectObject.getGoal()); //TODO
//			preparedStatement.setInt(10, projectObject.getAntiGoal());
			//had to remove some columns for last minute fix
			
			// execute insert SQL statement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	

	public static void insertUserPledge(String projectId, String pledgeAmount) throws SQLException {

		Project project = new Project();
		project.setId(Integer.parseInt(projectId));
		try {
		project.setAntiPledge(Integer.parseInt(pledgeAmount));
		} catch(NumberFormatException nf) {
		}
		
		JDBCPreparedStatementInsert.insertRecordIntoTable(project);
		
	}
}
