package ist.meic.pa;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inspector {
	
	private Map<String,Object> iMethods = new HashMap<String, Object>();
	private Object actual;
	
	
	public Inspector() {
		// Create objects only when needed, using reflection
		iMethods.put("q", new CommandQuit());
		iMethods.put("m", new CommandModify());
		iMethods.put("c", new CommandCall());
		iMethods.put("i", new CommandInspect());
	}
	
	public void inspect(Object obj) {
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			String line[]= sc.nextLine().split(" ");
			
		}
	}
}
