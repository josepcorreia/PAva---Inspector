package ist.meic.pa;

import ist.meic.pa.exceptions.InspectException;

import java.util.ArrayList;

public class CommandF implements Command {

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) throws InspectException {
	    
	    int index = inspectedObjects.indexOf(obj);
		if(index == inspectedObjects.size()-1)
			throw new InspectException(obj);
		else
			throw new InspectException(inspectedObjects.get(index+1));
	}


}
