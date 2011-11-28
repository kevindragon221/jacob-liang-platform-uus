/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 16, 2010 @1:31:02 PM
 */
package com.bhtec.common.listener;

import static com.bhtec.common.constant.Common.USER_CODE;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class OnlineCounterListener implements HttpSessionListener {
	Logger log = Logger.getLogger(this.getClass());
	public void sessionCreated(HttpSessionEvent sesEvent) {
		OnlineCounter.getInstance().raise();
	}

	public void sessionDestroyed(HttpSessionEvent sesEvent) {
		OnlineCounter.getInstance().reduce();
		HttpSession session = sesEvent.getSession();
		Object userName = session.getAttribute(USER_CODE);
		if(userName != null)
			OnlineCounter.sessionMap.remove((String)userName);
		log.info("destroyed session,userName is: " + userName);
	}

}
