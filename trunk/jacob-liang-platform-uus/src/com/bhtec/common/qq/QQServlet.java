/**
 *功能说明：
 * @author jacobliang
 * @time @Aug 26, 2010 @3:18:05 PM
 */
package com.bhtec.common.qq;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.cometd.Bayeux;
import org.mortbay.cometd.ext.AcknowledgedMessagesExtension;
import org.mortbay.cometd.ext.TimesyncExtension;


public class QQServlet extends HttpServlet {
	public QQServlet() {
		System.out.println("**********************************************");
		System.out.println("QQServlet");
		System.out.println("**********************************************");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		Bayeux bayeux = (Bayeux) getServletContext().getAttribute(Bayeux.ATTRIBUTE);
		new QQService(bayeux);
		bayeux.addExtension(new TimesyncExtension());
		bayeux.addExtension(new AcknowledgedMessagesExtension());
	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		((HttpServletResponse) res).sendError(503);
	}
}
