package ist.meic.pa;

public interface Command {
	public void execute(Object obj, String[] line);
	public void execute(Object obj);
}
