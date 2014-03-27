package ist.meic.pa;

import ist.meic.pa.exceptions.InspectException;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandB implements Command{

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
			throws Exception {
		int index = inspectedObjects.indexOf(obj);
		if(index == 0){
			throw new InspectException(obj);
		}
		else{
			throw new InspectException(inspectedObjects.get(index-1));
		}

	}

}
