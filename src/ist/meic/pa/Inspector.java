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

	@SuppressWarnings("unchecked")
	public void inspect(Object obj) {
		if(obj != null) {

			actual = obj;

			try{
				iMethods.get("i").execute(actual);
				inspectedObjects.add(actual);
				actualIndex = getObjectIndex(actual);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InspectCycle();
		}
	}

	/**
	 * 
	 */
	private void InspectCycle() {
		while(true) {
			Util.printPrompt();
			String[] line = readInput();

			Command c = iMethods.get(line[0]);

			Object ret = null;

			try {
				if(c == null) {
					Class<Command> cmd = null;
					try {
						cmd = (Class<Command>) Class.forName("ist.meic.pa.Command"+ line[0].toUpperCase());
					} catch (ClassNotFoundException e) {
						System.err.println("The Command isn't valid. Please insert a valid command.");
						continue;
					}
					c = createNewInstance(line, c, cmd);
				}
				ret = executeCommand(line, c);
			} catch (QuitException e) {
				return;
			} catch (InspectException e) {
				try {
					ret = processBackOrForward(e);

				} catch (Exception e1) {
				} 
			} catch (InstantiationException e) {
			} catch (Exception e) {
			}

			if(ret != null)
				actual = ret;
		}
	}

	/**
	 * @param line
	 * @param c
	 * @param cmd
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Command createNewInstance(String[] line, Command c,
			Class<Command> cmd) throws InstantiationException,
			IllegalAccessException {
		if(cmd != null) {
			c=cmd.newInstance();
			iMethods.put(line[0], c);
		}
		return c;
	}

	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	private Object processBackOrForward(InspectException e) throws Exception {
		Object ret;
		ret = e.getRetObject();
		actualIndex = getObjectIndex(ret);
		// print new actual object so user knows where he is in the graph
		iMethods.get("i").execute(ret);
		return ret;
	}

	/**
	 * @param line
	 * @param c
	 * @return
	 * @throws Exception
	 */
	private Object executeCommand(String[] line, Command c) throws Exception {
		Object ret;
		ret = c.execute(actual, inspectedObjects, savedObjects, line);
		if(ret != null){
			processInspected(ret);
		}
		return ret;
	}

	/**
	 * @return
	 */
	private String[] readInput() {
		Scanner sc = new Scanner(System.in);
		String line[]= sc.nextLine().split(" ");
		return line;
	}

	public void processInspected(Object obj){
		inspectedObjects.subList(actualIndex+1, inspectedObjects.size()).clear();
		inspectedObjects.add(obj);
		actualIndex = getObjectIndex(obj);
	}

	/**
	 * @param obj
	 * @return
	 */
	private int getObjectIndex(Object obj) {
		return inspectedObjects.indexOf(obj);
	}
}
