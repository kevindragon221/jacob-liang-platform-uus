/**
 *功能说明：主框架功能区
 * @author jacobliang
 * @time @Jul 24, 2010 @10:01:53 PM
 */
package com.bhtec.action.platform.mainframefun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bhtec.action.BaseAction;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.mainframefun.MainFrameFunService;
import com.opensymphony.xwork2.ModelDriven;
import static com.bhtec.common.constant.Common.*;

public class MainFrameFunAction extends BaseAction implements ModelDriven<MainFrameFun>{
	private static final long serialVersionUID = 10000L;
	private MainFrameFunService mainFrameFunService = null;
	private MainFrameFun mainFrameFun = new MainFrameFun(); 
	private List<MainFrameFun> mainFrameFunList = new ArrayList<MainFrameFun>();
	private int count = 0;
	private String mfFunName = "";
	private String failMesg;
	/**
	 * 功能说明：增加功能區
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:31:07 PM
	 */
	public void saveMainFrameFun(){
		try{
			mainFrameFunService.setHttpServletRequest(this.getHttpServletRequest());
			mainFrameFunService.saveMainFrameFun(mainFrameFun);
			this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		}catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：刪除功能區
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:32:29 PM
	 */
	public String deleteMainFrameFun(){
		try{
			mainFrameFunService.setHttpServletRequest(this.getHttpServletRequest());
			String delRecIds = this.getDelRecIds();
			mainFrameFunService.deleteMainFrameFun(delRecIds);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据名称查找功能区
	 * @author jacobliang
	 * @param	mainFrameName	功能区名称
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public String findMainFrameFunByFunName(){
		try{
			mainFrameFun = mainFrameFunService.findMainFrameFunByFunName(this.getHttpServletRequest().getParameter("modViewRecId"));
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：查找所有主框架功能区
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public String findMainFrameFunByCon(){
		try{
			Map map = mainFrameFunService.findMainFrameFunByCon(getStart(), this.getLimit(), mfFunName);
			mainFrameFunList = (List<MainFrameFun>)map.get(BUSI_LIST);
			count = (Integer)map.get(TOTAL_PROPERTY);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	/**
	 * 功能说明：根据id查找功能区
	 * @author jacobliang
	 * @throws 
	 * @time Jul 24, 2010 4:34:52 PM
	 */
	public String findMainFrameFunByFunId(){
		try{
			mainFrameFun = mainFrameFunService.findMainFrameFunByFunId(this.getModViewRecId());
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public void setMainFrameFunService(MainFrameFunService mainFrameFunService) {
		this.mainFrameFunService = mainFrameFunService;
	}

	public MainFrameFun getModel() {
		return mainFrameFun;
	}

	public List<MainFrameFun> getMainFrameFunList() {
		return mainFrameFunList;
	}

	public void setMainFrameFunList(List<MainFrameFun> mainFrameFunList) {
		this.mainFrameFunList = mainFrameFunList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMfFunName() {
		return mfFunName;
	}

	public void setMfFunName(String mfFunName) {
		this.mfFunName = mfFunName;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

}
