package Model;

import java.util.Map;

public class Originator {
	private Map<String, Team> teams;
	private String lastUndoSavepoint;
    CareTaker careTaker;
    
    public Originator(Map<String, Team> teams, CareTaker careTaker){
        this.teams = teams;
        this.careTaker = careTaker;
        System.out.println("inside originator"+teams.get("Pr3").getStudentIDs());
        createSavepoint("INITIAL");
    }
    
    public Map<String, Team> getTeams() {
		return teams;
	}
    
    public void setTeams(Map<String, Team> teams1) {
		teams = teams1;
		System.out.println("set teams"+teams.get("Pr3").getStudentIDs());
	}
    
    public void createSavepoint(String savepointName){
        careTaker.saveMemento(new Memento(this.teams), savepointName);
        lastUndoSavepoint = savepointName;
    }
 
    public void undo(){
    	System.out.println("Last save point"+lastUndoSavepoint);
        setOriginatorState(lastUndoSavepoint);
    }
 
    public void undo(String savepointName){
    	System.out.println("undo originator");
        setOriginatorState(savepointName);
    }
 
    public void undoAll(){
        setOriginatorState("INITIAL");
        careTaker.clearSavepoints();
    }
 
    private void setOriginatorState(String savepointName){
        Memento mem = careTaker.getMemento(savepointName);
        
        this.teams = mem.getTeams();
        System.out.println("memento"+this.teams.get("Pr3").getStudentIDs());
    }
 
    @Override
    public String toString(){
        return "Teams"+teams;
    }
}
