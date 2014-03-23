package ist.meic.pa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inspector {
	
	private Map<String,Command> iMethods = new HashMap<String, Command>();
	private Object actual;
	
	
	public Inspector() {
		// Create objects only when needed, using reflection
		// consider using factory. Switch case problem
		iMethods.put("q", new CommandQuit());
		iMethods.put("m", new CommandModify());
		iMethods.put("c", new CommandCall());
		iMethods.put("i", new CommandInspect());
	}
	
	public void inspect(Object obj) {
		
		actual = obj;
		iMethods.get("i").execute(actual);
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			String line[]= sc.nextLine().split(" ");
			
			Command c = iMethods.get(line[0]);
			
			if(c == null) {
				
			}
			
			c.execute(actual, line);
			
		}
	}
}
