package ist.meic.pa;

public class CommandQuit implements Command {

	@Override
	public void execute(Object... objects) {
		// TODO Auto-generated method stub
		System.exit(0);
	}
	
}
