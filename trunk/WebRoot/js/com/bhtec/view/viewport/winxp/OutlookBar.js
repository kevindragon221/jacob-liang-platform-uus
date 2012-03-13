/**
 * viewport导航条
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.viewport.BhtOutlookBar
 * @date 2010-05-30
 */
Ext.namespace('com.bhtec.view.viewport.winxp');
com.bhtec.view.viewport.winxp.OutlookBar = function(config) {
	//动态改变树，config.sysMenuId 默认为第一个系统菜单id
	var dynamicChangeTree = com.bhtec.view.viewport.general.DynamicChangeTree(basicConstant.WINXP);
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
        height:Ext.getBody().getHeight(),
        autoScroll:true,
        tools:[{id:'down',qtip:'展开',handler:function(event,toolEl,panel){
        	if(toolEl.dom.className == 'x-tool x-tool-down'){
        		toolEl.dom.className = 'x-tool x-tool-up';
        		toolEl.dom.qtip = '收起';
        	}else{
        		toolEl.dom.className = 'x-tool x-tool-down'
        		toolEl.dom.qtip = '展开';
        	}
        	panel.items.each(function(item,index){//展开
        			if(index != 0)		
						item.toggleCollapse(true);						
			});
        }}],
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