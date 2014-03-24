package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandCall implements Command {

	@Override
	public Object execute(Object obj, String[] line) {

		// Numero de argumentos
		int argsLength = line.length - 2;

		List<Method> toInvoke = new ArrayList<Method>();

		Class objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {
			for(Method m : objectClass.getMethods()) {
				if(m.getName().equals(line[1])) {
					toInvoke.add(m);
				}
			}
			objectClass = objectClass.getSuperclass();
		}

		List<Method> args = new ArrayList<Method>();
		for(Method m : toInvoke) {
			if(m.getParameterTypes().length == argsLength){
				args.add(m);
			}
		}
		toInvoke = args;
		args = null;

		for(Method m : toInvoke) {
			// with arguments
			if (m.getParameterTypes().length != 0) {
				Class<?>[] methodParams = m.getParameterTypes();
				ArrayList<Object> methodArgs = new ArrayList<Object>(methodParams.length);

				int lineIndex = 2;
				for(Class arg : methodParams) {
					try {
						methodArgs.add(Util.convert(line[lineIndex], arg));
					} catch (RuntimeException e) {
						// Se não consegue converter um argumento, não é este o metodo a ser chamado
						break;
					}
					lineIndex++;
				}
				if(methodArgs.size() == m.getParameterTypes().length) {
					Object ret;
					try {
						ret = m.invoke(obj, methodArgs.toArray());
						new CommandInspect().execute(ret);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// without arguments
			} else {
				try {
					Object ret = m.invoke(obj, null);
					new CommandInspect().execute(ret);
					break;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		return null;
	}

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}
}
