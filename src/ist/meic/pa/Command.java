package ist.meic.pa;


import java.util.ArrayList;

public interface Command {
	public void execute(Object obj) throws Exception;
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line) throws Exception;
}
