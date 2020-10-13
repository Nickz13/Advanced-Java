package application;

import java.util.Map;

import Model.Team;

public class Memento {
	 
	private Map<String, Team> teams;
 
    public Memento(Map<String, Team> teams){
        this.teams = teams;
    }
    
    public Map<String, Team> getTeams() {
		return teams;
	}
}
