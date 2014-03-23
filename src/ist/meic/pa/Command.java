package ist.meic.pa;

public interface Command {
	public Object execute(Object obj, String[] line);
	public void execute(Object obj);
}
