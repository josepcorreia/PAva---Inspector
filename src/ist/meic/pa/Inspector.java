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


	public Inspector() {
		// Create objects only when needed, using reflection
		/*iMethods.put("q", new CommandQ());
		iMethods.put("m", new CommandM());
		iMethods.put("c", new CommandC());
		
		iMethods.put("b", new CommandB());
		iMethods.put("f", new CommandF());
	*/
	}

	public void inspect(Object obj) {
		if(obj != null) {

			actual = obj;

			try{
				//mudar, fazer com reflextion tambem
				iMethods.put("i", new CommandI());
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
					if(c == null) {
						Class<Command> cmd = null;
						try {
							cmd = (Class<Command>) Class.forName("ist.meic.pa.Command"+ line[0].toUpperCase());
						} catch (ClassNotFoundException e) {
							System.err.print("The Command isn't valid. Please insert a valid command");
						}
						c=cmd.newInstance();
						iMethods.put(line[0], c);
					}
					ret = c.execute(actual, inspectedObjects, line);
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
