package ist.meic.pa;

import ist.meic.pa.util.Util;

public class Example {

	public static void main(String[] args) {
		A e = new A();
		new Inspector().inspect(e);
		Util.printString("TERMINATED");
	}
}
