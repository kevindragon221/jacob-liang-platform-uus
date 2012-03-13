/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 16, 2010 @5:26:13 PM
 */
package com.bhtec.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManger {
	public void sessionDestroyed(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
