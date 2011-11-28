/**
 * 菜单跳转控制类
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.control.MenuControl
 * @date 2010-07-11
 */
$import('./js/com/bhtec/control/MenuControlPlt', 'js');
$import('./js/com/bhtec/control/MenuControlUum', 'js');
Ext.namespace('com.bhtec.control');
com.bhtec.control.MenuControl = function(config) {
	if(config.moduleLabel == true){
		var moduleLabelPlt = new com.bhtec.control.MenuControlPlt(config);//平台菜单
		if(moduleLabelPlt.moduleLabelList){
			return moduleLabelPlt;
		}
		var moduleLabelUum = new com.bhtec.control.MenuControlUum(config);//统一用户菜单
		if(moduleLabelUum.moduleLabelList){
			return moduleLabelUum;
		}
		return;
	}
	var menuControlPlt = new com.bhtec.control.MenuControlPlt(config);//平台菜单
	var menuControlUum = new com.bhtec.control.MenuControlUum(config);//统一用户菜单
	var list = new com.bhtec.view.util.List(); 
	if(menuControlPlt.pageList){//列表页面
	 	list = new com.bhtec.view.util.List(menuControlPlt.pageList);
	}else if(menuControlUum.pageList){
		list = new com.bhtec.view.util.List(menuControlUum.pageList);
	}else{//非列表页面
		if(menuControlPlt.pageForm){
			list = menuControlPlt.pageForm;
		}else if(menuControlUum.pageForm){
			list = menuControlUum.pageForm;
		}
	}
		
	var addTab = function() {
		//主页面板
		var mainPagePanel = getExtCmpById(basicConstant.MAIN_PAGE_ID);
        mainPagePanel.add({
        	id:config.id+'_tab',
            title: config.text,
            iconCls: config.iconCls,            
            closable:true,
            bodyStyle : basicConstant.PAGE_BACKGROUND,
            items:[list.formPanel]
        }).show();
    }
    
    return {
    	addTab:addTab
    }
   
}