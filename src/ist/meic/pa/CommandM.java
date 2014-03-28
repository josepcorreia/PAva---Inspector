package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.ArrayList;

public class CommandM implements Command{

	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object)
	 */
	@Override
	public void execute(Object obj) {
		// DO NOTHING

	}

	/* (non-Javadoc)
	 * @see ist.meic.pa.Command#execute(java.lang.Object, java.util.ArrayList, java.util.HashMap, java.lang.String[])
	 */
	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {
		Class<?> objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();

			if(processFields(obj, objectClass, line, fields)) {
				break;
			}
			objectClass = objectClass.getSuperclass();
		}	
		return null;
	}

	/**
	 * @param obj
	 * @param line
	 * @param fields
	 */
	private boolean processFields(Object obj, Class<?> objectClass, String[] line, Field[] fields) {
		for(Field field : fields){
			if( (objectClass.getName().equals(obj.getClass().getName())) || (!objectClass.getName().equals(obj.getClass().getName()) & (Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())))) {
			if(field.getName().equals(line[1])) {
				field.setAccessible(true);
				try {
					Object ret = null;
					ret = Util.convert(line[2], field.getType());

					field.set(obj, ret);

					new CommandI().execute(obj);
					return true;
				}  catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					Util.printString("EXCEPTION "+e.getMessage());
				}
			}
			}
			else {
				Util.printString("Attempt to modify superclass private field.");
			}
		}
		return false;
	}
}
