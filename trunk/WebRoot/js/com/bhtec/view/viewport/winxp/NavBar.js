/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtNavBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.NavBar = function(config) {
	/**
	 * 常用功能菜单
	 */
	var commFunMenu = function(){
		var commFunArr = new Array();
		for(i=0;i<assignUumUserCommfunList.length;i++){
			var modEnIdThi = 'commfun_'+assignUumUserCommfunList[i].modEnId;
			var modNameThi = assignUumUserCommfunList[i].modName;
			var modImgClsThi = assignUumUserCommfunList[i].modImgCls;
			var belongToSys = assignUumUserCommfunList[i].belongToSys;
			var modPageType = assignUumUserCommfunList[i].modPageType;
			var clickMenu = function(item){
				var menuFlag = item.id.indexOf('_');
				var menuEnId = item.id.substr(menuFlag + 1);
				var configMenu = {
					id:menuEnId,
					iconCls:item.iconCls,
					text:item.text,
					belongToSys:item.belongToSys,
					modPageType:item.modPageType
				};
				//标签面板
				var tab = getExtCmpById(menuEnId+'_tab');
				if(tab){
					//主页面板
					var mainPagePanel = getExtCmpById('mainPagePanelId');
					mainPagePanel.setActiveTab(tab);
				}else{
					var memuControl = new com.bhtec.control.MenuControl(configMenu);
					memuControl.addTab();
				}
			}
			commFunArr.push({id:modEnIdThi,clickHideDelay:0,
							 text:modNameThi,iconCls:modImgClsThi,
							 handler:clickMenu,belongToSys:belongToSys,
							 modPageType:modPageType});
		}
		return commFunArr;
		//--------常用功能结束-----------------------------
	}
	
    /**
     * 系统导航栏菜单，系统菜单为动态
     */
    var sysFirstMenu = function(){
    	var firstMenuArr = new Array();
    	var commFunModulePanel = commFunMenu();//常用功能
    	firstMenuArr.push({id:'userCommFunMenuId',text:'常用功能',iconCls:'usedfuncmaint',menu:{items:commFunModulePanel}});
    	firstMenuArr.push('-');
    	
    	var firstMenuList = firstMenu;//一级菜单Map
    	if(firstMenuList && firstMenuList != ''){
			for(i=0;i<firstMenuList.length;i++){
				var moduleIdFir = firstMenuList[i].moduleId;
				var modNameFir = firstMenuList[i].modName;
				var modImgClsFir = firstMenuList[i].modImgCls;
				var sysSecMenu = new Array();
				var secondList = secondMenu[moduleIdFir];//map取值,
		    	if(secondList && secondList != ''){//二级菜单
					for(l=0;l<secondList.length;l++){
						var moduleIdSec = secondList[l].moduleId;
						var modNameSec = secondList[l].modName;
						var modImgClsSec = secondList[l].modImgCls;
						var thirdList = thirdMap[moduleIdSec];//map取值,
						var sysThiMenu = new Array();
						if(thirdList && thirdList != ''){					
							for(j=0;j<thirdList.length;j++){//三级菜单
								var modEnIdThi = thirdList[j].modEnId;
								var modNameThi = thirdList[j].modName;
								var modImgClsThi = thirdList[j].modImgCls;
								var belongToSys = thirdList[j].belongToSys;
								var modPageType = thirdList[j].modPageType;
								var clickMenu = function(item){
									var configMenu = {
										id:item.id,
										iconCls:item.iconCls,
										text:item.text,
										belongToSys:item.belongToSys,
										modPageType:item.modPageType
									};
									//标签面板
									var tab = getExtCmpById(item.id+'_tab');
									if(tab){
										//主页面板
										var mainPagePanel = getExtCmpById('mainPagePanelId');
										mainPagePanel.setActiveTab(tab);
									}else{
										var memuControl = new com.bhtec.control.MenuControl(configMenu);
										memuControl.addTab();
									}
								}
								//clickHideDelay:0,马上隐藏点击事件
								sysThiMenu.push({id:modEnIdThi,clickHideDelay:0,
												 text:modNameThi,iconCls:modImgClsThi,
												 handler:clickMenu,belongToSys:belongToSys,
												 modPageType:modPageType});
							}
						}
						var sysThiMenuTemp = '';
						if(sysThiMenu.length>0)
							sysThiMenuTemp = {items:sysThiMenu};
						sysSecMenu.push({text:modNameSec,iconCls:modImgClsSec,menu:sysThiMenuTemp});
					}
					
		    	}
		    	var sysSecMenuTemp = '';
				if(sysSecMenu.length>0)
					sysSecMenuTemp = {items:sysSecMenu};
				firstMenuArr.push({text:modNameFir,iconCls:modImgClsFir,menu:sysSecMenuTemp});
				firstMenuArr.push('-');
			}
    	}
    	firstMenuArr.push('->');
    	firstMenuArr.push({text:'帮助',tooltip:'帮助(F1)',iconCls:'help',handler:function(){shortcutKey('F1')}},'-');
    	firstMenuArr.push({text:'WebQQ',tooltip:'WebQQ(F10)',iconCls:'qq',handler:function(){shortcutKey('F10')}},'-');
    	firstMenuArr.push({text:'常用',iconCls:'usedfunc',menu:usedFunMenu()},'-');
    	firstMenuArr.push({text:'皮肤',iconCls:'change_skin',menu:changeSkinMenu()},'-');
    	firstMenuArr.push({text:'注销',tooltip:'注销(F6)',iconCls:'logout',handler:function(){shortcutKey('F6')}},'-');
    	firstMenuArr.push({text:'退出',tooltip:'退出(F7)',iconCls:'close',handler:function(){shortcutKey('F7')}},'-');
    	
    	return firstMenuArr;
    }
    /**
     * 导航面板
     */
	var navBarPanel = {
		xtype : 'toolbar',
		items:sysFirstMenu(),
    	commFunModulePanel:commFunMenu//常用功能菜单同步
	}	
	return navBarPanel;
}