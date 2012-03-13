/**
 *功能说明：
 * @author jacobliang
 * @time @Jan 19, 2011 @9:49:30 AM
 */
package com.bhtec.service.impl.platform.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bhtec.common.tools.SpringContextUtil;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.platform.logger.SysPlLoggerService;

public class LoggerJob implements Job {
	private Logger log = Logger.getLogger(this.getClass());
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String name = context.getJobDetail().getName();
		String days = SystemConifgXmlParse.getSystemConfigBean().getLogRecordKeepDays();
		String number = "-0123456789";
		for(int i=0;i<days.length();i++){
			char c = days.charAt(i);
			if(number.indexOf(c) < 0){
				return;
			}
		}
		log.info("log keep days: " + days);
		log.info("job name is: " + name+" "+(new Date()));
		SysPlLoggerService sysPlLoggerService = (SysPlLoggerService)SpringContextUtil.getBean("sysPlLoggerService");
		sysPlLoggerService.deleteLog(Integer.parseInt(days));
	}
}
