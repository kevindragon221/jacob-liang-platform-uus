/**
 *功能说明：
 * @author jacobliang
 * @time @Sep 23, 2010 @3:33:09 PM
 */
package com.bhtec.action.uum.user;

import static com.bhtec.common.constant.Common.BUSI_LIST;
import static com.bhtec.common.constant.Common.OWNER_TYPE_USR;
import static com.bhtec.common.constant.Common.PRIVILEGE_SCOPE;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_OPT;
import static com.bhtec.common.constant.Common.PRIVILEGE_TYPE_ROW;
import static com.bhtec.common.constant.Common.TOTAL_PROPERTY;
import static com.bhtec.common.constant.Common.USER;
import static com.bhtec.common.constant.Common.*;
import static com.bhtec.common.constant.Common.USER_DEFAULT_PWD;
import static com.bhtec.common.constant.Common.USER_VALIDATE;
import static com.bhtec.common.constant.ServiceVariable.ASSIGNED_MODOPTS;
import static com.bhtec.common.constant.ServiceVariable.UNASSIGNED_MODOPTS;
import static com.bhtec.common.tools.UtilTools.getFirstLetters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.bhtec.action.uum.UumBaseAction;
import com.bhtec.common.tools.UtilTools;
import com.bhtec.domain.pojo.platform.SysplModuleMemu;
import com.bhtec.domain.pojo.uum.UumOrgan;
import com.bhtec.domain.pojo.uum.UumRoleUserRef;
import com.bhtec.domain.pojo.uum.UumUser;
import com.bhtec.domain.pojohelper.tree.TreeVo;
import com.bhtec.exception.ApplicationException;
import com.bhtec.exception.SystemException;
import com.bhtec.service.iface.platform.PlatformCommonService;
import com.bhtec.service.iface.uum.privilege.PrivilegeService;
import com.bhtec.service.iface.uum.role.RoleUserService;
import com.bhtec.service.iface.uum.user.UserService;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends UumBaseAction  implements ModelDriven<UumUser> {
	private static final long serialVersionUID = 1000000L;
	Logger log = Logger.getLogger(this.getClass());
	private UumUser uumUser = new UumUser();
	private List<UumUser> userList;
	private List<UumOrgan> organList;
	private List<UumRoleUserRef> roleUserRefList;
	private boolean existUser;
	private int count;
	private String failMesg;
	private String disEnableFlag;
	private boolean disEnableBol;
	private UserService userService;
	private RoleUserService roleUserService;
	private String orgOrRoleId;
	private String orgRoleIds;
	private long defaultOrgRoleId;
	
	private PrivilegeService privilegeService;
	private List<SysplModuleMemu> assignedModMenuList;
	private List<SysplModuleMemu> unassignedModMenuList;
	private List<String> modOptIdList;
	private PlatformCommonService platformCommonService;
	private String userDefaultPwd;
	private Date userValidate;
	
	//系统管理员列表信息
	private List<UumUser> systemAdminCodeList;
	private int systemAdminCount;
	private String personalPhoto = "uploadFile"+File.separator+"img";
	private String privilegeScope;
	/**
	 * private File fileUpload 中fileUpload是与FileUpload.js中‘选择文件’
	 * 的id属性相对应的，如果需要修改必须同步进行
	 * 
	 * private String fileUploadFileName（*FileName）
	 * 前缀*必须与File 定义变量相一致
	 * 如果File变量有改动，其也随之修改
	 */
	private File fileUpload;
	private String fileUploadFileName;
	/**
	 * 功能说明：保存用户
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 6:03:41 PM
	 */
	public void saveUser(){
		try {
			userService.setHttpServletRequest(this.getHttpServletRequest());
			uumUser.setCreator((String)getHttpServletRequest().getSession().getAttribute(USER_CODE));
			uumUser.setCreateDate(new Date());
			String pothoUrl = "";
			if(fileUpload != null){
				pothoUrl = UtilTools.uploadFile(this.getHttpServletRequest(),this.fileUpload,personalPhoto,this.fileUploadFileName);
			}
			uumUser.setUserPhotoUrl(pothoUrl);
			uumUser.setUserNamePy(getFirstLetters(uumUser.getUserName()));
			userService.saveUser(uumUser);
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
	 * 功能说明：查询所有用户
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 6:47:32 PM
	 */
	public String findAllUser(){
		try {
			userList = this.userService.findAllUser();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("findAllUser() occur error. ", e);
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：查询所有模块信息
	 * @author jacobliang
	 * @throws 
	 * @time Sep 23, 2010 
	 */
	public String findUserByCon(){
		Map map = userService.findUserByCon(getStart(),getLimit(),orgOrRoleId,uumUser.getUserName(), this.uumUser.getUserCode());
		userList = (List<UumUser>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		return SUCCESS;
	}
	/**
	 * 功能说明：查询用户信息为管理员分配
	 * @author jacobliang
	 * @return
	 * @time Nov 23, 2010 8:27:57 AM
	 */
	public String findUserForAdminSet(){
		Map map = userService.findUserInfoForAdminSet(getStart(),getLimit(),uumUser.getUserName(), this.uumUser.getUserCode());
		userList = (List<UumUser>)map.get(BUSI_LIST);
		count = (Integer)map.get(TOTAL_PROPERTY);
		systemAdminCodeList = (List<UumUser>)map.get("systemAdminCodeList");
		systemAdminCount = (Integer)map.get("systemAdminCount");
		return SUCCESS;
	}
	
	/**
	 * 功能说明：查找指定节点的下一级子节点
	 * @author jacobliang
	 * @param	UserId	用户id
	 * @return
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public void findNextLevelChildNodes(){
		String isCheckbox = this.getHttpServletRequest().getParameter("isCheckbox");
		List<TreeVo> asyncTreeList = userService.findNextLevelChildNodes(
				getModViewRecId()==null?"0_1":getModViewRecId(),isCheckbox);
		JSONArray jsonArray = JSONArray.fromObject(asyncTreeList);   
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能说明：根据用户ID查询某个用户
	 * @author jacobliang
	 * @param UserId
	 * @throws 
	 * @time Sep 23, 2010 3:11:23 PM
	 */
	public String findUserByUserId(){
		try {
			uumUser = userService.findUserByUserId(new Long(getModViewRecId()));
			uumUser.setUserPassword(UtilTools.decrypt(uumUser.getUserPassword()));
			String newPhotoUrl = UtilTools.getClusterResourcePath("")+personalPhoto
								+File.separator+uumUser.getUserPhotoUrl();
			uumUser.setUserPhotoUrl(newPhotoUrl);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("getModViewRecId() is not number. ", e);
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：查询用户名称是否重复
	 * @author jacobliang
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 11:11:39 AM
	 */
	public String findUserByUserCode(){
		try {
			existUser = userService.findUserByUserCode(this.getHttpServletRequest().getParameter("userCodee"));
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：修改用户
	 * @author jacobliang
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Jul 27, 2010 9:43:55 AM
	 */
	public void modifyUser(){
		try {
			userService.setHttpServletRequest(this.getHttpServletRequest());
			if(fileUpload != null){
				String photoUrl = UtilTools.unuploadFileForUpdate(this.getHttpServletRequest(), this.fileUpload, 
						personalPhoto,this.fileUploadFileName, uumUser.getUserPhotoUrl());
				uumUser.setUserPhotoUrl(photoUrl);
			}
			uumUser.setUserNamePy(getFirstLetters(uumUser.getUserName()));
			userService.modifyUser(uumUser);
			this.returnSuccess();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error("modifyUser() is not number. ", e);
			this.returnFailur(this.ERROR);
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		} catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	
	/**
	 * 功能说明：停用启用用户
	 * @author jacobliang
	 * @param  disEnableFlag	停用启用标志
	 * @param
	 * @param moduleId
	 * @return
	 * @throws 
	 * @time Sep 22, 2010 1:29:34 PM
	 */
	public String disEnableUser(){
		try {
			userService.setHttpServletRequest(this.getHttpServletRequest());
			disEnableBol = userService.disEnableUser(new Long(getModViewRecId()), disEnableFlag);
		} catch (NumberFormatException e) {
			e.printStackTrace();		
			log.error("disEnableUser() is not number. ", e);
			failMesg = this.ERROR;
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：删除照片
	 * @author jacobliang
	 * @return
	 * @time Oct 20, 2010 11:01:26 AM
	 */
	public String deletePhotoFile(){
		try{
			//删除本地图片
			UtilTools.deleteFile(getHttpServletRequest(),personalPhoto+uumUser.getUserPhotoUrl());
			//修改照片URL为空
			if(!"".equals(uumUser.getUserId()) && uumUser.getUserId() != null)
				userService.modifyUserPhotoUrlToEmpty(uumUser.getUserId());
		}catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 功能说明：根据userId查询用户的所有角色
	 * @author jacobliang
	 * @return
	 * @time Oct 24, 2010 5:38:40 PM
	 */
	public String findRoleUserListByUserId() {
		roleUserRefList = roleUserService.findRoleUserListByUserId(uumUser.getUserId());
		return SUCCESS;
	}
	/**
	 * 功能说明：保存为用户分配的角色
	 * @author jacobliang
	 * @return
	 * @time Oct 25, 2010 7:25:13 PM
	 */
	public String saveRoleUser(){
		try {
			roleUserService.setHttpServletRequest(this.getHttpServletRequest());
			roleUserService.saveRoleUser(uumUser.getUserId(), orgRoleIds,
					defaultOrgRoleId);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：角色分配操作权限
	 * @author jacobliang
	 * @return
	 * @time Nov 1, 2010 4:28:48 PM
	 */
	public String userAssignOptPrivilege(){
		Map moduleMemuMap = 
			privilegeService.findSeledAUnseledModOptByOwnIdAPriType(uumUser.getUserId(), OWNER_TYPE_USR);
		assignedModMenuList = (List<SysplModuleMemu>)moduleMemuMap.get(ASSIGNED_MODOPTS);
		unassignedModMenuList = (List<SysplModuleMemu>)moduleMemuMap.get(UNASSIGNED_MODOPTS);
		privilegeScope = (String)moduleMemuMap.get(PRIVILEGE_SCOPE);
		return SUCCESS;
	}
	/**
	 * 功能说明：保存为角色分配的模块操作权限
	 * @author jacobliang
	 * @return
	 * @time Nov 2, 2010 2:22:23 PM
	 */
	public String saveUserOptPrivilege(){
		try {
			privilegeService.setHttpServletRequest(this.getHttpServletRequest());
			privilegeService.savePrivilege(modOptIdList, uumUser.getUserId(), 
					PRIVILEGE_TYPE_OPT,OWNER_TYPE_USR,privilegeScope);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 功能说明：根据用户名模糊查询
	 * @author jacobliang
	 * @return
	 * @time Feb 15, 2012 9:30:58 AM
	 */
	public String findUumUserByUserName(){
		userList = privilegeService.findAllUumUserByUserName(uumUser.getUserName());
		return SUCCESS;
	}
	
	/**
	 * 功能说明：根据用户ID 已分配行权限
	 * @author jacobliang
	 * @return
	 * @time Feb 15, 2012 11:47:19 AM
	 */
	public String findSeledRowPrivilegeByUserId(){
		Map map = privilegeService.findSeledRowPrivilegeByUserId(uumUser.getUserId());
		userList = (List)map.get(USER);
		privilegeScope = (String)map.get(PRIVILEGE_SCOPE);
		return SUCCESS;
	}
	
	/**
	 * 功能说明：保存为用户分配的行权限
	 * @author jacobliang
	 * @return
	 * @time Nov 2, 2010 2:22:23 PM
	 */
	public String saveUserRowPrivilege(){
		try {
			privilegeService.setHttpServletRequest(this.getHttpServletRequest());
			privilegeService.savePrivilege(modOptIdList, uumUser.getUserId(), 
					PRIVILEGE_TYPE_ROW,OWNER_TYPE_USR,privilegeScope);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：重置用户密码
	 * @author jacobliang
	 * @return
	 * @time Nov 7, 2010 6:36:20 PM
	 */
	public String resetPwd(){
		try {
			userService.setHttpServletRequest(this.getHttpServletRequest());
			//系统参数用户默认密码
			String userDefaultPwd = platformCommonService.findSystemParaByParaName(USER_DEFAULT_PWD);
			userService.resetPwdByUserId(uumUser.getUserId(),uumUser.getUserName(),userDefaultPwd);
		} catch (ApplicationException e) {
			e.printStackTrace();
			failMesg = e.toString();
		}catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failMesg = this.ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 功能说明：根据用户ID修改用户号 用户名 用户密码 
	 * @author jacobliang
	 * @throws ApplicationException
	 * @time Nov 18, 2010 5:29:52 PM
	 */
	public void modifyUserInfo(){
		try {
			userService.setHttpServletRequest(this.getHttpServletRequest());
			this.userService.modifyUserInfo(uumUser);
			this.returnSuccess();
		} catch (ApplicationException e) {
			e.printStackTrace();
			this.returnFailur(e.toString());
		} catch (SystemException e) {
			e.printStackTrace();
			this.returnFailur(this.ERROR);
		}
	}
	/**
	 * 功能说明：获得用户的默认密码和有效期
	 * @author jacobliang
	 * @return
	 * @time Dec 11, 2010 12:07:35 PM
	 */
	public String obtainUserDefPwdAValidate(){
		userDefaultPwd = platformCommonService.findSystemParaByParaName(USER_DEFAULT_PWD);
		int validate = Integer.parseInt(platformCommonService.findSystemParaByParaName(USER_VALIDATE)
						.equals("")?"0":platformCommonService.findSystemParaByParaName(USER_VALIDATE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONDAY, validate);
		userValidate = cal.getTime();
		return SUCCESS;
	}
	
	/**
	 * 功能说明：根据拼音代码查询用户信息
	 * @author jacobliang
	 * @param userNamePy
	 * @return
	 * @time Feb 20, 2012 1:47:34 PM
	 */
	public String findAllUumUserByUserNamePy(){
		long userId = (Long)getHttpServletRequest().getSession().getAttribute(USER_ID);
		List list = userService.findAllUumUserByUserNamePy(uumUser.getUserNamePy(),userId);
		userList = new ArrayList<UumUser>();
		if(list != null){
			for(int i=0,l=list.size();i<l;i++){
				UumUser uumUser = new UumUser();
				Object[] obj = (Object[])list.get(i);
				uumUser.setUserId(Long.parseLong(obj[0].toString()));
				uumUser.setUserName(obj[1].toString());
				userList.add(uumUser);
			}
		}
		return SUCCESS;
	}
	
	public UumUser getModel() {
		return uumUser;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UumUser getUumUser() {
		return uumUser;
	}

	public void setUumUser(UumUser uumUser) {
		this.uumUser = uumUser;
	}

	public List<UumUser> getUserList() {
		return userList;
	}

	public void setUserList(List<UumUser> userList) {
		this.userList = userList;
	}

	public boolean getExistUser() {
		return existUser;
	}

	public void setExistUser(boolean existUser) {
		this.existUser = existUser;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFailMesg() {
		return failMesg;
	}

	public void setFailMesg(String failMesg) {
		this.failMesg = failMesg;
	}

	public String getDisEnableFlag() {
		return disEnableFlag;
	}

	public void setDisEnableFlag(String disEnableFlag) {
		this.disEnableFlag = disEnableFlag;
	}

	public boolean isDisEnableBol() {
		return disEnableBol;
	}

	public void setDisEnableBol(boolean disEnableBol) {
		this.disEnableBol = disEnableBol;
	}

	public List<UumOrgan> getOrganList() {
		return organList;
	}

	public void setOrganList(List<UumOrgan> organList) {
		this.organList = organList;
	}

	public String getOrgOrRoleId() {
		return orgOrRoleId;
	}

	public void setOrgOrRoleId(String orgOrRoleId) {
		this.orgOrRoleId = orgOrRoleId;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public void setRoleUserService(RoleUserService roleUserService) {
		this.roleUserService = roleUserService;
	}

	public List<UumRoleUserRef> getRoleUserRefList() {
		return roleUserRefList;
	}

	public void setRoleUserRefList(List<UumRoleUserRef> roleUserRefList) {
		this.roleUserRefList = roleUserRefList;
	}

	public String getOrgRoleIds() {
		return orgRoleIds;
	}

	public void setOrgRoleIds(String orgRoleIds) {
		this.orgRoleIds = orgRoleIds;
	}

	public long getDefaultOrgRoleId() {
		return defaultOrgRoleId;
	}

	public void setDefaultOrgRoleId(long defaultOrgRoleId) {
		this.defaultOrgRoleId = defaultOrgRoleId;
	}

	public List<SysplModuleMemu> getAssignedModMenuList() {
		return assignedModMenuList;
	}

	public void setAssignedModMenuList(List<SysplModuleMemu> assignedModMenuList) {
		this.assignedModMenuList = assignedModMenuList;
	}

	public List<SysplModuleMemu> getUnassignedModMenuList() {
		return unassignedModMenuList;
	}

	public void setUnassignedModMenuList(List<SysplModuleMemu> unassignedModMenuList) {
		this.unassignedModMenuList = unassignedModMenuList;
	}

	public List<String> getModOptIdList() {
		return modOptIdList;
	}

	public void setModOptIdList(List<String> modOptIdList) {
		this.modOptIdList = modOptIdList;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public List<UumUser> getSystemAdminCodeList() {
		return systemAdminCodeList;
	}

	public void setSystemAdminCodeList(List<UumUser> systemAdminCodeList) {
		this.systemAdminCodeList = systemAdminCodeList;
	}

	public int getSystemAdminCount() {
		return systemAdminCount;
	}

	public void setSystemAdminCount(int systemAdminCount) {
		this.systemAdminCount = systemAdminCount;
	}

	public void setPlatformCommonService(PlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}

	public String getUserDefaultPwd() {
		return userDefaultPwd;
	}

	public void setUserDefaultPwd(String userDefaultPwd) {
		this.userDefaultPwd = userDefaultPwd;
	}

	public Date getUserValidate() {
		return userValidate;
	}

	public void setUserValidate(Date userValidate) {
		this.userValidate = userValidate;
	}

	public String getPrivilegeScope() {
		return privilegeScope;
	}

	public void setPrivilegeScope(String privilegeScope) {
		this.privilegeScope = privilegeScope;
	}
}
