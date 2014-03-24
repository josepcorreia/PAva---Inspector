package ist.meic.pa;

import java.util.ArrayList;

public class CommandQuit implements Command {

	@Override
	public Object execute(Object obj, String[] line) {

		return null;
	}

	@Override
	public void execute(Object obj) {
		// Do nothing
	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line){
	
        return null;
    }
	
}
