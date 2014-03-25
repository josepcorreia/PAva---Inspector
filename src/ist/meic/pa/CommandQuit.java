package ist.meic.pa;

import ist.meic.pa.exceptions.QuitException;

import java.util.ArrayList;

public class CommandQuit implements Command {

	@Override
	public void execute(Object obj) {
		// Do nothing
	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) throws QuitException{
	
		throw new QuitException();
    }
	
}
