/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtOutlookBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.general');
com.bhtec.view.viewport.general.OutlookBar = function(config) {
	//动态改变树，config.sysMenuId 默认为第一个系统菜单id
	var dynamicChangeTree = com.bhtec.view.viewport.general.DynamicChangeTree(basicConstant.GENERAL);		
    var accordion = {
    	xtype:'panel',
    	id:basicConstant.LEFT_MENU_ID,
    	title:config.sysMenuName,
    	iconCls:'platform',
        region:'west',
        margins:'5 0 5 5',
        split:true,
        lines: false,
        width: Ext.getBody().getWidth()*0.15,
        layout:'accordion',
        collapseMode: 'mini',
        frame:false,
        animCollapse: true,
        collapsible: true,
        border:true,
        layoutConfig:{
                animate:true
        },
        items:dynamicChangeTree.sysSecThiMenu(config.sysMenuId),
        listeners:{
        	render  : function(panel){
        		panel.tools.toggle.dom.qtip='收起';
        	}
        }
    };
	return accordion;
}