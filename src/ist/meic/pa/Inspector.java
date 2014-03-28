package ist.meic.pa;

import ist.meic.pa.exceptions.*;
import ist.meic.pa.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Inspector {

	private Map<String,Command> iMethods = new HashMap<String, Command>();
	private Object actual;
	private int actualIndex = 0;
	
	private ArrayList<Object> inspectedObjects = new ArrayList<Object>();
	private HashMap<String, Object> savedObjects = new HashMap<String, Object>();
	

	public Inspector() {
		// Default command
		iMethods.put("i", new CommandI());
	}
	
	public void processInspected(Object obj){
			inspectedObjects.subList(actualIndex+1, inspectedObjects.size()).clear();
			inspectedObjects.add(obj);
			actualIndex = inspectedObjects.indexOf(obj);
	}
	
	@SuppressWarnings("unchecked")
	public void inspect(Object obj) {
		if(obj != null) {

			actual = obj;

			try{
				iMethods.get("i").execute(actual);
				inspectedObjects.add(actual);
				actualIndex = inspectedObjects.indexOf(actual);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while(true) {
				Util.printString("> ");
				Scanner sc = new Scanner(System.in);
				String line[]= sc.nextLine().split(" ");

				Command c = iMethods.get(line[0]);

				Object ret = null;

				try {
					if(c == null) {
						Class<Command> cmd = null;
						try {
							cmd = (Class<Command>) Class.forName("ist.meic.pa.Command"+ line[0].toUpperCase());
						} catch (ClassNotFoundException e) {
							System.err.println("The Command isn't valid. Please insert a valid command.");
						}
						if(cmd != null) {
							c=cmd.newInstance();
							iMethods.put(line[0], c);
						}
						else {
							continue;
						}
					}
					ret = c.execute(actual, inspectedObjects, savedObjects, line);
					if(ret != null){
						processInspected(ret);
					}
				} catch (QuitException e) {
					return;
				} catch (InspectException e) {
					try {
						ret = e.getRetObject();
						actualIndex = inspectedObjects.indexOf(ret);
						// print new actual object so user knows where he is in the graph
						iMethods.get("i").execute(ret);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				} catch (InstantiationException e) {
					// Trying to instantiate interface Command
					// DO NOTHING
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(ret != null)
					actual = ret;
			}
		}
	}
}
