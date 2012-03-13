/**
 *功能说明：
 * @author jacobliang
 * @time @Aug 19, 2010 @5:37:57 PM
 */
package com.bhtec.service.iface.platform.mainframe;

import java.util.List;
import java.util.Map;

import com.bhtec.domain.pojohelper.platform.mainframe.MainFrame;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;

public interface MainFrameService  extends BaseService {
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public Map<String,List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException;
	/**
	 * 功能说明：根据条件查询所有主框架功能区
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 9, 2010 6:37:54 PM
	 */
	public Map<String,String> findMainFrame()throws ApplicationException;
	/**
	 * 功能说明：保存主页模版设置
	 * @author jacobliang
	 * @param mainFrame
	 * @throws 
	 * @time Aug 20, 2010 10:05:02 AM
	 */
	public void saveMainFrame(MainFrame mainFrame)throws ApplicationException;
}
