package ist.meic.pa;

public class CommandQuit implements Command {

	@Override
	public void execute(Object obj, String[] line) {
		System.exit(0);
	}

	@Override
	public void execute(Object obj) {
		// Do nothing
	}
	
}
