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
	var height = Ext.getBody().getHeight() * 0.5;
	
	/**
	 * 左侧面板
	 */
	var leftPanel = commWidgets.panel({
				title : config.leftTitle || '',
				layout : 'form',
				border : true,
				height : height,
				autoWidth : true,
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
				autoWidth : true,
				items:[config.rightPanel||{}]
			});
	/**
	 * 按钮面板
	 */
	var buttons = commWidgets.panel({
				layout : 'form',
				height : height,
//				autoWidth : true,
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
	var multiformPanel = commWidgets.formPanel({
		border : false,
		autoWidth : true,
		autoHeight : true,
		bodyStyle : 'padding-left:15px;',
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
					columnWidth : .45,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					items : [leftPanel]
				}, {
					columnWidth : .1,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px 0 5px 5px',
					items : [buttons]
				}, {
					columnWidth : .45,
					baseCls : 'x-plain',
					bodyStyle : 'padding:5px',
					items : [rightPanel]
				}],
		buttonAlign : 'center',
		buttons : config.buttons||[]
	});
	
	/**
	 * 对导按钮
	 */
	var multiSelWin = commWidgets.window({
				id:'selectedToId',
				title : config.winTitle || '',
				layout : 'fit',
				width : Ext.getBody().getWidth() * 0.55,
				height : Ext.getBody().getHeight() * 0.75,
				items : [multiformPanel]
			});
}
Ext.extend(com.bhtec.view.util.ux.MultiSelect,
		com.bhtec.view.util.CommonWidgets, {});