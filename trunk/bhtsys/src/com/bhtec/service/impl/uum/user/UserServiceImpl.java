/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:22:30 PM
 */
package com.bhtec.service.impl.uum.user;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.DISABLE;
import static com.bhtec.common.constant.Common.ENABLE;
import static com.bhtec.common.constant.Common.LOG_LEVEL_FIRST;
import static com.bhtec.common.constant.Common.LOG_LEVEL_SECOND;
import static com.bhtec.common.constant.Common.MODIFY_OPT;
import static com.bhtec.common.constant.Common.OWNER_TYPE_USR;
import static com.bhtec.common.constant.Common.SAVE_OPT;
import static com.bhtec.common.constant.Common.SPLIT;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.ServiceVariable.DISABLE_DIS;
import static com.bhtec.common.constant.ServiceVariable.ROLE_CSS;
import static com.bhtec.common.constant.ServiceVariable.USER_MGR;
import static com.bhtec.common.constant.ServiceVariable.Y;
import static com.bhtec.common.tools.UtilTools.isNullOrEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bhtec.common.tools.UtilTools;
import com.bhtec.common.util.SystemConifgXmlParse;
import com.bhtec.dao.iface.uum.user.UserDao;
import com.bhtec.domain.pojo.uum.UumOrgRoleRef;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRole;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.domain.pojohelper.tree.TreeVoExtend;
import com.bhtec.exception.ApplicationException;
import com.bhtec.service.iface.commonused.UserCommFunService;
import com.bhtec.service.iface.uum.organ.OrganService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleOrganService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.iface.uum.user.UserAgentService;
import com.bhtec.service.iface.uum.user.UserService;
import com.bhtec.service.impl.BaseServiceImpl;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	Logger log = Logger.getLogger(this.getClass());
	private UserDao userDao;
	private RoleOrganService roleOrganService;
	private OrganService organService;
	private RoleUserService roleUserService;
	private PrivilegeService privilegeService;
	private UserAgentService userAgentService;
	private UserCommFunService userCommFunService;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 功能说明：停用启用用户 删除用户所属角色并移到无角色用户 删除用户所拥有操作权限
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param  UserId			用户ID
	 * @return	boolean true成功
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 * 1 移除用户角色 2 移除用户代理 3 移除用户代理权限 4 移除用户常用功能 5 移除用户操作行权限
	 */
	public boolean disEnableUser(long userId, String disEnableFlag)
			throws ApplicationException {
		if(DISABLE.equals(disEnableFlag)){
			UumUser uumUser = this.findUserByUserId(userId);
			uumUser.setStatus(DISABLE);
			UumOrgan uumOrgan = new UumOrgan();
			uumOrgan.setOrgId(0L);
			uumUser.setUumOrgan(uumOrgan);//更改用户所属机构为0
			roleUserService.deleteRoleUserRef(userId);//删除用户所属角色
			Set uumRoleUserRefs = new HashSet();//移到无角色用户
			UumRoleUserRef uumRoleUserRef = new UumRoleUserRef();
			UumOrgRoleRef uumOrgRoleRef = new UumOrgRoleRef();
			uumOrgRoleRef.setOrgRoleId(0L);
			uumRoleUserRef.setUumUser(uumUser);
			uumRoleUserRef.setIsDefault(Y);
			uumRoleUserRef.setUumOrgRoleRef(uumOrgRoleRef);
			uumRoleUserRefs.add(uumRoleUserRef);
			uumUser.setUumRoleUserRefs(uumRoleUserRefs);
			privilegeService.deletePrivilegeByOwnerIdType(userId, OWNER_TYPE_USR);//删除用户所拥有操作、行权限
			userAgentService.deleteAllUserAgent(userId);//2 移除用户代理 3 移除用户代理权限
			userCommFunService.deleteUserCommFunByUserId(userId);//4 移除用户常用功能
			this.writeLog(uumUser, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}else{
			UumUser uumUser = this.findUserByUserId(userId);
			uumUser.setStatus(ENABLE);
			this.writeLog(uumUser, LOG_LEVEL_SECOND, DISABLE_DIS);
			return true;
		}
	}

	/**
	 * 功能说明：查询所有用户信息
	 * @author jacobliang
	 * @return	List<UumUser>	所有用户列表
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public List<UumUser> findAllUser() {
		return this.userDao.findAllUser();
	}

	/**
	 * 功能说明：机构角色关系树，查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	treeId	机构或角色id
	 * @param	isCheckbox	是否需要checkbox
	 * @return	List<UumOrgan>	所有机构和角色
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public List<TreeVo> findNextLevelChildNodes(String treeId,String isCheckbox) {
		int temp = treeId.indexOf("_");
		int orgId = 0;
		if(temp >= 0)
			orgId = Integer.parseInt(treeId.substring(0,temp));
		
		List<UumOrgRoleRef> roleList = roleOrganService.findRoleOrganListByOrgId(orgId);
		List<TreeVo> orgList = organService.findNextLevelChildNodes(orgId,0);
		
		for(TreeVo treeVo:orgList){
			treeVo.setId(treeVo.getId()+"_1");//机构无checkbox
		}
		
		for(UumOrgRoleRef uumOrgRoleRef:roleList){
			UumRole role = uumOrgRoleRef.getUumRole();
			TreeVo treeVo ;
			if(Y.equals(isCheckbox)){
				TreeVoExtend treeVoExt = new TreeVoExtend();
				treeVoExt.setChecked(false);
				treeVo = treeVoExt;//角色带有checkbox
			}else{
				treeVo = new TreeVo();
			}
			
			treeVo.setId(uumOrgRoleRef.getOrgRoleId()+"_2");
			treeVo.setText(role.getRoleName());
			treeVo.setIconCls(ROLE_CSS);
			treeVo.setLeaf(true);
			orgList.add(treeVo);
		}
		return orgList;
	}

	/**
	 * 功能说明：根据条件分页查询用户
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	格式：id_1:机构 id_2:角色 查询某个树的节点
	 * @param	userName 用户名称
	 * @param	userCode 用户code
	 * @return	map 1.list 2.count
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public Map findUserByCon(int start, int limit, String treeId,
			String userName, String userCode) {
		String organIds = "";
		if(!isNullOrEmpty(treeId)){
			if(treeId.length() >= 3 ){
				int id = Integer.parseInt(treeId.substring(0,treeId.indexOf("_")));
				List<UumOrgan> list = this.organService.findChildNodeList(id,0);
				for(UumOrgan uumOrgan:list){
					String organId = uumOrgan.getOrgId().toString();
					organIds = organIds.equals("")?organId:organIds+","+organId;
				}
			}
		}
			
		return userDao.findUserByCon(start, limit, treeId, organIds, userName, userCode);
	}
	
	/**
	 * 功能说明：查询用户信息为管理员分配
	 * @author jacobliang
	 * @param	start	开始记录数
	 * @param	limit	每页显示条数
	 * @param	treeId	格式：id_1:机构 id_2:角色 查询某个树的节点
	 * @param	userName 用户名称
	 * @param	userCode 用户code
	 * @return	map 1.list 2.count
	 * @throws 
	 * @version 2.0
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public Map findUserInfoForAdminSet(int start,int limit,String userName,String userCode){
		Map map = userDao.findUserByCon(start, limit, "", "", userName, userCode);
		List<UumUser> userList = (List<UumUser>)map.get(BUSI_LIST);
		int count = (Integer)map.get(TOTAL_PROPERTY);
		List<UumUser> systemAdminCodeList = new ArrayList<UumUser>();
		SystemConifgXmlParse systemConifg = SystemConifgXmlParse.getInstance();
		List<String> systemAdminCode = systemConifg.getSystemConfigBean().getSystemMgr();
		int subtractCount = 0;
		if(userList != null && userList.size() > 0){
			for(UumUser uumUser:userList){
				for(String userCodee:systemAdminCode){
					if(uumUser.getUserCode().equals(userCodee)){
						systemAdminCodeList.add(uumUser);
						subtractCount++;
					}
				}
			}
			userList.removeAll(systemAdminCodeList);
			count = count - subtractCount;
			map.put(BUSI_LIST, userList);
			map.put(TOTAL_PROPERTY, count);
		}
		map.put("systemAdminCodeList", systemAdminCodeList);
		map.put("systemAdminCount", subtractCount);
		return map;
	}

	/**
	 * 功能说明：查询用户号是否重复
	 * @author jacobliang
	 * @param	userCode	用户登录名
	 * @return	boolean true用户名存在 false不存在
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public boolean findUserByUserCode(String userCode) {
		int count = this.userDao.findUserByUserCode(userCode);
		if(count > 0)
			return true;
		return false;
	}

	/**
	 * 功能说明：根据用户ID查询某个用户
	 * @author jacobliang
	 * @param 	userId		用户ID
	 * @return	UumUser		用户对象
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public UumUser findUserByUserId(long userId) {
		UumUser uumUser = (UumUser)this.userDao.getPojoById("com.bhtec.domain.pojo.uum.UumUser", userId);
		uumUser.setBelongOrganId(uumUser.getUumOrgan().getOrgId());
		
		if(!uumUser.getUumRoleUserRefs().isEmpty()){
			Object[] objs = uumUser.getUumRoleUserRefs().toArray();
			for(int i=0;i<objs.length;i++){
				UumRoleUserRef uumRoleUserRef = (UumRoleUserRef)objs[i];
				if(Y.equals(uumRoleUserRef.getIsDefault())){
					String roleName = uumRoleUserRef.getUumOrgRoleRef().getUumRole().getRoleName();
					long orgRoleId = uumRoleUserRef.getUumOrgRoleRef().getOrgRoleId();
					uumUser.setUumRoleName(roleName);
					uumUser.setUumOrgRoleId(orgRoleId);
					break;
				}
			}
		}
		return uumUser;
	}

	/**
	 * 功能说明：修改用户
	 * @author jacobliang
	 * @param UumUser	用户对象
	 * @return
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public void modifyUser(UumUser uumUser) throws ApplicationException {
		//设置用户所属机构
		UumOrgRoleRef uumOrgRoleRef = roleOrganService
				.findUumOrgRoleRefById(uumUser.getUumOrgRoleId()==null?0:uumUser.getUumOrgRoleId());
		uumUser.setUumOrgan(uumOrgRoleRef.getUumOrgan());
		uumUser.setUserPassword(UtilTools.encrypt(uumUser.getUserPassword()));//加密
		this.userDao.update(uumUser);//保存用户
		
		writeLog(uumUser, LOG_LEVEL_SECOND, MODIFY_OPT);
	}

	/**
	 * 功能说明：保存用户
	 * @author jacobliang
	 * @param uumUser	用户对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 30, 2010 3:11:23 PM
	 */
	public void saveUser(UumUser uumUser) throws ApplicationException {
		//设置用户所属机构
		UumOrgRoleRef uumOrgRoleRef = roleOrganService.findUumOrgRoleRefById(uumUser.getUumOrgRoleId());
		uumUser.setUumOrgan(uumOrgRoleRef.getUumOrgan());
		uumUser.setUserPassword(UtilTools.encrypt(uumUser.getUserPassword()));//加密
		Set uumRoleUserRefs = new HashSet();
		UumRoleUserRef uumRoleUserRef = new UumRoleUserRef();//默认角色
		uumRoleUserRef.setUumUser(uumUser);
		uumRoleUserRef.setIsDefault(Y);
		uumRoleUserRef.setUumOrgRoleRef(uumOrgRoleRef);
		uumRoleUserRefs.add(uumRoleUserRef);
		uumUser.setUumRoleUserRefs(uumRoleUserRefs);
		
		this.userDao.save(uumUser);//保存用户
		this.writeLog(uumUser, LOG_LEVEL_FIRST, SAVE_OPT);
	}
	
	/**
	 * 功能说明：公用模块写日志
	 * @author jacobliang
	 * @param UumOrgan	模块对象
	 * @throws ApplicationException
	 * @throws 
	 * @time Sep 16, 2010 8:55:43 PM
	 */
	private void writeLog(UumUser uumUser,String logLevel,String opt)throws ApplicationException{
		StringBuffer logContent = new StringBuffer();
		logContent.append(uumUser.getUserCode()+SPLIT).append(uumUser.getUserName()+SPLIT)
		.append(uumUser.getUumRoleName()+SPLIT);
		saveLog(logLevel, USER_MGR,opt,logContent.toString(), uumUser.getUserId()+"");
	}
	/**
	 * 功能说明：根据用户code和密码获得用户信息
	 * @author jacobliang
	 * @param userCode	用户登录名
	 * @param password	用户密码
	 * @return	UumUser	用户对象
	 * @time Nov 3, 2010 2:20:11 PM
	 */
	public UumUser findUserByUserCodeAPwd(String userCode,String password) throws ApplicationException{
		if(isNullOrEmpty(userCode) || isNullOrEmpty(userCode))return null;
		List<UumUser> uumUserList = this.userDao.findUserByUserCodeAPwd(userCode, password);
		if(uumUserList == null || uumUserList.size()==0)return null;
		if(uumUserList.size() > 1)
			throw new ApplicationException("用户信息重复");
		UumUser uumUser = uumUserList.get(0);
		return uumUser;
	}
	/**
	 * 功能说明：当为用户分配多个角色时，更改用户的默认角色
	 * @author jacobliang
	 * @param organId
	 * @param userId
	 * @throws ApplicationException
	 * @time Nov 7, 2010 11:09:05 AM
	 */
	public void modifyUserBelongOrgIdAssignRole(long organId,long userId){
		this.userDao.modifyUserBelongOrgIdAssignRole(organId, userId);
	}
	
	/**
	 * 功能说明：根据用户ID重置用户密码
	 * @author jacobliang
	 * @param userId
	 * @param userName	用户名
	 * @param userDefaultPwd	用户默认密码
	 * @throws ApplicationException
	 * @time Nov 7, 2010 6:21:20 PM
	 */
	public void resetPwdByUserId(long userId,String userName,String userDefaultPwd)throws ApplicationException{
		this.userDao.resetPwdByUserId(userId, UtilTools.encrypt(userDefaultPwd));
		StringBuffer logContent = new StringBuffer();
		logContent.append("重置密码用户："+userName);
		saveLog(LOG_LEVEL_SECOND, USER_MGR,"重置密码",logContent.toString(), userId+"");
	}
	
	/**
	 * 功能说明：修改用户的照片URL为空
	 * @author jacobliang
	 * @param userId	用户ID
	 * @throws ApplicationException
	 * @time Nov 9, 2010 2:02:53 PM
	 */
	public void modifyUserPhotoUrlToEmpty(long userId)throws ApplicationException{
		this.userDao.modifyUserPhotoUrlToEmpty(userId);
	}
	
	/**
	 * 功能说明：根据用户ID修改用户号 用户名 用户密码 
	 * @author jacobliang
	 * @param userId		用户ID
	 * @param userCode		用户号
	 * @param userName		用户名
	 * @param password		用户密码
	 * @throws ApplicationException
	 * @time Nov 18, 2010 5:29:52 PM
	 */
	public void modifyUserInfo(UumUser uumUserNew)throws ApplicationException{
		UumUser uumUser = (UumUser)this.userDao.getPojoById("com.bhtec.domain.pojo.uum.UumUser", uumUserNew.getUserId());
		uumUser.setUserCode(uumUserNew.getUserCode());
		uumUser.setUserName(uumUserNew.getUserName());
		uumUser.setUserPassword(uumUserNew.getUserPassword());
	}
	
	/**
	 * 功能说明：根据机构ID查询用户信息
	 * @author jacobliang
	 * @param organId
	 * @return
	 * @time Jan 26, 2011 11:04:24 AM
	 */
	public List<UumUser> findUumUserByOrganId(long organId){
		return userDao.findUumUserByOrganId(organId);
	}

	/**
	 * 功能说明：根据指定的用户ID集合查询用户信息
	 * @author jacobliang
	 * @param userIds
	 * @return
	 * @time Feb 14, 2012 4:35:35 PM
	 */
	public List findUumUserByUserIds(String userIds){
		if(isNullOrEmpty(userIds))return null;
		return this.userDao.findUumUserByUserIds(userIds);
	}
	
	/**
	 * 功能说明：根据用户名模糊用户信息
	 * @author jacobliang
	 * @param userName
	 * @return
	 * @time Feb 14, 2012 4:38:16 PM
	 */
	public List<UumUser> findAllUumUserByUserName(String userName){
		return this.userDao.findAllUumUserByUserName(userName);
	}
	
	/**
	 * 功能说明：根据拼音代码查询用户信息
	 * @author jacobliang
	 * @param userNamePy
	 * @return
	 * @time Feb 20, 2012 1:47:34 PM
	 */
	public List findAllUumUserByUserNamePy(String userNamePy,long userId){
		return this.userDao.findAllUumUserByUserNamePy(userNamePy,userId);
	}
	
	public void setRoleOrganService(RoleOrganService roleOrganService) {
		this.roleOrganService = roleOrganService;
	}

	public void setOrganService(OrganService organService) {
		this.organService = organService;
	}


	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}


	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public void setUserAgentService(UserAgentService userAgentService) {
		this.userAgentService = userAgentService;
	}

	public void setUserCommFunService(UserCommFunService userCommFunService) {
		this.userCommFunService = userCommFunService;
	}
}
