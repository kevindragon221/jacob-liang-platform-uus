/**
 * 对导列表
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.util.ux.MultiSelect
 * @date 2010-10-23
 */
Ext.namespace('com.bhtec.view.util.ux');
com.bhtec.view.util.ux.MultiSelect = function(config) {
	var commWidgets = this; //父类调用
	var height = Ext.getBody().getHeight() * 0.45;
	
	/**
	 * 按钮面板
	 */
	var assignButtons = commWidgets.panel({
				layout : 'form',
				height : height,
				autoWidth : true,
				border : false,
				items : [{
							bodyStyle : 'padding-top:100px',
							border : false,
							items : [{
										xtype : 'button',
										iconCls : 'right_arrow',
										text : '分配',
										handler:function(){
											config.selected()||'';
										}
									}]
						}, {
							bodyStyle : 'padding-top:80px',
							border : false,
							items : [{
										xtype : 'button',
										iconCls : 'left_arrow',
										text : '取消',
										handler:function(){
											config.unselected()||'';
										}
									}]
						}]
			});
	
	/**
	 * 多面板组合
	 */
	var multiPanel = commWidgets.panel({
		border : false,
		autoWidth : true,
		autoHeight : true,
		bodyStyle : config.bodyStyle||'padding-left:25px;',
		layout : 'column',
		labelWidth : 60,
		items : [{
					baseCls : 'x-plain',
					bodyStyle : 'padding-left:5px;',
					border : false,
					columnWidth : 1,
					layout : 'form',
					items : [config.headerPanel]
				}, {
					columnWidth : .4,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					items : [config.leftPanel]
				}, {
					columnWidth : .1,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					items : [assignButtons]
				}, {
					columnWidth : .5,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px',
					items : [config.rightPanel]
				}],
		buttonAlign : 'center',
		buttons : config.buttons||[],
		listeners : config.listeners||{}
	});
	//显示系统
	if(config.isShowWindow == true){
	 	commWidgets.window({
				id:'selectedToId',
				title : config.winTitle || '',
				bodyStyle : 'padding-left:25px;'+basicConstant.PAGE_BACKGROUND,
				layout : 'fit',
				width : Ext.getBody().getWidth() * 0.55,
				height : Ext.getBody().getHeight() * 0.65,
				items : [multiPanel]
			});
	}else{
		return multiPanel;
	}
}
Ext.extend(com.bhtec.view.util.ux.MultiSelect,
		com.bhtec.view.util.CommonWidgets, {});