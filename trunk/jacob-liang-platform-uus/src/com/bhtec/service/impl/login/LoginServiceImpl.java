/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 3, 2010 @2:18:35 PM
 */
package com.bhtec.service.impl.login;

import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.ROLE_PRIVILEGE;
import static com.bhtec.common.constant.Common.USER_PRIVILEGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhtec.common.tools.PwdCrypt;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.login.LoginService;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.platform.module.ModuleService;
import com.bhtec.service.iface.uum.UumCommonService;
import com.bhtec.service.impl.BaseServiceImpl;

public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
	private UumCommonService uumCommonService;
	private PlatformCommonService platformCommonService;
	/**
	 * 功能说明：根据用户ID获得用户的角色机构信息
	 * @author jacobliang
	 * @param userId					用户ID
	 * @return	List<UumRoleUserRef>	用户角色机构信息
	 * @time Nov 3, 2010 10:25:38 AM
	 */
	public List<UumRoleUserRef> obtainRoleUserRefByUserId(long userId) {
		return uumCommonService.findRoleUserListByUserId(userId);
	}
	
	/**
	 * 功能说明：根据用户code和密码获得用户信息
	 * @author jacobliang
	 * @param userCode		用户登录输入的名称
	 * @param password		用户密码
	 * @return UumUser		查询用户对象
	 * @time Nov 3, 2010 10:14:04 AM
	 */
	public UumUser obtainUserByUserCodePwd(String userCode, String password) throws ApplicationException{
		password = PwdCrypt.getInstance().encrypt(password);//明文加密成密文
		return uumCommonService.findUserByUserCodeAPwd(userCode, password);
	}
	
	/**
	 * 功能说明：根据登录用户ID或角色过滤模块操作权限
	 * @author jacobliang
	 * @param userId	用户ID
	 * @param roleId	角色ID
	 * @return	@return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 3, 2010 8:56:48 PM
	 */
	@SuppressWarnings("unchecked")
	public Map findFilterUserModOptPrivilege(long userId,long roleId) {
		Map modOptMap = null;
		boolean hasSpePri = uumCommonService.findUserHasSpecialPrivilege(userId);//用户是否有特殊操作权限
		if(hasSpePri){//如有根据用户ID过滤特殊权限
			modOptMap = uumCommonService.findUserModOptPriForLogin(userId, USER_PRIVILEGE);
		}else{//如没有根据角色ID过滤权限
			modOptMap = uumCommonService.findUserModOptPriForLogin(roleId, ROLE_PRIVILEGE);
		}
		return modOptMap;
	}
	/**
	 * 功能说明：当登录用户为管理员时查询所有模块操作
	 * @author jacobliang
	 * @return @return modMenuMap:1-list;2-map;3-map;4-map
	 * @time Nov 4, 2010 1:28:07 PM
	 */
	public Map findAllModuleOptForAdmin(){
		Map modMenuMap = platformCommonService.findFilterModuleMenu(platformCommonService.findAllModule());//所有模块菜单
		List<SysplModOptRef> sysplModOptRefList = platformCommonService.findAllSysplModOptRef();//所有操作按钮
		Map<String,List<SysplModuleMemu>> sysplModOptMap = new HashMap<String,List<SysplModuleMemu>>();//四级模块操作
		for(SysplModOptRef sysplModOptRef:sysplModOptRefList){
			SysplOperate sysplOperate = sysplModOptRef.getSysplOperate();//具体操作
			//操作按钮
			SysplModuleMemu sysplModuleMemuOpt = new SysplModuleMemu();
			sysplModuleMemuOpt.setModuleId(sysplModOptRef.getModOptId()+1000);//将模块操作关系的id加上1000,防id重复
			sysplModuleMemuOpt.setModName(sysplOperate.getOperateName());
			sysplModuleMemuOpt.setModImgCls(sysplOperate.getOptImgLink());
			sysplModuleMemuOpt.setOptFunLink(sysplOperate.getOptFunLink());//为操作准备功能链接
			sysplModuleMemuOpt.setModOrder(sysplOperate.getOptOrder());//操作按钮的顺序
			SysplModuleMemu sysplModuleMemu = sysplModOptRef.getSysplModuleMemu();//所属模块
			String modEnId = sysplModuleMemu.getModEnId();//模块英文id,作为操作列表map的key值
			if(sysplModOptMap.containsKey(modEnId)){
				List<SysplModuleMemu> modOptList = sysplModOptMap.get(modEnId);
				//根据操作顺序进行排序
				int listIndex = 0;//元素索引
				boolean orderBol = true;//是否插入指定索引元素
				for(SysplModuleMemu sysplModuleMemuOptt : modOptList){					
					int listModOrder = sysplModuleMemuOptt.getModOrder();//已经有的操作顺序
					int currModOrder = sysplModuleMemuOpt.getModOrder();//当前操作顺序
					if(listModOrder > currModOrder){
						modOptList.add(listIndex,sysplModuleMemuOpt);//顺序小的插在前
						orderBol = false;
						break;
					}
					listIndex++;
				}
				if(orderBol)
					modOptList.add(sysplModuleMemuOpt);//加到操作list中
			}else{
				List<SysplModuleMemu> modOptList = new ArrayList<SysplModuleMemu>();//操作list
				modOptList.add(sysplModuleMemuOpt);//加到操作list中
				sysplModOptMap.put(modEnId, modOptList);//设置map
			}
		}
		//modMenuMap:1-list;2-map;3-map;4-map
		modMenuMap.put(FOURTH, sysplModOptMap);
		return modMenuMap;
	}
	
	/**
	 * 功能说明：根据用户ID查询用户常用功能
	 * @author jacobliang
	 * @param userId	用户ID
	 * @return
	 * @time Nov 26, 2010 11:00:32 AM
	 */
	public List<UumUserCommfun> findUserCommFunByUserId(long userId){
		return this.uumCommonService.findUserCommFunByUserId(userId);
	}
	
	/**
	 * 功能说明：删除用户无用常用功能
	 * @author jacobliang
	 * @param 	uumUserCommfunList		 用户常用功能列表
	 * @throws ApplicationException
	 * @time Nov 26, 2010 10:57:57 AM
	 */
	public void deleteUserCommFunById(List<UumUserCommfun> uumUserCommfunList)throws ApplicationException{
		uumCommonService.deleteUserCommFunById(uumUserCommfunList);
	}
	
	public void setUumCommonService(UumCommonService uumCommonService) {
		this.uumCommonService = uumCommonService;
	}

	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
}
