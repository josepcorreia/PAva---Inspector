package ist.meic.pa;

public class B {

	private String c;
	protected int d;
	public char coiso;
	
	public int g() {
		return 42;
	}
	
	public int g(int h) {
		return d-h;
	}
	
	protected void z() {
		System.err.println("called");
	}
}

class E extends B {
	boolean f;
	
	public Double g(double d) {
		return 2.0;
	}
	
	public int g(int h) {
		return d+h;
	}
	
	public static long i = 10L;
}
