package ist.meic.pa;


import java.util.ArrayList;
import java.util.HashMap;

public interface Command {
	public Object execute(Object obj, String[] line) throws Exception;
	public void execute(Object obj) throws Exception;
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) throws Exception;
	public Object execute(Object obj, ArrayList inspectedObjects, HashMap<String, Object> savedObjects, String[] line) throws Exception;
}
