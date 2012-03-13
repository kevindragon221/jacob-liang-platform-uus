/**
 *功能说明：
 * @author jacobliang
 * @time @Aug 8, 2010 @11:04:05 AM
 */
package com.bhtec.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Common {
	//web.xml web根目录名称
	public final static String WEB_APP_ROOT_KEY = "bhtec.root";
	//记录总数
	public final static String TOTAL_PROPERTY = "totalProperty";
	//记录list
	public final static String BUSI_LIST = "busiList";
	//一级菜单
	public final static String FIRST = "first";
	//二级菜单
	public final static String SECOND = "second";
	//三级菜单
	public final static String THIRD = "third";
	//四级菜单操作
	public final static String FOURTH = "fourth";
	//机构名称 ID
	public final static String ORGAN_NAME = "organName";
	public final static String ORGAN_ID = "organId";
	//角色名称 ID
	public final static String ROLE_NAME = "roleName";
	public final static String ROLE_ID = "roleId";
	public final static String ORGAN_ROLE_ID = "organRoleId";
	//用户名称 ID
	public final static String USER_NAME = "userName";
	public final static String USER_CODE = "userCode";
	public final static String USER_ID = "userId";
	public final static String PASSWORD = "password";
	public final static String AGENT_ID = "agentId";
	//多角色列表
	public final static String MULTI_ROLE_LIST = "multiRoleList";
	
	//日志功能设置
	public final static String LOG_LEVEL_WITHOD = "0";
	public final static String LOG_LEVEL_FIRST = "1";
	public final static String LOG_LEVEL_SECOND = "2";
	public final static String LOG_LEVEL_THIRD = "3";
	public final static String SAVE_OPT = "保存";
	public final static String MODIFY_OPT = "修改";
	public final static String DELETE_OPT = "删除";
	public final static String USER_LOGIN = "用户登录";
	public final static String SPLIT = ";";
	public final static String ENABLE = "enable";
	public final static String DISABLE = "disable";
	//角色切换类型
	public final static String LOGIN = "login";
	public final static String IN_SYSTEM = "insystem";
	//切换角色标志
	public final static String CHANGE_ROLE_FLAG = "changeRoleFlag";
	public final static String PRIVILEGE_TYPE_ROW = "row";
	public final static String PRIVILEGE_TYPE_OPT = "opt";
	public final static String PRIVILEGE_SCOPE_ORG = "org";
	public final static String PRIVILEGE_SCOPE_ROL = "rol";
	public final static String PRIVILEGE_SCOPE_USR = "usr";
	public final static String PRIVILEGE_SCOPE_INC = "inc";
	public final static String PRIVILEGE_SCOPE_EXC = "exc";
	public final static String PRIVILEGE_SCOPE_ALL = "all";
	public final static String PRIVILEGE_SCOPE = "privilegeScope";
	public final static String OWNER_TYPE_ROL = "rol";
	public final static String OWNER_TYPE_USR = "usr";
	public final static String USER_DEFAULT_PWD = "userdefaultpassword";
	public final static String USER_VALIDATE = "uservalidate";
	public final static String[] PRIVILEGE_TYPE_ARR = {PRIVILEGE_TYPE_ROW,PRIVILEGE_TYPE_OPT};
	public final static String[] OWNER_TYPE_ARR = {OWNER_TYPE_USR,OWNER_TYPE_ROL};
//	public final static Map PRIVILEGE_MAP = new HashMap(){{
//								put(PRIVILEGE_TYPE_ROW,new ArrayList());
//								put(PRIVILEGE_TYPE_OPT,new ArrayList());
//								}};
	//组相关信息
	public final static String USER_GROUP = "userGroup";//用户组
	public final static String ROLE_GROUP = "roleGroup";//角色组
	public final static String GENERAL_GROUP = "generalGroup";//普通组
	public final static String USER = "user";//用户类型
	public final static String ROLE = "role";//用户类型
	//创建系统文件路径
	public final static String BHT_SYS_FILE_DIR = "bhtsys_file_dir";
	//系统配置文件
	public final static String SYS_CONFIGFILE_XML = "SystemConfig.xml";
	public final static String TOMCAT = "tomcat";
	public final static String WEBSPHERE = "websphere";
	public final static String WEBLOGIC = "weblogic";
	public final static String AGENT_USER = "agentUser";
	public final static String ROLE_LIST = "roleList";
	public final static String COMMON_FUN = "commonFun";//常用功能
	public final static String AGENT_LOGIN = "agentLogin";//代理登录
	public final static String NORMAL_LOGIN = "normalLogin";//正常登录
}
