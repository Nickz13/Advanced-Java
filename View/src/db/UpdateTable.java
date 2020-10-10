package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import application.*;
import Model.Team;

public class UpdateTable {

	public void updateTeam(Team team) {
		System.out.println("pid"+team.getProjectID());
		System.out.println(team.getStudentIDs());
		try {
			Main m = new Main();
			m.initializeDB();

			Connection conn = m.getConnection();
			//			String sql = "UPDATE Team set Students ="+ (String)team.getStudentIDs()+"where PID="+(String)team.getProjectID()+";";
			//			System.out.println("mystring"+sql);
			String query = "update Team set Students = ? where PID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, (String)team.getStudentIDs());
			preparedStmt.setString(2, (String)team.getProjectID());
			preparedStmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
