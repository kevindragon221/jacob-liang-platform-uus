/**
 *功能说明：删除日志Job
 * @author jacobliang
 * @time @Jan 19, 2011 @9:49:30 AM
 */
package com.bhtec.service.impl.platform.scheduler;

import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

public class JobTest implements StatefulJob {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String name = context.getJobDetail().getName();
		System.out.println("job name is: " + name+" "+(new Date()));
	}

}
