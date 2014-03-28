package ist.meic.pa;

public class B {

	private String c = "beca beca";
	protected int d;
	public char coiso;
	public String teste = "isto";
	
	protected double g() {
		return 42.0;
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
	
	private int m() {
		return 69;
	}
	
	public Double g(double d) {
		return 2.0;
	}
	
	public int g(int h) {
		return d+h;
	}
	
	public String s() {
		return new String("bla bla");
	}
	
	public void printString(String s) {
		System.err.println(s);
	}
	
	public static long i = 10L;
}
