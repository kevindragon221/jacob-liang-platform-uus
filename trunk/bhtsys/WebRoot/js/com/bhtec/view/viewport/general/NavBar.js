/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.general.NavBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.general');
com.bhtec.view.viewport.general.NavBar = function(config) {
    /**
     * 点击系统菜单改变outlook树结构
     */
    var dynamicChangeTree = new com.bhtec.view.viewport.general.DynamicChangeTree(outlookBarMainFrame);
    /**
     * 动态装载系统菜单
     */
    var loadMenuForSys = function(moduleId,modName,modImgCls){//moduleId系统模块id
		return function(){
			var leftMenu = getExtCmpById(basicConstant.LEFT_MENU_ID);//outlook菜单
			leftMenu.setTitle(modName);
			leftMenu.setIconClass(modImgCls);
			var items = leftMenu.items;
			items.each(function(item){//删除outlook菜单所有组件
				leftMenu.remove(item);
			});
			leftMenu.add(dynamicChangeTree.sysSecThiMenu(moduleId));//改变outlook菜单
			var viewPort = getExtCmpById(basicConstant.FRAME_VIEW_PORT_ID);//获得viewport
			viewPort.show();//重新show
			viewPort.doLayout();//重新布局
	    };
	}
    /**
     * 系统导航栏菜单，系统菜单为动态
     */
    var sysFirstMenu = function(){
    	var firstMenuArr = new Array();
    	var firstMenuList = firstMenu;
    	if(firstMenuList != ''){
			for(i=0;i<firstMenuList.length;i++){
				var moduleId = firstMenuList[i].moduleId;
				var modName = firstMenuList[i].modName;
				var modImgCls = firstMenuList[i].modImgCls;
				
				firstMenuArr.push({text:modName,iconCls:modImgCls,handler:loadMenuForSys(moduleId,modName,modImgCls)});
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
		items:sysFirstMenu()
	}	
	return navBarPanel;
}