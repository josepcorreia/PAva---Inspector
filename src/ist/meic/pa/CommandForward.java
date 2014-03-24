package ist.meic.pa;

import java.util.ArrayList;

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
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) {
	    
	    int index = inspectedObjects.indexOf(obj);
		if(index == inspectedObjects.size()-1)
		    return obj;
		else
		    return inspectedObjects.get(index+1);
	}


}
