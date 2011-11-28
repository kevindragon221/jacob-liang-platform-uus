/**
 * 基本常量
 * @author lianglp
 * @version 1.0
 */
var basicConstant = {
	BASIC_PACKAGE_NAME : 'com.bhtec.view.basic',
	VIEW_PORT : 'com.bhtec.view.viewport',//viewport包名
	WIN_HEIGHT : window.screen.height  * 0.6,//窗口高度
	WIN_WIDTH :  window.screen.width  * 0.7,//窗口宽度
	DEBUG_FLAG : true,//debug标志	
	MAIN_PAGE : 'loginActionForword!loginActionForword.action',//主页面
	LIMIT:20,//页数
	BODY_WIDTH:Ext.getBody().getWidth(),//窗体宽
	BODY_HEIGHT:Ext.getBody().getHeight(),//窗体高
	CMP_HEIGHT:20,
	CMP_WIDTH:200,
	FORM_ID:'form_id',
	TREE_HEIGHT:Ext.getBody().getHeight()*0.53,
	TREE_WIDTH:Ext.getBody().getWidth()*0.3,
	GRID_COL_WIDTH:80,
	GENERAL	: 'general',//普通類型
	WINXP:'winxp',//windows,
	LEFT_MENU_ID:'leftMenuId',//左侧菜单
	MAIN_PAGE_ID:'mainPagePanelId',//主页菜单
	PAGE_BACKGROUND:'background-image:url(./img/bht.png)',//背景图片
	FRAME_VIEW_PORT_ID:'frameViewPortId',
	ORGAN_ROOT:'总部',
	ACCESSORY_IMG_PATH:'./uploadFile/img/',
	ADD_OPT_LINK:'add',
	MODIFY_OPT_LINK:'modify',
	DELETE_OPT_LINK:'delete',
	VIEW_OPT_LINK:'view',
	ENABLE_OPT_LINK:'enable',
	DISABLE_OPT_LINK:'disable',
	OPTASSIGN_OPT_LINK:'optassign',
	OPTPRI_OPT_LINK:'optpri',
	ROW_PRIVILEGE:'rowprivilege',
	PLTM:'平台管理->',
	UUM:'统一用户->',
	BHT_COOKIE_HEADER:'bht_cookie_header',
	BHT_COOKIE_NAVIGATE:'bht_cookie_navigate',
	BHT_COOKIE_OUTLOOK:'bht_cookie_outlook',
	BHT_COOKIE_MAINPAGE:'bht_cookie_mainpage',
	BHT_COOKIE_STATEBAR:'bht_cookie_statebar'
}
//---------------------模块常量-------------------------------------------
/**
 * 主页面功能区
 * @type 
 */
var mainFrameFunConstant = {
	MODULE_ID : 'mainFrameFunId',
	XML_FILE : "./js/com/bhtec/view/business/platform/mainframefun/MainFrameFun.xml"
}
/**
 * 页面布局
 * @type 
 */
var pageLayoutConstant = {
	XML_FILE : "./js/com/bhtec/view/business/commonused/userpagelayout/UserPageLayout.xml"
}
/**
 * 模块管理
 * @type 
 */
var moduleConstant = {
	MODULE_ID : 'moduleId',
	XML_FILE : "js/com/bhtec/view/business/platform/module/Module.xml"
}

/**
 * 系统公告
 * @type 
 */
var afficheConstant = {
	MODULE_ID : 'afficheId',
	XML_FILE : "./js/com/bhtec/view/business/platform/affiche/Affiche.xml"
}

/**
 * 模块操作管理
 * @type 
 */
var moduleOptConstant = {
	MODULE_ID : 'opButtonId',
	XML_FILE : "./js/com/bhtec/view/business/platform/moduleopt/ModuleOpt.xml"
}

/**
 * 系统配置管理
 * @type 
 */
var systemConfigConstant = {
	MODULE_ID : 'sysConfigId',
	XML_FILE : "./js/com/bhtec/view/business/platform/systemconfig/SystemConfig.xml"
}

/**
 * 系统参数管理
 * @type 
 */
var systemParaConstant = {
	MODULE_ID : 'systemParaMgr',
	XML_FILE : "./js/com/bhtec/view/business/platform/systemparameter/SystemParameter.xml"
}
/**
 * 类别字典管理
 * @type 
 */
var typeDictionaryConstant = {
	MODULE_ID : 'typeDicMgr',
	XML_FILE : "./js/com/bhtec/view/business/platform/typedictionary/TypeDictionary.xml"
}
/**
 * 省市地区管理
 * @type 
 */
var districtConstant = {
	MODULE_ID : 'priCityMgr',
	XML_FILE : "./js/com/bhtec/view/business/platform/district/District.xml"
}
/**
 * 日志管理
 * @type 
 */
var loggerConstant = {
	MODULE_ID : 'sysLogMgr',
	XML_FILE : "./js/com/bhtec/view/business/platform/logger/Logger.xml"
}
/**
 * 编码管理
 * @type 
 */
var codeConstant = {
	MODULE_ID : 'codMgr',
	XML_FILE : "./js/com/bhtec/view/business/platform/code/Code.xml"
}
/**
 * 调度管理
 * @type 
 */
var schedulerConstant = {
	MODULE_ID : 'schedulerId',
	XML_FILE : "./js/com/bhtec/view/business/platform/scheduler/Scheduler.xml"
}
//--------------------------------统一用户---------------------------------------------------------
/**
 * 机构管理
 * @type 
 */
var organConstant = {
	MODULE_ID : 'organId',
	XML_FILE : "./js/com/bhtec/view/business/uum/organ/Organ.xml"
}

/**
 * 角色管理
 * @type 
 */
var roleConstant = {
	MODULE_ID : 'roleId',
	XML_FILE : "./js/com/bhtec/view/business/uum/role/Role.xml"
}

/**
 * 角色分配给机构管理
 * @type 
 */
var roleOrganConstant = {
	MODULE_ID : 'roleOrganId',
	XML_FILE : "./js/com/bhtec/view/business/uum/role/RoleOrgan.xml"
}

/**
 * 用户管理
 * @type 
 */
var userConstant = {
	MODULE_ID : 'userId',
	XML_FILE : "./js/com/bhtec/view/business/uum/user/User.xml"
}

/**
 * 修改用户信息
 * @type 
 */
var userInfoModify = {
	XML_FILE : "./js/com/bhtec/view/business/commonused/userinfomodify/UserInfoModify.xml"
}
/**
 * 用户组信息
 * @type 
 */
var userGroupConstant = {
	MODULE_ID : 'usergroupmgr',
	XML_FILE : "./js/com/bhtec/view/business/uum/group/userGroup/UserGroup.xml"
}
//角色组
var roleGroupConstant = {
	MODULE_ID : 'rolegroupmgr',
	XML_FILE : "./js/com/bhtec/view/business/uum/group/roleGroup/RoleGroup.xml"
}
//普通组
var generalGroupConstant = {
	MODULE_ID : 'generalgroupmgr',
	XML_FILE : "./js/com/bhtec/view/business/uum/group/generalGroup/GeneralGroup.xml"
}
/**
 * 角色切换JS路径
 * @type 
 */
var jsfile = {
	ROLE_CHANGE : "./js/com/bhtec/view/business/uum/role/RoleChange.js",
	COMMON_FUNCTION :"./js/com/bhtec/view/business/commonused/commonfuntion/UserCommonFunction.js"
}