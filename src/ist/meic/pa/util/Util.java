package ist.meic.pa.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Util {


	// Init ---------------------------------------------------------------------------------------

	private static final Map<String, Method> CONVERTERS = new HashMap<String, Method>();

	private static final Map<Class<?>, Class<?>> WRAPPERS = new HashMap<Class<?>, Class<?>>();

	static {
		// Preload converters.
		Method[] methods = Util.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getParameterTypes().length == 1) {
				// Converter should accept 1 argument. This skips the convert() method.
				CONVERTERS.put(method.getParameterTypes()[0].getName() + "_"
						+ method.getReturnType().getName(), method);
			}
		}

		WRAPPERS.put(boolean.class, Boolean.class);
		WRAPPERS.put(byte.class, Byte.class);
		WRAPPERS.put(short.class, Short.class);
		WRAPPERS.put(char.class, Character.class);
		WRAPPERS.put(int.class, Integer.class);
		WRAPPERS.put(long.class, Long.class);
		WRAPPERS.put(float.class, Float.class);
		WRAPPERS.put(double.class, Double.class);
	}

	// Action -------------------------------------------------------------------------------------

	/**
	 * Convert the given object value to the given class.
	 * @param from The object value to be converted.
	 * @param to The type class which the given object should be converted to.
	 * @return The converted object value.
	 * @throws NullPointerException If 'to' is null.
	 * @throws UnsupportedOperationException If no suitable converter can be found.
	 * @throws RuntimeException If conversion failed somehow. This can be caused by at least
	 * an ExceptionInInitializerError, IllegalAccessException or InvocationTargetException.
	 */
	public static <T> T convert(Object from, Class<T> to) {

		// Null is just null.
		if (from == null) {
			return null;
		}

		// Can we cast? Then just do it.
		if (to.isAssignableFrom(from.getClass())) {
			return to.cast(from);
		}

		// Lookup the suitable converter.
		String converterId = from.getClass().getName() + "_" + to.getName();
		Method converter = CONVERTERS.get(converterId);
		if (converter == null) {
			throw new UnsupportedOperationException("Cannot convert from " 
					+ from.getClass().getName() + " to " + to.getName()
					+ ". Requested converter does not exist.");
		}

		// Convert the value.
		try {
			// Methods are static: obj argument in invoke method can be null
			if(to.isPrimitive()) {
				to = (Class<T>) wrap(to, converter.invoke(null, from).getClass());
			}

			return to.cast(converter.invoke(null, from));
		} catch (Exception e) {
			throw new RuntimeException("Cannot convert from " 
					+ from.getClass().getName() + " to " + to.getName()
					+ ". Conversion failed with " + e.getMessage(), e);
		}
	}




	public static <T> Class<T> wrap(Class<?> from, Class<T> to) {
		return (Class<T>) WRAPPERS.get(from);
	}

	/**
	 * Converts String to Integer.
	 * @param value The String to be converted.
	 * @return The converted Integer value.
	 */
	public static Integer stringToInteger(String value) {
		return Integer.valueOf(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static Boolean stringToBoolean(String value) {
		return Boolean.valueOf(value);
	}

	/**
	 * Converts String to Integer.
	 * @param value The String to be converted.
	 * @return The converted Integer value.
	 */
	public static Double stringToDouble(String value) {
		return Double.valueOf(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static Float stringToFloat(String value) {
		return Float.valueOf(value);
	}

	/**
	 * Converts String to Integer.
	 * @param value The String to be converted.
	 * @return The converted Integer value.
	 */
	public static Short stringToShort(String value) {
		return Short.valueOf(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static Long stringToLong(String value) {
		return Long.valueOf(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static Character stringToCharacter(String value) {
		return value.charAt(0);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static int stringToint(String value) {
		return Integer.parseInt(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static double stringTodouble(String value) {
		return Double.parseDouble(value);
	}

	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static float stringTofloat(String value) {
		return Float.parseFloat(value);
	}
	
	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static char stringTochar(String value) {
		return value.charAt(0);
	}
	
	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static long stringTolong(String value) {
		return Long.parseLong(value);
	}
	
	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static short stringToshort(String value) {
		return Short.parseShort(value);
	}
	
	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static byte stringTobyte(String value) {
		return Byte.parseByte(value);
	}
	
	/**
	 * Converts String to Boolean.
	 * @param value The String to be converted.
	 * @return The converted Boolean value.
	 */
	public static boolean stringToboolean(String value) {
		return Boolean.parseBoolean(value);
	}
}
