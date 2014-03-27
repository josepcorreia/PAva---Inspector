package ist.meic.pa;

import ist.meic.pa.exceptions.QuitException;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandQuit implements Command {

	@Override
	public Object execute(Object obj, String[] line) throws QuitException {
		throw new QuitException();
	}

	@Override
	public void execute(Object obj) {
		// Do nothing
	}
	
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, String[] line) throws QuitException{
	
		throw new QuitException();
    }

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
