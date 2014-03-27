package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandSave implements Command {

	@Override
	public Object execute(Object obj, String[] line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
	
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, HashMap<String, Object> savedObjects, String[] line){
	
		if(savedObjects.get(line[1]) != null) {
			savedObjects.put(line[1], obj);
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
