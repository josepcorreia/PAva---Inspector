package ist.meic.pa;

import ist.meic.pa.util.Util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.ArrayList;

public class CommandM implements Command{

	@Override
	public void execute(Object obj) {
		// DO NOTHING

	}

	@Override
	public Object execute(Object obj, ArrayList<Object> inspectedObjects,
			HashMap<String, Object> savedObjects, String[] line)
					throws Exception {
		Class<?> objectClass = obj.getClass();

		while(objectClass.getSuperclass() != null) {
			Field[] fields = objectClass.getDeclaredFields();

			for(Field field : fields){
				if(field.getName().equals(line[1])) {
					field.setAccessible(true);

					try {

						Object ret = null;
						ret = Util.convert(line[2], field.getType());

						field.set(obj, ret);

						new CommandI().execute(obj);
					}  catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (RuntimeException e) {
						Util.printString("EXCEPTION "+e.getMessage());
					}

					return null;
				}	
			}
			objectClass = objectClass.getSuperclass();
		}	
		return null;
	}
}
