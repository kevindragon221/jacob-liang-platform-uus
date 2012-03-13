/**
 * 多套页面viewport
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.ViewportFactory
 */
//---------------- 全局变量-------------------------
var firstMenu;//一级菜单list
var secondMenu;//二级菜单map
var thirdMap;//三级菜单map
var fourthModOpt;//四级操作按钮map
var assignUumUserCommfunList;//常用功能列表
//---------------- 全局变量-------------------------
Ext.namespace('com.bhtec.view');
com.bhtec.view.ViewportFactory = function(config) {
	//---------------- 全局变量-------------------------
	firstMenu = config.firstList||'';
	secondMenu = config.secondMap||'';
	thirdMap = config.thirdMap||'';
	fourthModOpt = config.fourthMap||'';
	assignUumUserCommfunList = config.assignUumUserCommfunList||[];//当前用户分配的常用功能
	//---------------- 全局变量-------------------------
	
	var firstMenuSysId = firstMenu != ''?firstMenu[0].moduleId:'';
	var firstMenuSysName = firstMenu != ''?firstMenu[0].modName:'';
	
	var northMainFrame  = '',navMainFrame='',centerMainFrame = '',westMainFrame   = '',southMainFrame  = '';
	//动态加载页面框架js和构造实例
	if(basicConstant.WINXP == navigateMainFrame){
		navMainFrame = new com.bhtec.view.viewport.winxp.NavBar();
	}else if(basicConstant.GENERAL == navigateMainFrame){
		navMainFrame = new com.bhtec.view.viewport.general.NavBar();
	}
	if(basicConstant.WINXP == headerMainFrame){
		northMainFrame = new com.bhtec.view.viewport.winxp.Header({
			navigate:navMainFrame
		});
	}else if(basicConstant.GENERAL == headerMainFrame){
		northMainFrame = new com.bhtec.view.viewport.general.Header({
			navigate:navMainFrame
		});
	}
	
	if(basicConstant.WINXP == mainPageMainFrame){
		centerMainFrame = new com.bhtec.view.viewport.winxp.MainPage();
		
	}else if(basicConstant.GENERAL == mainPageMainFrame){
		centerMainFrame = new com.bhtec.view.viewport.general.MainPage();
	}
	//如果headerMainFrame = winxp,则不显示outlook
	if(basicConstant.WINXP != navigateMainFrame){
		if(basicConstant.WINXP == outlookBarMainFrame){
			westMainFrame = new com.bhtec.view.viewport.winxp.OutlookBar({
				sysMenuName:firstMenuSysName,
				sysMenuId:firstMenuSysId
			});
		}else if(basicConstant.GENERAL == outlookBarMainFrame){
			westMainFrame = new com.bhtec.view.viewport.general.OutlookBar({
				sysMenuName:firstMenuSysName,
				sysMenuId:firstMenuSysId
			});
		}
	}
	
	if(basicConstant.WINXP == stateBarMainFrame){
		southMainFrame = new com.bhtec.view.viewport.winxp.StateBar();
	}else if(basicConstant.GENERAL == stateBarMainFrame){
		southMainFrame = new com.bhtec.view.viewport.general.StateBar();
	}
	
	var viewPortArr = new Array();
	viewPortArr.push(northMainFrame);
	viewPortArr.push(centerMainFrame);
	westMainFrame==''?'':viewPortArr.push(westMainFrame);
	viewPortArr.push(southMainFrame);
	var viewPort = new Ext.Viewport({
			id:basicConstant.FRAME_VIEW_PORT_ID,
			layout : 'border',
			border:false,
			items :viewPortArr
	})
	return viewPort;
}

