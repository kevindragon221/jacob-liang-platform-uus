/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 4, 2010 @9:53:13 AM
 */
package com.bhtec.service.impl.commonused;

import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.ServiceVariable.USER_SET_PAGELAYOUT;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bhtec.domain.pojo.uum.UumUserPageLayout;
import com.bhtec.domain.pojohelper.platform.mainframefun.MainFrameFun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.commonused.UserPageLayoutService;
import com.bhtec.service.iface.platform.mainframefun.MainFrameFunService;
import com.bhtec.service.impl.BaseServiceImpl;

public class UserPageLayoutServiceImpl extends BaseServiceImpl implements
		UserPageLayoutService {
	private MainFrameFunService mainFrameFunService;
	/**
	 * 功能说明：根据用户ID查询页面布局
	 * @author jacobliang
	 * @param userId	根据用户ID主键查询用户所设置页面布局
	 * @return
	 * @time Nov 4, 2010 9:50:25 AM
	 */
	public UumUserPageLayout findUserPageLayoutByUserId(long userId) {
		UumUserPageLayout uumUserPageLayout = 
			(UumUserPageLayout)this.getBaseDao()
			.getPojoById("com.bhtec.domain.pojo.uum.UumUserPageLayout", userId);
		return uumUserPageLayout;
	}
	/**
	 * 功能说明：保存用户设置的页面布局
	 * @author jacobliang
	 * @param uumUserPageLayout		用户页面布局对象
	 * @time Nov 4, 2010 9:47:48 AM
	 */
	public void saveUserPageLayout(UumUserPageLayout uumUserPageLayout)throws ApplicationException{
		UumUserPageLayout uumUserPageLayoutOld = findUserPageLayoutByUserId(uumUserPageLayout.getUserId());
		if(uumUserPageLayoutOld == null){//用户没有设置页面布局则保存
			this.getBaseDao().save(uumUserPageLayout);
		}else{//拷贝最新的值并更新
			try {
				BeanUtils.copyProperties(uumUserPageLayoutOld, uumUserPageLayout);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			this.getBaseDao().update(uumUserPageLayoutOld);
		}
		StringBuffer optContent = new StringBuffer();
		optContent.append("用户ID为：" + uumUserPageLayout.getUserId()+"设置页面布局为： ")
		.append("北："+uumUserPageLayout.getHeader()+SPLIT)
		.append("导航："+uumUserPageLayout.getNavigate()+SPLIT)
		.append("西："+uumUserPageLayout.getOutlookbar()+SPLIT)
		.append("中："+uumUserPageLayout.getMainpage()+SPLIT)
		.append("南："+uumUserPageLayout.getStatebar()+SPLIT);
		this.saveLog(LOG_LEVEL_FIRST, USER_SET_PAGELAYOUT, SAVE_OPT, optContent.toString(), uumUserPageLayout.getUserId().toString());
	}
	/**
	 * 功能说明：获得定义的所有功能区列表
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Aug 19, 2010 7:37:38 PM
	 */
	public Map<String, List<MainFrameFun>> findAllMainFrameFun()throws ApplicationException {
		return mainFrameFunService.findAllMainFrameFun();//主框架功能区方法
	}

	public void setMainFrameFunService(MainFrameFunService mainFrameFunService) {
		this.mainFrameFunService = mainFrameFunService;
	}

}
