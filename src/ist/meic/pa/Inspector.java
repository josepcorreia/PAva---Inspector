package ist.meic.pa;


import ist.meic.pa.exceptions.*;

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
		// Default command
		iMethods.put("i", new CommandI());
	}

	@SuppressWarnings("unchecked")
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
				String input = sc.nextLine();
				String line[] = input.split(" ");

				if(input.contains("\"")) {
					String processString = input;
					while(true) {
						int first = processString.indexOf('\"');
						String string = input.substring(first+1, input.length());
						String newSubstring = string.substring(0, string.indexOf('\"'));

						break;

					}
				}

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
							System.err.print("> ");
							continue;
						}
					}
					ret = c.execute(actual, inspectedObjects, savedObjects, line);
					if(ret != null){
						inspectedObjects.add(ret);
					}
				} catch (QuitException e) {
					return;
				} catch (InspectException e) {
					try {
						ret = e.getRetObject();
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

				System.err.print("> ");

			}
		}
	}
}
