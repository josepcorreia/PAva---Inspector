package ist.meic.pa;

import java.util.ArrayList;

public class CommandBack implements Command{

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) {
		
		int index = inspectedObjects.indexOf(obj);
		if(index == 0)
		    return obj;
		else
		    return inspectedObjects.get(index-1);
	}

}
