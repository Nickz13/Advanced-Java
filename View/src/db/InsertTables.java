package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import Model.*;

public class InsertTables {
	private ProjectHandler pro;
	public InsertTables() throws ClassNotFoundException, IOException {
		pro = new ProjectHandler();
	}
	
	public void insertTeams(Connection con) throws SQLException {
		try {
			PreparedStatement pst = con.prepareStatement("insert into TEAM (PID, Students) values(?,?)");
			
			for (Map.Entry mapElement : pro.getTeams().entrySet()) {
				String key = (String)mapElement.getKey();
				Team value = (Team)mapElement.getValue();
				pst.setString(1, key);
				pst.setString(2, value.getStudentIDs());
				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
		}
	}
	
	public void insertProjects(Connection con) throws SQLException {
		try {
			PreparedStatement pst = con.prepareStatement("insert into Project (Title, ID, Description, Project_Owner, Skills) values(?,?,?,?,?)");
			
			for (Map.Entry mapElement : pro.getProj().entrySet()) {
				String key = (String)mapElement.getKey();
				Project value = (Project)mapElement.getValue();
				pst.setString(1, value.getTitle());
				pst.setString(2, key);
				pst.setString(3, value.getDesc());
				pst.setString(4, value.getOid());
				pst.setString(5, value.getSkill());
				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
		}
	}
	
	public void insertStudents(Connection con) throws SQLException {
		try {
			PreparedStatement pst = con.prepareStatement("insert into StudentInfo (ID, Score, Personality, Conflict) values(?,?,?,?)");
			
			for (Map.Entry mapElement : pro.getStudInfo().entrySet()) {
				String key = (String)mapElement.getKey();
				StudentInfo value = (StudentInfo)mapElement.getValue();
				pst.setString(1, key);
				pst.setString(2, value.getScore());
				pst.setString(3, value.getpersonalityType());
				pst.setString(4, value.getStudents());
				pst.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
	        System.exit(0);
		}
	}
}
