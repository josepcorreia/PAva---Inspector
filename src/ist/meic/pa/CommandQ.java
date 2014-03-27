package ist.meic.pa;

import ist.meic.pa.exceptions.QuitException;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandQ implements Command {

	@Override
	public void execute(Object obj) {
		// Do nothing
	}

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, HashMap<String, Object> savedObjects, String[] line)
			throws Exception {
		throw new QuitException();

	}
	
}
