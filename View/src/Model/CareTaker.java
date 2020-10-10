package Model;

import java.util.HashMap;
import java.util.Map;

public class CareTaker {
	private final Map<String, Memento>savepointStorage = new HashMap<String, Memento>();
	 
    public void saveMemento(Memento memento,String savepointName){
        System.out.println("Saving state..."+savepointName);
        savepointStorage.put(savepointName, memento);
        System.out.println("Initial hash"+savepointStorage.get("INITIAL").getTeams().get("Pr3").getStudentIDs());
    }
 
    public Memento getMemento(String savepointName){
        System.out.println("Undo at ..."+savepointName);
        return savepointStorage.get(savepointName);
    }
 
    public void clearSavepoints(){
        System.out.println("Clearing all save points...");
        savepointStorage.clear();
    }
}
