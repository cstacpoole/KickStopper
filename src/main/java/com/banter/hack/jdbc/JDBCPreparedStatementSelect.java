package com.banter.hack.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.banter.hack.scrape.Project;

public class JDBCPreparedStatementSelect {
	
	public static void loadProjectAttFromOurTable(Project projectObject) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "SELECT COUNT(*) AS NUMBER_OF_TIMES_BACKED,SUM(ANTIPLEDGED) AS TOTAL_ANTIPLEDGED  FROM PROJECTS WHERE ID = ?";
		System.out.println(selectSQL + " :::" + projectObject.getId());
		try {
			dbConnection = GetJDBCConnection.getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, projectObject.getId());

			// execute select SQL statement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int timesBacked = rs.getInt("NUMBER_OF_TIMES_BACKED");
				int totalAntiPledged = rs.getInt("TOTAL_ANTIPLEDGED");
				
				projectObject.setAntiPledge(totalAntiPledged);
				projectObject.setAntiBackers(timesBacked);

				System.out.println("timesBacked : " + timesBacked);
				System.out.println("totalAntiPledged : " + totalAntiPledged);

			}

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

	
	

	
	
}
