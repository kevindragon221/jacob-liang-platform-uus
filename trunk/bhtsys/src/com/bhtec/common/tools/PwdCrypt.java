package com.bhtec.common.tools;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *功能描述：
 *创建时间：Mar 9, 2009 11:18:04 AM 
 *@author lianglp
 *@version 1.0
 */

public class PwdCrypt {
	final String love = "liangs2yixiu!@#$%^&";
	
	public static PwdCrypt getInstance(){
		return new PwdCrypt();
	}
    /**
     * 加密操作
     * @param data
     * @return
     */
    public String encrypt(String data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(simplecrypt(data).getBytes());
    }
    
    /**
     * 解密操作
     * @param data
     * @return
     * @throws IOException
     */
    public String decrypt(String data) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] result = null;
		try {
			result = decoder.decodeBuffer(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return simplecrypt(new String(result));
    }
    
    /**
     * 
     *功能描述：进行常量异或
     *@author lianglp
     *@param
     *@return String
     *@throws
     *@version 1.0
     *@date Mar 9, 200911:44:02 AM
     */
    public String simplecrypt(String data){
    	char[] a = data.toCharArray();
		for (int i = 0; i < a.length; i++) {
			for(int j=0;j<love.length();j++){
				char c = love.charAt(j);
				a[i] = (char) (a[i] ^ c);
			}
		}
		String s = new String(a);
		return s;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PwdCrypt pwdCrypt = new PwdCrypt();
		String estr = pwdCrypt.encrypt("admin");
		System.out.println("estr is : " + estr);
		String dstr = pwdCrypt.decrypt(estr);
		System.out.println("dstr is : " + dstr);
		  BASE64Decoder decoder = new BASE64Decoder();
	        byte[] result = null;
			try {
				result = decoder.decodeBuffer("UhIUBhEXF1Y=");
				System.out.println("dstr is : " + pwdCrypt.simplecrypt(new String(result)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
