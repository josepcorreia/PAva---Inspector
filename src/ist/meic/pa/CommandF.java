package ist.meic.pa;

import ist.meic.pa.exceptions.InspectException;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandF implements Command {
	
	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object)
	 */
	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
	

	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object, java.util.ArrayList, java.util.HashMap, java.lang.String[])
	 */
	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {
		int index = inspectedObjects.indexOf(obj);
		if(index == inspectedObjects.size()-1)
			throw new InspectException(obj);
		else
			throw new InspectException(inspectedObjects.get(index+1));
	}
}
