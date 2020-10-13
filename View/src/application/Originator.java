package application;

import java.util.Map;
import Model.*;

public class Originator {
	
	private Map<String, Team> teams;
	private CareTaker careTaker;
	
	public Originator(CareTaker careTaker) {
		this.careTaker = careTaker;
	}

	public void createSavePoint(String savepointName) {
		// TODO Auto-generated method stub
		careTaker.saveMemento(new Memento(this.teams), savepointName);
	}
	
	public void undo(String savePointName){
        setOriginatorState(savePointName);
    }
	
	public void removePrevious(String savePointName) {
		careTaker.removeSavePoint(savePointName);
	}
	
	private void setOriginatorState(String savepointName) {
		Memento mem = careTaker.getMemento(savepointName);
        this.teams = mem.getTeams();
		
	}
	
	public Map<String, Team> getTeams() {
		return teams;
	}
	
	public void setTeams(Map<String, Team> teams) {
		this.teams = teams;
	}
}
