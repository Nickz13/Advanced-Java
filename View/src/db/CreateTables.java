package db;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
	
	public void createTeamTable(Statement stmt) {
		try {
			String teams = "CREATE TABLE TEAM " +
	                "(PID VARCHAR(20) PRIMARY KEY     NOT NULL," +
	                "Students            VARCHAR(100))";
			stmt.executeUpdate("DROP TABLE IF EXISTS TEAM");
			stmt.executeUpdate(teams);
	         stmt.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Teams Table created successfully");
	}
	
	public void createStudentTable(Statement stmt) {
		try {
			String studentInfo = "CREATE TABLE StudentInfo " +
	                "(ID VARCHAR(20) PRIMARY KEY     NOT NULL," +
	                "Score         VARCHAR(50), " + 
	                "Personality CHAR(20), "+
	                "Conflict VARCHAR(50))";
			stmt.executeUpdate("DROP TABLE IF EXISTS StudentInfo");
			stmt.executeUpdate(studentInfo);
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Students Table created successfully");
		
	}
	
	public void createProjectTable(Statement stmt) {
		try {
			String projects = "CREATE TABLE Project " +
	                "(title           TEXT," +
	                "ID VARCHAR(20) PRIMARY KEY     NOT NULL," +
	                "Description            VARCHAR(50), " + 
	                "Project_Owner        VARCHAR(20), " + 
	                "Skills         VARCHAR(60))";
			stmt.executeUpdate("DROP TABLE IF EXISTS Project");
			stmt.executeUpdate(projects);
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Project Table created successfully");
		
	}
}
