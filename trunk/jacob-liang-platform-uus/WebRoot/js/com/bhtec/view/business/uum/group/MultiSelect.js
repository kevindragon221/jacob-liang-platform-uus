/**
 * 对导列表
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.util.ux.MultiSelect
 * @date 2010-10-23
 */
Ext.namespace('com.bhtec.view.business.uum.group');
com.bhtec.view.business.uum.group.MultiSelect = function(config) {
	var commWidgets = this; //父类调用
	var height = Ext.getBody().getHeight() * 0.45;
	
	/**
	 * 左侧面板
	 */
	var leftPanel = commWidgets.panel({
				title : config.leftTitle || '',
				layout : 'form',
				border : true,
				height : height,
				width : 290,
				items:[config.leftPanel||{}]
			});
	/**
	 * 右侧面板
	 */
	var rightPanel = commWidgets.panel({
				title : config.rightTitle || '',
				layout : 'form',
				border : true,
				height : height,
				width : 290,
				items:[config.rightPanel||{}]
			});
	/**
	 * 按钮面板
	 */
	var buttons = commWidgets.panel({
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
		bodyStyle : 'padding-left:15px;',
		layout : 'column',
		labelWidth : 60,
		items : [{
					baseCls : 'x-plain',
					bodyStyle : 'padding-left:5px;padding-top:2px;',
					border : false,
					columnWidth : 1,
					layout : 'form',
					items : [config.headerPanel]
				}, {
					columnWidth : .4,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 0px',
					items : [leftPanel]
				}, {
					columnWidth : .1,
					baseCls : 'x-plain',
					bodyStyle : 'padding:0px 0 5px 5px',
					items : [buttons]
				}, {
					columnWidth : .5,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px',
					items : [rightPanel]
				}],
		buttonAlign : 'center',
		buttons : config.buttons||[]
	});
	
	return 	multiPanel;
}
Ext.extend(com.bhtec.view.business.uum.group.MultiSelect,
		com.bhtec.view.util.CommonWidgets, {});