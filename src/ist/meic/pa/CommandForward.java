package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandForward implements Command {

	@Override
	public Object execute(Object obj, String[] line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
	
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, String[] line) {
	    
	    int index = inspectedObjects.indexOf(obj);
		if(index == inspectedObjects.size()-1)
		    return obj;
		else
		    return inspectedObjects.get(index+1);
	}

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
