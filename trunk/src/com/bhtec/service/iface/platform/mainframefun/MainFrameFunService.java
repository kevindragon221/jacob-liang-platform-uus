/**
 *功能说明：
 * @author jacobliang
 * @time @Jul 24, 2010 @10:05:20 PM
 */
package com.bhtec.service.iface.platform.mainframefun;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.bhtec.common.tools.XmlOpUtil;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.BaseService;
import com.bhtec.service.impl.platform.mainframefun.MainFrameFunServiceImpl;

public interface MainFrameFunService extends BaseService{
	/**
	 * 功能说明：增加功能區,同时包含修改功能区功能
	 * @author jacobliang
	 * @param	mainFrameFun	功能区对象
	 * @throws 
	 * @time Jul 24, 2010 4:31:07 PM
	 */
	public void saveMainFrameFun(MainFrameFun mainFrameFun)throws ApplicationException;
	/**
	 * 功能说明：修改功能區
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:31:52 PM
	 */
	public void modifyMainFrameFun();
	/**
	 * 功能说明：刪除功能區
	 * @author jacobliang
	 * @param	delRecIds	功能区名称
	 * @throws 
	 * @time Jul 24, 2010 4:32:29 PM
	 */
	public void deleteMainFrameFun(String delRecIds)throws ApplicationException;
	/**
	 * 功能说明：
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public MainFrameFun findMainFrameFunByFunName(String modViewRecId)throws ApplicationException;
	/**
	 * 功能说明：根据条件查询所有主框架功能区
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 9, 2010 6:37:54 PM
	 */
	public Map findMainFrameFunByCon(int start,int limit,String funName)throws ApplicationException;
	/**
	 * 功能说明：根据id查找功能区
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public MainFrameFun findMainFrameFunByFunId(String modViewRecId)throws ApplicationException;
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public Map<String,List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException;
}
