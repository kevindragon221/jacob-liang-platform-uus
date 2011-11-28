/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtNavBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.NavBar = function(config) {
	var cookie = new com.bhtec.util.Cookie();
	var changeSkinOp = function(item){
            Ext.util.CSS.swapStyleSheet('theme', 'ext/resources/css/' + item.id + '.css');
            //保存主题
            cookie.saveCookie(userCode_cookie+'_bht.theme', item.id);
    }
	var changeSkinMenu = new Ext.menu.Menu();
	var themes = com.bhtec.util.Data.themes;
    for (var i = 0; i < themes.length; i++){
    	var theme = themes[i];
    	var themeName = theme[1];
    	var themeValue = theme[2];
        changeSkinMenu.add({
        	id:themeValue,
            text: themeName,
            iconCls:'skinSelected',
            handler: changeSkinOp
        })
    }
    
     /**
     * 从cookie中获取主题
     * 后面的小括号表示立即执行该函数
     */
    var readThemeFromCookie = (function() {
        var themeValue = cookie.getCookie(userCode_cookie+'_bht.theme');
        if (themeValue) {
        	var themeValueId = {
        		id:themeValue
        	}
            changeSkinOp(themeValueId);
        }
    }).defer(0);
    
	 /**
     * 常用功能菜单
     */
    var usedFunMenu = function(){
    	var munuItems = [{
			    	id:'pageLayout',
			        text: '页面布局 (F2)',
			        iconCls:'pagePattern',
			        handler: function(){
			        	var xmlDoc = loadXMLFile(pageLayoutConstant.XML_FILE);//加载模块xml
						loadModuleJs(xmlDoc);//动态加载js
			        	//模块页面
						var pageLayoutVOp = new com.bhtec.view.business.commonused.userpagelayout.UserPageLayout({xmlDoc:xmlDoc});
						pageLayoutVOp.funForm({});
			        }
	    		  },{
			    	id:'modifyUser',
			        text: '修改用户 (F3)',
			        iconCls:'user_edit',
			        handler: function(){
			        	var xmlDoc = loadXMLFile(userInfoModify.XML_FILE);//加载模块xml
						loadModuleJs(xmlDoc);//动态加载js
			        	//模块页面
						var userInfoModifyForm = new com.bhtec.view.business.commonused.userinfomodify.UserInfoModify({xmlDoc:xmlDoc});
						userInfoModifyForm.saveForm();
			        }
	    		  },{
			    	id:'usedFun',
			        text: '常用功能 (F4)',
			        iconCls:'usedfuncmaint',
			        handler: function(){
			        	loadModuleJsByJsPath(jsfile.COMMON_FUNCTION);//动态加载常用功能JS
			        	new com.bhtec.view.business.commonused.commonfuntion.UserCommonFunction();
			        }
	    		  },{
			    	id:'portalMaint',
			        text: 'portal维护 (F5)',
			        iconCls:'portal',
			        handler: ''
	    		  }];
	    if(changeRoleType != 'login'
	    			&& uumRoleUserRefList.length>1){
	    	munuItems.push({
			    	id:'multiRoleChanged',
			        text: '角色切换 (F6)',
			        iconCls:'rolemgr',
			        handler: function(){
			        	loadModuleJsByJsPath(jsfile.ROLE_CHANGE);//动态加载切换角色JS
			        	var roleChange = new com.bhtec.view.business.uum.role.RoleChange({
							        		uumRoleUserRefList:uumRoleUserRefList
							        	});
					    roleChange.roleSelectedWindow();
			        }
	    		  })
	    }
	    var usedMenu = new Ext.menu.Menu({
	    	items:munuItems
	    });
	    return usedMenu;
    }
    /**
     * 退出系统操作
     */
    var closeWin = function(){
    	var configClose = {
    		title:'退出系统',
    		msg:'您确认退出系统?',
    		fn:function(confirm){
    			if(confirm == 'ok')
    				window.close();
    		}
    	}
    	askMesg(configClose);
    }
    /**
     * 注销系统操作
     */
    var logout = function(){
    	var configLogout = {
    		title:'注销系统',
    		msg:'您确认注销系统?',
    		fn:function(confirm){
    			if(confirm == 'ok'){
    				window.close();
    				window.open('login.html');
    			}
    		}
    	}
    	askMesg(configLogout);
    }
   
	
	var webQQ = function(){
		clientWin = new com.bhtec.view.util.qq.ClientWin({
                        currentUser: userCode
                    });
        clientWin.show();
        //页面退出的时候
        dojo.addOnUnload(clientWin, "leave");
	}
	
	/**
	 * 常用功能菜单
	 */
	var commFunMenu = function(){
		var commFunArr = new Array();
		for(i=0;i<assignUumUserCommfunList.length;i++){
			var modEnIdThi = 'commfun_'+assignUumUserCommfunList[i].modEnId;
			var modNameThi = assignUumUserCommfunList[i].modName;
			var modImgClsThi = assignUumUserCommfunList[i].modImgCls;
			var clickMenu = function(item){
				var menuFlag = item.id.indexOf('_');
				var menuEnId = item.id.substr(menuFlag + 1);
				var configMenu = {
					id:menuEnId,
					iconCls:item.iconCls,
					text:item.text
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
			commFunArr.push({id:modEnIdThi,clickHideDelay:0,text:modNameThi,iconCls:modImgClsThi,handler:clickMenu});
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
								var clickMenu = function(item){
									var configMenu = {
										id:item.id,
										iconCls:item.iconCls,
										text:item.text
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
								sysThiMenu.push({id:modEnIdThi,clickHideDelay:0,text:modNameThi,iconCls:modImgClsThi,handler:clickMenu});
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
    	firstMenuArr.push({text:'帮助',tooltip:'帮助(F1)',iconCls:'help',handler:''},'-');
    	firstMenuArr.push({text:'WebQQ',tooltip:'WebQQ(F8)',iconCls:'qq',handler:webQQ},'-');
    	firstMenuArr.push({text:'常用',iconCls:'usedfunc',menu:usedFunMenu()},'-');
    	firstMenuArr.push({text:'皮肤',iconCls:'change_skin',menu:changeSkinMenu},'-');
    	firstMenuArr.push({text:'注销',tooltip:'注销(F6)',iconCls:'logout',handler:logout},'-');
    	firstMenuArr.push({text:'退出',tooltip:'退出(F7)',iconCls:'close',handler:closeWin},'-');
    	
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