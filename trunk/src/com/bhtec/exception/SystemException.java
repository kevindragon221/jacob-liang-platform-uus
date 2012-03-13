package com.bhtec.exception;
/**
 *功能描述：
 *@since   Mar 17, 2010 8:56:36 PM
 *@author  jacobliang
 *@version 1.0
 */
public class SystemException extends RuntimeException {
	public SystemException(){
		super();
	}
	
	public SystemException(String mesg){
		super(mesg);
	}
	
	public SystemException(String mesg, Throwable rootCause) {
	        super(mesg);
	        rootCause.printStackTrace();
	}
}
