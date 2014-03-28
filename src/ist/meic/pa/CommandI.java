package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class CommandI implements Command {

	public void execute(Object obj) {

		Util.printString(obj + " is an instance of " + obj.getClass());
		Util.printString("----------");

		HashSet<String> uniqueFields = new HashSet<String>();

		Class<?> objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {

			Field[] fields = objectClass.getDeclaredFields();
			if(Arrays.asList(fields).size() != 0)
				Util.printString("Fields inherited from " + objectClass.getName() + ":");

			for(Field field : fields){
				// verificar se ha campos a null

				field.setAccessible(true);
				try {
					// show all fields of the inspected object obj, but only the public and protected ones of its superclasses
					if(uniqueFields.contains(field.getName()))
						Util.printString("(Shadowed field from " + objectClass + ") " + Modifier.toString(field.getModifiers()) + " " + field.getType() + " " + field.getName() + " = " + field.get(obj));
					else {
						uniqueFields.add(field.getName());
						Util.printString(Modifier.toString(field.getModifiers()) + " " + field.getType() + " " + field.getName() + " = " + field.get(obj));
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			objectClass = objectClass.getSuperclass();
		}

		// first print all fields, now print all methods
		Util.printString("----------");
		objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {

			Method[] methods = objectClass.getDeclaredMethods();
			if(Arrays.asList(methods).size() != 0)
				Util.printString("Methods inherited from " + objectClass.getName() + ":");

			for(Method method : methods){
				// verificar se ha campos a null

				method.setAccessible(true);
				try {
					if( (objectClass.getName().equals(obj.getClass().getName())) || (!objectClass.getName().equals(obj.getClass().getName()) & (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())))) {
						if(Arrays.asList(method.getExceptionTypes()).size() == 0)
							Util.printString(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "(" + Arrays.toString(method.getParameterTypes()).replace(",", "").replace("[", "").replace("]", "") + ")");
						else
							Util.printString(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "(" + Arrays.toString(method.getParameterTypes()).replace(",", "").replace("[", "").replace("]", "") + ")" + " throws " + Arrays.toString(method.getExceptionTypes()).replace(",", "").replace("[", "").replace("]", ""));
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			objectClass = objectClass.getSuperclass();
		}
	}

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {
		Class<?> objectClass = obj.getClass();
		Object objField = null;

		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();

			for(Field field : fields){
				if(field.getName().equals(line[1])) {
					try {
						// inspect all fields only of the inspected object obj, and only the public and protected ones of its superclasses
						if( (objectClass.getName().equals(obj.getClass().getName())) || (!objectClass.getName().equals(obj.getClass().getName()) & (Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())))) {    
							field.setAccessible(true);
							if(field.getType().isPrimitive()) {
								Util.printString(field.get(obj).toString());
							}
							else {
								objField = field.get(obj);
								execute(objField);
							}
						} else
							Util.printString("Seriously, are you trying to access a private field?");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return objField;
				}
			}
			objectClass = objectClass.getSuperclass();
		}
		return objField;
	}
}
