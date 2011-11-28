package com.bhtec.exception;

import java.lang.reflect.Field;

/**
 *定义例外编码
 *@author  jacobliang
 *@version 1.0
 */
public class ExceptionCode {
	public static final java.lang.String A00001 = "编码为空";
	
	public static void main(String[] args) throws Exception{
		try {
			String s = "a";
			if(s.equals("a"))
				throw new ApplicationException(ExceptionCode.A00001);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
