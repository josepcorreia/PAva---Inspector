package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandC implements Command {
	
	/**
	 * Constructor
	 */
	public CommandC() {
		
	}

	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object)
	 */
	@Override
	public void execute(Object obj) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object, java.util.ArrayList, java.util.HashMap, java.lang.String[])
	 */
	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {

		// Numero de argumentos
		int argsLength = line.length - 2;

		Object ret = null;

		List<Method> toInvoke = new ArrayList<Method>();

		extractMethods(obj, line, toInvoke);

		Class<?> objectClass = obj.getClass().getSuperclass();

		objectClass = extractSuperclassMethods(line, toInvoke, objectClass);

		List<Method> args = extractMethodsMatchingArguments(argsLength,
				toInvoke);

		toInvoke = args;
		args = null;

		ret = processCalling(obj, savedObjects, line, ret, toInvoke);

		return ret;
	}

	/**
	 * Method used to process the calling of a method with or without arguments
	 * 
	 * @param obj
	 * @param savedObjects
	 * @param line
	 * @param ret
	 * @param toInvoke
	 * @return
	 */
	private Object processCalling(Object obj,
			HashMap<String, Object> savedObjects, String[] line, Object ret,
			List<Method> toInvoke) {

		for(Method m : toInvoke) {
			
			// May be null
			Class<?>[] methodParams = m.getParameterTypes();

			// May be empty
			ArrayList<Object> methodArgs = new ArrayList<Object>(methodParams.length);

			int lineIndex = 2;
			processMethodsArguments(savedObjects, line, methodParams,
					methodArgs, lineIndex);

			try {
				ret = m.invoke(obj, methodArgs.toArray());
				ret = processReturnType(ret, m);
				break;

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Util.printString("This method is private!");;
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ret;
	}

	/**
	 * Method used to check if the method's return type is void
	 * 
	 * @param ret
	 * @param m
	 * @return
	 */
	private Object processReturnType(Object ret, Method m) {
		if(!m.getReturnType().equals(void.class)) {
			ret = processPrimitiveOrObject(ret, m);
		}
		return ret;
	}

	/**
	 * Method used to process the method's return. It may an object or a
	 * primitive type
	 * 
	 * @param ret
	 * @param m
	 * @return
	 */
	private Object processPrimitiveOrObject(Object ret, Method m) {
		if(m.getReturnType().isPrimitive()) {
			Util.printString(ret.toString());
			ret = null;
		} else {
			new CommandI().execute(ret);
		}
		return ret;
	}

	/**
	 * Method used to process each argument's method
	 * 
	 * @param savedObjects
	 * @param line
	 * @param methodParams
	 * @param methodArgs
	 * @param lineIndex
	 */
	private void processMethodsArguments(HashMap<String, Object> savedObjects,
			String[] line, Class<?>[] methodParams,
			ArrayList<Object> methodArgs, int lineIndex) {
		for(Class<?> arg : methodParams) {
			try {
				processArguments(savedObjects, line, methodArgs,
						lineIndex, arg);
			} catch (RuntimeException e) {
				// If an argument can't be converted, it's not the right method
				// to be called
				break;
			}
			lineIndex++;
		}
	}

	/**
	 * Method used to check if the passed argument is stored or has the same
	 * type of the method's received argument
	 * 
	 * @param savedObjects
	 * @param line
	 * @param methodArgs
	 * @param lineIndex
	 * @param arg
	 */
	private void processArguments(HashMap<String, Object> savedObjects,
			String[] line, ArrayList<Object> methodArgs, int lineIndex,
			Class<?> arg) {
		if(savedObjects.containsKey(line[lineIndex])) {
			getSavedObjects(savedObjects, line, methodArgs,
					lineIndex, arg);
		} else {

			methodArgs.add(Util.convert(line[lineIndex], arg));
		}
	}

	/**
	 * @param savedObjects
	 * @param line
	 * @param methodArgs
	 * @param lineIndex
	 * @param arg
	 */
	private void getSavedObjects(HashMap<String, Object> savedObjects,
			String[] line, ArrayList<Object> methodArgs, int lineIndex,
			Class<?> arg) {
		Object saved = savedObjects.get(line[lineIndex]);
		if (saved.getClass().getName().equals(arg.getName())) {
			methodArgs.add(saved);
		}
	}

	/**
	 * @param argsLength
	 * @param toInvoke
	 * @return
	 */
	private List<Method> extractMethodsMatchingArguments(int argsLength,
			List<Method> toInvoke) {
		List<Method> args = new ArrayList<Method>();
		for(Method m : toInvoke) {
			if(m.getParameterTypes().length == argsLength){
				args.add(m);
			}
		}
		return args;
	}

	/**
	 * @param line
	 * @param toInvoke
	 * @param objectClass
	 * @return
	 */
	private Class<?> extractSuperclassMethods(String[] line,
			List<Method> toInvoke, Class<?> objectClass) {
		while(objectClass.getSuperclass() != null) {
			for(Method m : objectClass.getDeclaredMethods()) {
				if(m.getName().equals(line[1])) {
					toInvoke.add(m);
				}
			}
			objectClass = objectClass.getSuperclass();
		}
		return objectClass;
	}

	/**
	 * @param obj
	 * @param line
	 * @param toInvoke
	 */
	private void extractMethods(Object obj, String[] line, List<Method> toInvoke) {
		for(Method m : obj.getClass().getDeclaredMethods()) {
			if(m.getName().equals(line[1])) {
				m.setAccessible(true);
				toInvoke.add(m);
			}
		}
	}
}
