package ist.meic.pa;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class CommandI implements Command {
	
	public void execute(Object obj) {
		
		System.err.println(obj + " is an instance of " + obj.getClass());
		System.err.println("----------");
		
		Class objectClass = obj.getClass();
		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();
			
			for(Field field : fields){
				// verificar se h�� campos a null
				
					field.setAccessible(true);
					try {
						System.err.println(Modifier.toString(field.getModifiers()) + " " + field.getType() + " " + field.getName() + " = " + field.get(obj));
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
	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line){

		Class objectClass = obj.getClass();
		Object objField = null;
		
		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();
			
			for(Field field : fields){
				if(field.getName().equals(line[1])) {
					try {
						field.setAccessible(true);
						objField = field.get(obj);
						execute(objField);
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
