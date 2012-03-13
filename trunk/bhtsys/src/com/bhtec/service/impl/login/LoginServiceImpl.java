/**
 *功能说明：
 * @author jacobliang
 * @time @Nov 3, 2010 @2:18:35 PM
 */
package com.bhtec.service.impl.login;

import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.COMMON_FUN;
import static com.bhtec.common.constant.Common.FOURTH;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE_ALL;
import static com.bhtec.common.constant.Common.THIRD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bhtec.common.tools.PwdCrypt;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.domain.pojo.platform.SysplModOptRef;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.platform.SysplOperate;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojo.uum.UumUserAgent;
import com.bhtec.domain.pojo.uum.UumUserCommfun;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.login.LoginService;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.UumCommonService;
import com.bhtec.service.impl.BaseServiceImpl;

public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
	private UumCommonService uumCommonService;
	private PlatformCommonService platformCommonService;
	/**
	 * 功能说明：预登录 代理 多角色
	 * @author jacobliang
	 * @return
	 * @time Feb 23, 2012 10:03:26 AM
	 */
	public Map preLogin(UumUser uumUser){
		long userId = uumUser.getUserId();
		List<UumUserAgent> userAgentList = uumCommonService.findUumUserAgentByAgentUserId(userId);
		List<UumRoleUserRef> uumRoleUserRefList = obtainRoleUserRefByUserId(uumUser.getUserId());
		Map map = new HashMap();
		map.put(AGENT_USER, userAgentList);
		map.put(ROLE_LIST, uumRoleUserRefList);
		
		return map;
	}
	
	/**
	 * 功能说明：权限过滤
	 * @author jacobliang
	 * @param userId
	 * @param roleId
	 * @param organId
	 * @param organRoleId
	 * @param userCode
	 * @param agentId
	 * @return
	 * @throws ApplicationException
	 * @time Feb 24, 2012 11:49:04 AM
	 */
	@SuppressWarnings("unchecked")
	public Map privilegeFilter(Long userId, Long roleId, 
							   Long organId, Long organRoleId,
							   String userCode,Long agentId)throws ApplicationException{
		boolean isAllPrivilege = true;//是否代理全部权限
		List privilegeIdList = null;
		if(agentId != null){
			privilegeIdList = uumCommonService.findAgentPrivilegeByAgentId(agentId.toString());
			if(privilegeIdList.size() == 1){
				String privilegeId = (String)privilegeIdList.get(0);
				if(!PRIVILEGE_SCOPE_ALL.equals(privilegeId)){
					isAllPrivilege = false;
				}
			}else{
				isAllPrivilege = false;
			}
		}
		Map map = null;
		if(isAllPrivilege){
			if(isAdministrator(userCode)){//是管理员
				//加载全部权限
				map = findAllModuleOptForAdmin();
			}else{//非管理员
				//模块权限过滤，用户是否有特殊权限，并过滤特殊权限
				map = uumCommonService.findUserPrivilegeForLogin(userId, roleId,organId,organRoleId);
			}
		}else{
			map = uumCommonService.findUserPrivilegeForAgentLogin(userId, roleId, organId, 
					organRoleId, privilegeIdList);
		}
		Map thirdMap = (Map)map.get(THIRD);
		//当前用户常用功能
		List<UumUserCommfun> uumUserCommfunList = findUserCommFunByUserId(userId);
		//有效的常用功能
		List<UumUserCommfun> availabilityCommFunList = new ArrayList<UumUserCommfun>();
		List<SysplModuleMemu> assignUumUserCommfunList = new ArrayList<SysplModuleMemu>();
		if(uumUserCommfunList != null && uumUserCommfunList.size()>0){
			Iterator iterator = thirdMap.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry entry = (Map.Entry) iterator.next();
				List<SysplModuleMemu> secondMenuList = (List<SysplModuleMemu>)thirdMap.get(entry.getKey());
				for(UumUserCommfun uumUserCommfun:uumUserCommfunList){
					for(SysplModuleMemu sysplModuleMemu:secondMenuList){
						if(uumUserCommfun.getId().getModuleId().longValue()
								== sysplModuleMemu.getModuleId().longValue()){
							assignUumUserCommfunList.add(sysplModuleMemu);
							availabilityCommFunList.add(uumUserCommfun);
							break;
						}
					}
				}
			}
		}
		//清除无用的常用功能数据
		uumUserCommfunList.removeAll(availabilityCommFunList);
		deleteUserCommFunById(uumUserCommfunList);
		map.put(COMMON_FUN,assignUumUserCommfunList);
		return map;
	}
	/**
	 * 功能说明：判断用户是否为管理员,管理员信息指定在配置文件中
	 * @author jacobliang
	 * @return
	 * @throws ApplicationException
	 * @time Nov 4, 2010 2:13:53 PM
	 */
	public boolean isAdministrator(String userCode) throws ApplicationException{
		//通过配置XML获得管理员用户，管理员则不需要权限过滤
		SystemConifgXmlParse xMLSystemConifg = SystemConifgXmlParse.getInstance();
		List<String> adminList = xMLSystemConifg.getSystemConfigBean().getSystemMgr();
		if(adminList.contains(userCode)){//管理员
			return true;
		}
		return false;
	}
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
