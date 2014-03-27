package ist.meic.pa.exceptions;

public class InspectException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object ret;  
	public InspectException(){
		
	}
	public InspectException(Object obj){
		ret=obj;
	}
	public Object getRetObject() {
		return ret;
	}
	
}
