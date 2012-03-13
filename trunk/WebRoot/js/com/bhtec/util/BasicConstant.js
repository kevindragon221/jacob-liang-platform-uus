/**
 * 基本常量
 * @author lianglp
 * @version 2.0
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
	BHT_COOKIE_STATEBAR:'bht_cookie_statebar',
	LIST_VIEW:'listView',
	OP_VIEW:'opView',
	DYNAMIC_LOAD_JS_MAP:new Map(),//动态js数组
	DYNAMIC_JS_INSTANCE_MAP:new Map(),//动态实例
	DYNAMIC_LOAD_XMLDOC:new Map(),//xml doc
	jsfile:{
		ROLE_CHANGE : './js/com/bhtec/view/business/uum/role/RoleChange.js'//角色切换JS路径
	},
	JS:'js',//文件类型
	JSP:'jsp',
	HTML:'html',
	FLEX:'flex',
	DEFAULT_THEME:'xtheme-human'
}

