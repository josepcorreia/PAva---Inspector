package ist.meic.pa;


import java.util.ArrayList;
import java.util.HashMap;

public interface Command {
	/**
	 * @param obj
	 * @throws Exception
	 */
	public void execute(Object obj) throws Exception;
	/**
	 * @param obj
	 * @param inspectedObjects
	 * @param savedObjects
	 * @param line
	 * @return
	 * @throws Exception
	 */
	public Object execute(Object obj, ArrayList<Object> inspectedObjects, HashMap<String, Object> savedObjects, String[] line) throws Exception;
}
