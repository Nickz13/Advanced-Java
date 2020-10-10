package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Team;
import db.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private CreateTables create = new CreateTables();
	private InsertTables insert;
	private Statement stmt;
	private Connection connection;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			initializeDB();
			select();
//			create.createTeamTable(stmt);
//			create.createProjectTable(stmt);
//			create.createStudentTable(stmt);
//			insert = new InsertTables();
//			insert.insertTeams(connection);
//			insert.insertProjects(connection);
//			insert.insertStudents(connection);
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,600,600);
			root.prefHeightProperty().bind(scene.heightProperty());
            root.prefWidthProperty().bind(scene.widthProperty());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initializeDB() {
		String url = "jdbc:sqlite:C:/sqlite/db/major.db";
	    try {  connection = DriverManager.getConnection(url);
	           stmt = connection.createStatement();
	           System.out.println("Connection Established");
	    }
	    catch (Exception ex) { ex.printStackTrace(); System.exit(0);}

	}
	
	public Statement getStmt() {
		return stmt;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	
	public void select(){
		   try {
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Team;" );
		      
		      while ( rs.next() ) {
		         String id = rs.getString("PID");
		         String st = rs.getString("Students");
		         System.out.println( "Project ID = " + id );
		         System.out.println( "Students = " + st );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
		   System.out.println("Operation done successfully");
		  }
	
	public static void main(String[] args) {
		launch(args);
	}
}
