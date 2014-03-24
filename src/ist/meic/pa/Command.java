package ist.meic.pa;

import java.util.ArrayList;

public interface Command {
	public Object execute(Object obj, String[] line);
	public void execute(Object obj);
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line);
}
