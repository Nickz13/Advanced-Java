package application;

import java.util.HashMap;
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
		Map<String, Team> temp = new HashMap<String, Team>();
		for (Map.Entry mapEle : mem.getTeams().entrySet()) {
			temp.put((String)mapEle.getKey(), (Team)mapEle.getValue());
		}
        this.teams = temp;
		
	}
	
	public Map<String, Team> getTeams() {
		return teams;
	}
	
	public void setTeams(Map<String, Team> teams) {
		this.teams = teams;
	}
}
