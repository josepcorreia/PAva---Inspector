package ist.meic.pa;

public class B {

	private String c = "beca beca";
	protected int d;
	public char coiso;
	protected String teste = "isto";
	
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
	private boolean f;
	protected String other = "coiso";
	private String c = "sdfsdf";
	
	private int m() {
		return 69;
	}
	
	public Double g(double d) {
		return d;
	}
	
	public Double g(double f, double s) {
		return f+s;
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
