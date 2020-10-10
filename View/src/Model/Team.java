package Model;

import java.io.Serializable;

public class Team implements Serializable {
	private String projectID;
	private String studentIDs;
	
	public Team(String projectID, String studentIDs) {
		this.projectID = projectID;
		this.studentIDs = studentIDs;
	}
	
	public String getStudentIDs() {
		return studentIDs;
	}
	
	public String getProjectID() {
		return projectID;
	}
}
