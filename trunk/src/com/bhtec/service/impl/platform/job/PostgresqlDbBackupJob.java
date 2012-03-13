/**
 *功能说明：
 * @author jacobliang
 * @time @Jan 21, 2011 @11:30:02 AM
 */
package com.bhtec.service.impl.platform.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bhtec.common.util.PostgresqlDbBackup;

public class PostgresqlDbBackupJob implements Job {
	private Logger log = Logger.getLogger(this.getClass());
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		new PostgresqlDbBackup();
		log.info("postgresql DB backup success. at " + new Date());
	}

}
