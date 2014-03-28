package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandC implements Command {

	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {

		// Numero de argumentos
		int argsLength = line.length - 2;

		Object ret = null;

		List<Method> toInvoke = new ArrayList<Method>();

		for(Method m : obj.getClass().getDeclaredMethods()) {
			if(m.getName().equals(line[1])) {
				m.setAccessible(true);
				toInvoke.add(m);
			}
		}

		Class<?> objectClass = obj.getClass().getSuperclass();


		while(objectClass.getSuperclass() != null) {
			for(Method m : objectClass.getDeclaredMethods()) {
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
				for(Class<?> arg : methodParams) {
					try {
						if(savedObjects.containsKey(line[lineIndex])) {
							Object saved = savedObjects.get(line[lineIndex]);
							if (saved.getClass().getName().equals(arg.getName())) {
								methodArgs.add(saved);
							}
						} else {

							methodArgs.add(Util.convert(line[lineIndex], arg));
						}
					} catch (RuntimeException e) {
						// Se não consegue converter um argumento, não é este o metodo a ser chamado
						break;
					}
					lineIndex++;
				}
				if(methodArgs.size() == m.getParameterTypes().length) {
					try {
						ret = m.invoke(obj, methodArgs.toArray());
						if(!m.getReturnType().equals(void.class)) {
							if(m.getReturnType().isPrimitive()) {
								Util.printString(ret.toString());
								ret = null;
							} else {
								new CommandI().execute(ret);
							}
						}
						break;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						//Util.printString("Seriously, are you trying to access a private method?");;
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// without arguments
			} else {
				try {
					ret = m.invoke(obj, null);
					if(!m.getReturnType().equals(void.class)) {
						if(m.getReturnType().isPrimitive()) {
							Util.printString(ret.toString());
							ret = null;
						} else {
							new CommandI().execute(ret);
						}
					}
					break;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					//Util.printString("Seriously, are you trying to access a private method?");;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return ret;
	}
}
