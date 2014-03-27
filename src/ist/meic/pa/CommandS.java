package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandS implements Command {

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
	
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, HashMap<String, Object> savedObjects, String[] line){
	
		if(savedObjects.get(line[1]) == null) {
			savedObjects.put(line[1], obj);
			System.err.println("Saved object " + obj + " with name " + line[1]);
		} else {
			System.err.println("Can not save object " + obj + " with name " + line[1] + ". Object " + savedObjects.get(line[1]) + " already exists with name " + line[1] + ".");
		}
        return null;
    }

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, String[] line)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
