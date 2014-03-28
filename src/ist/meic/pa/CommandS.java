package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author JoseCorreia
 *
 */
public class CommandS implements Command {

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
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, HashMap<String, Object> savedObjects, String[] line){

		if(savedObjects.get(line[1]) == null) {
			savedObjects.put(line[1], obj);
			Util.printString("Saved object " + obj + " with name " + line[1]);
		} else {
			Util.printString("Can not save object " + obj + " with name " + line[1] + ". Object " + savedObjects.get(line[1]) + " already exists with name " + line[1] + ".");
		}
		return null;
	}
}
