package ist.meic.pa;

import ist.meic.pa.exceptions.QuitException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Inspector {

	private Map<String,Command> iMethods = new HashMap<String, Command>();
	private Object actual;

	private ArrayList<Object> inspectedObjects = new ArrayList<Object>();
	private HashMap<String, Object> savedObjects = new HashMap<String, Object>();


	public Inspector() {
		// Create objects only when needed, using reflection
		// consider using factory. Switch case problem
		iMethods.put("q", new CommandQuit());
		iMethods.put("m", new CommandModify());
		iMethods.put("c", new CommandCall());
		iMethods.put("i", new CommandInspect());
		iMethods.put("b", new CommandBack());
		iMethods.put("f", new CommandForward());
		iMethods.put("s", new CommandSave());
	}

	public void inspect(Object obj) {
		if(obj != null) {

			actual = obj;

			try{
				iMethods.get("i").execute(actual);
				inspectedObjects.add(actual);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.print("> ");

			while(true) {
				Scanner sc = new Scanner(System.in);
				String line[]= sc.nextLine().split(" ");

				Command c = iMethods.get(line[0]);

				Object ret = null;

				try {
					if(c != null) {
						if(line[0].equals("s")) {
							ret = c.execute(actual, inspectedObjects, savedObjects, line);
						}
						//Mudar?
						else if(line[0].equals("b") || line[0].equals("f")) {
							ret = c.execute(actual, inspectedObjects, line);
							iMethods.get("i").execute(ret); // print new actual object so user knows where he is in the graph
						} else {    
							ret = c.execute(actual, line);
							if(ret!=null)
								inspectedObjects.add(ret);
						}
					}
				} catch (QuitException e) {
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(ret != null)
					actual = ret;

				System.err.print("> ");

			}
		}
	}
}
