package ist.meic.pa;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;

public class CommandInspect implements Command {
	
	@Override
	public void execute(Object obj, String[] line) {
		
	}
	
	public void execute(Object obj) {
		
		System.err.println(obj + " is an instance of " + obj.getClass());
		System.err.println("----------");
		
		Class objectClass = obj.getClass();
		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();
			
			for(Field field : fields){
				// verificar se há campos a null
				
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
}
