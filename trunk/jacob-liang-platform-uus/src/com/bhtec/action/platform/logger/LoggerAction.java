/**
 *功能说明：系统参数管理
 * @author jacobliang
 * @time @Jul 26, 2010 @4:54:52 PM
 */
package com.bhtec.action.platform.logger;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bhtec.action.platform.PlatformBaseAction;
import com.bhtec.domain.pojo.platform.SysplSysOptLog;
import com.bhtec.service.iface.platform.logger.SysPlLoggerService;
import com.opensymphony.xwork2.ModelDriven;

public class LoggerAction extends PlatformBaseAction implements ModelDriven<SysplSysOptLog> {
	private static final long serialVersionUID = 10000L;
	Logger log = Logger.getLogger(this.getClass());
	private SysplSysOptLog sysplSysOptLog = new SysplSysOptLog();
	private String failMesg;
	private int count;
	private List<SysplSysOptLog> sysPlLoggerServiceList;
	private SysPlLoggerService sysPlLoggerService;
	
	/**
	 * 功能说明：根据系统参数ID查询系统参数
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findSysplSysOptLogContentById(){
		String optContent = sysPlLoggerService.findSysplSysOptLogContentById(new Long(getModViewRecId()));
		sysplSysOptLog.setOptContent(optContent);
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据条件查询系统参数
	 * @author jacobliang
	 * @return Map 1 list 2 总数
	 * @throws 
	 * @time Dec 1, 2010 2:51:21 PM
	 */
	public String findLoggerByCon(){
		Map map = sysPlLoggerService.findLoggerByCon(getStart(),getLimit(),sysplSysOptLog);
		sysPlLoggerServiceList = (List<SysplSysOptLog>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return this.SUCCESS;
	}
	
	
	public SysplSysOptLog getModel() {
		return sysplSysOptLog;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<SysplSysOptLog> getSysPlLoggerServiceList() {
		return sysPlLoggerServiceList;
	}

	public void setSysPlLoggerServiceList(
			List<SysplSysOptLog> sysPlLoggerServiceList) {
		this.sysPlLoggerServiceList = sysPlLoggerServiceList;
	}

	public void setSysPlLoggerService(SysPlLoggerService sysPlLoggerService) {
		this.sysPlLoggerService = sysPlLoggerService;
	}

}
