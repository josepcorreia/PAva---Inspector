package ist.meic.pa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

public class CommandI implements Command {

	public void execute(Object obj) {

		System.err.println(obj + " is an instance of " + obj.getClass());
		System.err.println("----------");

		HashSet<String> uniqueFields = new HashSet<String>();

		Class<?> objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {

			Field[] fields = objectClass.getDeclaredFields();
			if(Arrays.asList(fields).size() != 0)
				System.err.println("Fields inherited from " + objectClass.getName() + ":");

			for(Field field : fields){
				// verificar se ha campos a null

				field.setAccessible(true);
				try {
					// show all fields of the inspected object obj, but only the public and protected ones of its superclasses
					if( (objectClass.getName().equals(obj.getClass().getName())) || (!objectClass.getName().equals(obj.getClass().getName()) & (Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())))) {
						if(uniqueFields.contains(field.getName()))
							System.err.println("(Shadowed field from " + objectClass + ") " + Modifier.toString(field.getModifiers()) + " " + field.getType() + " " + field.getName() + " = " + field.get(obj));
						else {
							uniqueFields.add(field.getName());
							System.err.println(Modifier.toString(field.getModifiers()) + " " + field.getType() + " " + field.getName() + " = " + field.get(obj));
						}
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
		System.err.println("----------");
		objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {

			Method[] methods = objectClass.getDeclaredMethods();
			if(Arrays.asList(methods).size() != 0)
				System.err.println("Methods inherited from " + objectClass.getName() + ":");

			for(Method method : methods){
				// verificar se ha campos a null

				method.setAccessible(true);
				try {
					if( (objectClass.getName().equals(obj.getClass().getName())) || (!objectClass.getName().equals(obj.getClass().getName()) & (Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())))) {
						if(Arrays.asList(method.getExceptionTypes()).size() == 0)
							System.err.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "(" + Arrays.toString(method.getParameterTypes()).replace(",", "").replace("[", "").replace("]", "") + ")");
						else
							System.err.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getName() + " " + method.getName() + "(" + Arrays.toString(method.getParameterTypes()).replace(",", "").replace("[", "").replace("]", "") + ")" + " throws " + Arrays.toString(method.getExceptionTypes()).replace(",", "").replace("[", "").replace("]", ""));
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
								System.err.println(field.get(obj));
							}
							else {
								objField = field.get(obj);
								execute(objField);
							}
						} else
							System.err.println("Seriously, are you trying to access a private field?");
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
