package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CommandModify implements Command{

	@Override
	public void execute(Object obj) {
		// DO NOTHING
		
	}
	
	public Object execute(Object obj, ArrayList inspectedObjects, String[] line){
		Class objectClass = obj.getClass();
		Object objField = null;
		
		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();
			
			for(Field field : fields){
				if(field.getName().equals(line[1])) {
					field.setAccessible(true);
					try {
						objField = field.get(obj);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						
					Object ret = null;
//					if (objField != null)
//						ret = Util.convert(line[2], objField.getClass());
//					else
						ret = Util.convert(line[2], field.getType());
					
					field.set(obj, ret);
					
					new CommandInspect().execute(obj);
					}  catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (RuntimeException e) {
						System.err.println("EXCEPTION "+e.getMessage());
					}
					
					return null;
				}	
			}
			objectClass = objectClass.getSuperclass();
		}	
		return null;
    }
}
