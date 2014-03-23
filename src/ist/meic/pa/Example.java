package ist.meic.pa;

public class Example {

	public static void main(String[] args) {
		A e = new A();
		new Inspector().inspect(e);
		System.err.println("TERMINATED");
	}
}
