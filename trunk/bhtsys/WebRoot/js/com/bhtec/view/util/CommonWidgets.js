/**
 * 公用组件页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.util.CommonWidgets
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.util');
com.bhtec.view.util.CommonWidgets = function(config) {
}
com.bhtec.view.util.CommonWidgets.prototype.cuvWindowId = 'cuvWindowId';

/**
 * 公用textField
 * 
 * @param {}
 *            config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.textField = function(config){
	   var textField = {
						xtype:'textfield',	
						name:config.name||'without_name',
						fieldLabel:config.fieldLabel||'无名称',
						width:config.width||basicConstant.CMP_WIDTH,
						height:config.height||basicConstant.CMP_HEIGHT,
						value:config.value||'',
						maxLength:config.maxLength||20,
						emptyText:config.allowBlank==undefined?(config.emptyText||''):'请输入...',
						allowBlank:config.allowBlank==undefined?true:config.allowBlank,
						blankText:config.blankText||(config.fieldLabel+' 不能为空'),
						originalValue:config.value||''
	   				};
	   Ext.applyIf(textField,config);
	   return textField;
}

/**
 * 公用dateField
 * 
 * @param {}
 *            config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.dateField = function(config){
	   var dateField = {
						xtype	   : 'datefield',	
						name 	   : config.name||'without_name',
						fieldLabel : config.fieldLabel||'无名称',
						width      : config.width||basicConstant.CMP_WIDTH,
						height     : config.height||basicConstant.CMP_HEIGHT,
						value      : config.value||'',
						format	   : config.format||'Y-m-d H:i:s',
						altFormats : config.altFormats||'Y-m-d H:i:s',
						editable   : config.editable==undefined?false:config.editable,
						emptyText  : config.allowBlank==undefined?'':'选择...',
						allowBlank : config.allowBlank==undefined?true:config.allowBlank,
						blankText  : config.blankText||(config.fieldLabel+' 不能为空'),
						originalValue:config.value||''
	   				};
	   Ext.applyIf(dateField,config);
	   
	   return dateField;
}

/**
 * 公用numberField
 * 
 * @param {}
 *            config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.numberField = function(config){
	   var numberField = {
						xtype	   : 'numberfield',	
						name 	   : config.name||'without_name',
						fieldLabel : config.fieldLabel||'无名称',
						width      : config.width||basicConstant.CMP_WIDTH,
						height     : config.height||basicConstant.CMP_HEIGHT,
						value      : config.value||'',
						emptyText  : config.allowBlank==undefined?(config.emptyText||''):'请输入...',
						allowBlank : config.allowBlank==undefined?true:config.allowBlank,
						blankText  : config.blankText||(config.fieldLabel+' 不能为空'),
						originalValue:config.value||''
	   				};
	   Ext.applyIf(numberField,config);
	   return numberField;
}


/**
 * 公用combo
 * @param {} config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.comboBox = function(config){
		var comboBox = {
			xtype 		: 'combo',
			name 		: config.name||'without_name',
			fieldLabel 	: config.fieldLabel||'无名称',
			width       : config.width||basicConstant.CMP_WIDTH,
			height      : config.height||basicConstant.CMP_HEIGHT,
			blankText   : config.blankText||(config.fieldLabel+' 不能为空'),
			mode 		: config.mode||'local',
			editable 	: config.editable==undefined?false:config.editable,
			allowBlank 	: config.allowBlank==undefined?true:config.allowBlank,
			store 		: config.store||[],
			valueField 	: config.valueField||'',
			displayField: config.displayField||'',
			value 		: config.value||'',
			maxLength   : config.maxLength||10,
			triggerAction : config.triggerAction||'all',
			originalValue : config.value||''
		};	
		Ext.applyIf(comboBox,config);
	   	return comboBox;
}	
/**
 * 公用triggerField
 * @param {} config
 * @param window	点击triggerField弹出窗口方法
 */
com.bhtec.view.util.CommonWidgets.prototype.triggerField = function(config){
		var triggerField = {
					xtype 		: 'trigger',					
					name 		: config.name||'without_name',
					fieldLabel 	: config.fieldLabel||'无名称',
					triggerClass: 'x-form-search-trigger',
					editable 	: config.editable==undefined?false:config.editable,
					width       : config.width||basicConstant.CMP_WIDTH,
					height      : config.height||basicConstant.CMP_HEIGHT,
					emptyText	: config.allowBlank==undefined?'':'请选择...',
					allowBlank 	: config.allowBlank==undefined?true:config.allowBlank,
					value 		: config.value||'',
					maxLength   : config.maxLength||10,
					blankText   : config.blankText||(config.fieldLabel+' 不能为空'),
					onTriggerClick : config.window,
					originalValue : config.value||''
		}
		Ext.applyIf(triggerField,config);
		return triggerField;
	};

/**
 * 公用treePanel
 * @param {} config
 * @param clickNode自字义点击树结点事件
 */
com.bhtec.view.util.CommonWidgets.prototype.treePanel = function(config){
		var treePanel = {
					xtype       : 'treepanel',
					name 		: config.name||'without_name',
					animate 	: config.animate==undefined?true:config.animate,
					height 		: config.height||basicConstant.TREE_HEIGHT,
					width		: config.width||basicConstant.TREE_WIDTH,
					border 		: config.border==undefined?true:config.border,
					autoScroll  : config.autoScroll==undefined?true:config.autoScroll,
					containerScroll : config.containerScroll==undefined?true:config.containerScroll,
					dropConfig 	: {appendOnly : true},
					frame		: config.frame==undefined?false:config.frame,
					rootVisible : config.rootVisible==undefined?false:config.rootVisible,
					root		: config.rootNode||com.bhtec.view.util.CommonWidgets.prototype.treeNode({
												id:'-1',
												expanded :true
							    			}),
					listeners	: {
						click:function(node,e){
							if(config.clickNode != undefined)
								config.clickNode(node,e);
						},
						checkchange:function(node,checked){
							if(config.checkNode != undefined)
								config.checkNode(node,checked);
						}
					}
		};		
		Ext.applyIf(treePanel,config);
		
		return treePanel;
	};
/**
 * 公用asyncTreePanel
 * @param {} config
 * @param clickNode自字义点击树结点事件
 */
com.bhtec.view.util.CommonWidgets.prototype.asyncTreePanel = function(config){
		var asyncTreeNodeConfig = {
							id:'0',
							text:config.rootText||'',
							expanded:true
						};
		
		if(config.isChecked){
			Ext.apply(asyncTreeNodeConfig,{checked:false})
		}
		//根结点id=0
		var rootNode = new Ext.tree.AsyncTreeNode(asyncTreeNodeConfig);
		//异步loader
		var loader = new  Ext.tree.TreeLoader({   
						          	dataUrl:config.url||''
						        });
		loader.on('beforeload',function(treeLoader,node){
					loader.baseParams.modViewRecId = node.attributes.id;
		})
		var treePanel = {
					xtype       : 'treepanel',
					name 		: config.name||'without_name',
					animate 	: config.animate==undefined?true:config.animate,
					height 		: config.height||Ext.getBody().getHeight()*0.58,
					border 		: config.border==undefined?false:config.border,
					autoScroll  : config.autoScroll==undefined?true:config.autoScroll,
					containerScroll : config.containerScroll==undefined?true:config.containerScroll,
					dropConfig 	: {appendOnly : true},
					frame		: config.frame==undefined?false:config.frame,
					rootVisible : config.rootVisible==undefined?false:config.rootVisible,
					loader		: loader,
					root		: config.rootNode||rootNode,
					title	    : config.title||'树',
					listeners	: {
						click:function(node,e){
							if(config.clickNode != undefined)
								config.clickNode(node,e);
						},
						checkchange:function(node, checked){
							if(config.checkNode != undefined)
								config.checkNode(node,checked);
						}
					},
					tools:[{id:'minus',qtip:'展开树',handler:function(event,toolEl,panel){
						if(toolEl.dom.className == 'x-tool x-tool-minus'){
			        		toolEl.dom.className = 'x-tool x-tool-plus';
			        		toolEl.dom.qtip = '收起树';
			        		panel.getRootNode().expand(true);//展开
			        	}else{
			        		toolEl.dom.className = 'x-tool x-tool-minus'
			        		toolEl.dom.qtip = '展开树';
			        		panel.getRootNode().collapse(true);//收起
			        	}
						
					}}]
		};		
		Ext.applyIf(treePanel,config);
		
		return treePanel;
}

/**
 * 公用asyncTreeNode
 * @param {} config
 */
com.bhtec.view.util.CommonWidgets.prototype.asyncTreeNode = function(config){
		var initConfig = {
				id 			: config.id||'0',
				text 		: config.text||'without_name',
				draggable 	: config.draggable==undefined?false:config.draggable, 
				allowChildren : config.allowChildren==undefined?true:config.allowChildren,
				expanded	: config.expanded==undefined?false:config.expanded
		};
		Ext.applyIf(initConfig,config);
		var asyncTreeNode = new Ext.tree.AsyncTreeNode(initConfig);
		return asyncTreeNode;
	};
	
/**
 * 公用treeNode
 * @param {} config
 */
com.bhtec.view.util.CommonWidgets.prototype.treeNode = function(config){
	   	var initConfig = {
				id 			: config.id||'0',
				text 		: config.text||'without_name',
				draggable 	: config.draggable==undefined?false:config.draggable, 
				allowChildren : config.allowChildren==undefined?true:config.allowChildren,
				expanded	: config.expanded==undefined?false:config.expanded
		};
		Ext.applyIf(initConfig,config);
		var treeNode = new Ext.tree.TreeNode(initConfig);
		return treeNode;
	};

/**
 * 公用treeWindow
 * @param {} config
 * @param clickNode自字义点击树结点事件
 */
com.bhtec.view.util.CommonWidgets.prototype.treeWindow = function(config){
		var treeWin = com.bhtec.view.util.CommonWidgets.prototype.window({
				id    : config.winId||Math.random(),
				title : config.title||'无标题',
				height 		: config.height||Ext.getBody().getHeight()*0.7,
				width		: config.width||Ext.getBody().getWidth()*0.3,
				items : config.items||[],
				layout:'auto',
				buttons:[{
							xtype : 'button',
							id : "confirmButton",
							iconCls : 'table_save',
							text : '确定',
							handler : function(){this.ownerCt.ownerCt.close()}
						}]
		});
};

/**
 * 公用window
 * @param {} config
 */
com.bhtec.view.util.CommonWidgets.prototype.window = function(config){
		var winConfig = {
				id    : config.id||Math.random(),
				title : config.title||'无标题',
				resizable : false,
				autoScroll:config.autoScroll==undefined?false:config.autoScroll,
				width : config.width||basicConstant.WIN_WIDTH,
				height: config.height||basicConstant.WIN_HEIGHT,
				modal : config.modal==undefined?true:config.modal,
				items : config.items||[],
				border 	  : config.border==undefined?false:config.border,
				bodyStyle : config.bodyStyle||('padding:10px;'+basicConstant.PAGE_BACKGROUND),
				layout	  : config.layout||'fit',
				buttons   : config.buttons||[],
				listeners : config.listeners
		};
		Ext.applyIf(winConfig,config);
		var window = new Ext.Window(winConfig);
		window.show();
};
/**
 * 保存添加按钮enable
 * @param {} config
 */
com.bhtec.view.util.CommonWidgets.prototype.enableSaveButton = function(config){
	getExtCmpById('saveButton').setDisabled(false);
	getExtCmpById('saveAddButton').setDisabled(false);
}

/**
 * 常用保存按钮
 * 
 * @param {}
 *            config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.saveButton = function(config){
	//弹出页面是否隐藏保存按钮
	var isHidden = false;
	if(config.allButtonHidden){
		isHidden = true;
	}
	var saveButton = {
		xtype : 'button',
		id : config.id||"saveButton",
		iconCls : 'table_save',
		text : '保存',
		hidden:isHidden,
		handler : config.handler||function(){
			if(getExtCmpById(basicConstant.FORM_ID).form.isValid() == true){
				this.setDisabled(true);
				var callMethod = function(method){        
			        method();        
			    }
			    if(config.save)
					callMethod(config.save||'');//保存回调
			}
		}
	}
	Ext.applyIf(saveButton,config)
	return saveButton;
};
/**
 * 常用保存并添加
 * @param {} config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.saveAddButton = function(config){
	//弹出页面是否隐藏保存按钮
	var isHidden = false;
	if(config.allButtonHidden){
		isHidden = true;
	}
	
	if(config.modify){
		isHidden = true;
	}
	return {
		xtype : 'button',
		id : config.id||"saveAddButton",
		iconCls : 'table_multiple',
		text : '保存添加',
		hidden:isHidden,
		handler : function(){
			if(getExtCmpById(basicConstant.FORM_ID).form.isValid() == true){
				var saveAddButton = this;
				saveAddButton.setDisabled(true);
				var callMethod = function(method){        
			        method();
			    }
			    if(config.saveAdd)
					callMethod(config.saveAdd||'');//保存回调
			}
		}
	}
};
/**
 * 重置按钮
 * @param {} config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.resetButton = function(config){
	//弹出页面是否隐藏重置按钮
	var isHidden = false;
	if(config.allButtonHidden){
		isHidden = true;
	}
	return {
		xtype : 'button',
		iconCls : 'table',
		text : '重置',
		hidden:isHidden,
		handler : function(){
			resetForm(basicConstant.FORM_ID);
		}
	}
};
/**
 * 关闭按钮
 * @param {} config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.closeButton = function(config){
	return {
		xtype : 'button',
		iconCls : 'table_close',
		text : '关闭',
		handler : config.handler||function(){
			this.ownerCt.ownerCt.ownerCt.ownerCt.close();
		}
	}
};
/**
 * 公用fieldSet
 * @param {} config
 * @return {}
 */
com.bhtec.view.util.CommonWidgets.prototype.fieldSet = function(config){
		var customColumnItems = config.customColumnItems;//是否自定义
		var columnFields = config.columnFields||[];
		var newColumnFields = [];
		if(customColumnItems == undefined
			|| customColumnItems == false){
			var colLength = columnFields.length;
			for(var i=0;i<colLength;i++){
				newColumnFields.push({
						border : false,
						layout : "form",
						columnWidth : 0.5,
						items : columnFields[i],
						selectOnFocus : true
				});
			}
		}else{
			newColumnFields = columnFields;
		}
		var fieldSet = {
						xtype	  : 'fieldset',	
						title	  : config.title||'无标题',
						layout 	  : 'column',
//						autoHeight:true,
//						autoWidth:true,
						bodyStyle : config.bodyStyle||'padding-left:20px;padding-top:10px;',
						items 	  : newColumnFields
					};
		Ext.applyIf(fieldSet,config);
		return fieldSet;
}

/**
 * 弹出窗口
 * @param {} config
 */
com.bhtec.view.util.CommonWidgets.prototype.cuvWindow = function(config){
		var paddingLeft = 'padding-left:200px;';
		if(Ext.isIE)paddingLeft = 'padding-left:20px;';
		var formPanel = {
			xtype:'form',
			id:config.formId||basicConstant.FORM_ID,
			border:false,
			autoHeight:true,
			autoWidth:true,
			trackResetOnLoad :true,
			fileUpload : config.fileUpload||true,
			enctype : config.enctype||'',
			labelWidth: config.labelWidth||80,
			items:[com.bhtec.view.util.CommonWidgets.prototype.fieldSet(config),
						{
							xtype	  : 'panel',
							layout 	  : 'column',
							border    : false,
							bodyStyle : paddingLeft,
							autoHeight:true,			
							items:[{
										border : false,
										layout : "form",
										columnWidth : 0.1
									},{
										border : false,
										layout : "form",
										columnWidth : 0.2,
										items : [com.bhtec.view.util.CommonWidgets.prototype
													.saveAddButton(config)]
									}, {
										border : false,
										layout : "form",
										columnWidth : 0.2,
										items : [com.bhtec.view.util.CommonWidgets.prototype
													.saveButton(config)]
									}, {
										border : false,
										layout : "form",
										columnWidth : 0.2,
										items : [com.bhtec.view.util.CommonWidgets.prototype
													.resetButton(config)]
									}, {
										border : false,
										layout : "form",
										columnWidth : 0.2,
										items : [com.bhtec.view.util.CommonWidgets.prototype
													.closeButton({winId:com.bhtec.view.util.CommonWidgets.prototype.cuvWindowId})]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.1
									}]
							}
					]
		};
		
		this.window({
			id:config.id||com.bhtec.view.util.CommonWidgets.prototype.cuvWindowId,
			title:config.title||'无标题',
			height: config.winheight||basicConstant.WIN_HEIGHT,
			autoScroll:config.autoScroll==undefined?false:config.autoScroll,
			items:[formPanel],
			listeners:{
					show :function(){
						var index = config.componentIndex||0;//初始控件索引,获得焦点
				    	this.findByType("textfield")[index].focus(true,100);
				    }
				}
		});
		//为表单域加回车键
		formEnterEvent();
};


/**
 * 公用grid列表
 */
com.bhtec.view.util.CommonWidgets.prototype.gridPanel = function(config){
	/**
	 * checkbox选择模式
	 */	
	var selModel = new Ext.grid.CheckboxSelectionModel();
	/**
	 * 构造列
	 */
	var constractCols = function(){		
		var cols = new Array();
		cols.push(config.selModel||selModel);
		cols.push(new Ext.grid.RowNumberer({
						header : "序号",
						dataIndex : "dataIndex",
						width : 35
				}));
		//列追加
		cols = cols.concat(config.colums||[]);
		return cols;
	}
	
	//列模式
	var colm = new Ext.grid.ColumnModel(constractCols());
	
	var gridPanel = {
			xtype	:	'grid',
			title	:	config.title||'',
			frame   : 	config.frame==undefined?false:config.frame,
			sm 		: 	config.selModel||selModel,
			border 	: 	config.border==undefined?false:config.border,
			stripeRows : config.stripeRows==undefined?true:config.stripeRows,
			viewConfig : new Ext.grid.GridView({
						autoFill : false
					}),
			store 	: 	config.gridStore||[],
			height 	: 	config.height||Ext.getBody().getHeight()-310, 
   			width  	:  	config.width||colm.getTotalWidth(false)+60,//Ext.getBody().getWidth()*0.8-10 //boolean参数指定是否包括隐藏列的宽度
			autoScroll : true,
    		loadMask:	true,
    		columnLines : true,
			cm 		: colm,
			buttonAlign:'center',
			bbar 	: config.bbar||{
						xtype	:'paging',
						store 	: config.gridStore||[],
						pageSize: basicConstant.LIMIT,
						displayInfo: true,
						displayMsg : '显示第 {0}-{1} 条记录，一共 {2} 条',
						emptyMsg   : '没有记录'
					}
		};
	Ext.applyIf(gridPanel,config);
	return gridPanel;
}

/**
 * 公用editorGridPanel列表
 */
com.bhtec.view.util.CommonWidgets.prototype.editorGridPanel = function(config){
	//editorGridId
	var editorGridId = config.id||Math.random();
	//checkbox选择模式
	var selModel = config.selModel||new Ext.grid.CheckboxSelectionModel();
	//开始编辑的单元格 数字
	var startEditeCell = config.startEditeCell||2;
	//显示grid上边按钮
	var displayTbar = config.displayTbar||false;
	//追加列
	var colums = config.colums||[];
	
	//构造列
	var constractCols = function(){		
		var cols = new Array();
		cols.push(selModel);
		cols.push(new Ext.grid.RowNumberer({
						header : "序号",
						width : 35
				}));
		//列追加
		cols = cols.concat(colums);
		return cols;
	}
	
	//列模式
	var colm = new Ext.grid.ColumnModel(constractCols());
	
	var gridPanel = {
			xtype	:	'editorgrid',
			id		:	editorGridId,
			title	:	config.title||'',
			frame   : 	config.frame==undefined?false:config.frame,
			sm 		: 	selModel,
			border 	: 	config.border==undefined?false:config.border,
			stripeRows : config.stripeRows==undefined?true:config.stripeRows,
			viewConfig : new Ext.grid.GridView({
						autoFill : false
					}),
			store 	: 	config.gridStore||[],
			height 	: 	Ext.getBody().getHeight()-310, 
   			width  	:  	config.width||colm.getTotalWidth(false)+60,//Ext.getBody().getWidth()*0.8-10 //boolean参数指定是否包括隐藏列的宽度
			autoScroll : true,
    		loadMask:	true,
    		columnLines : true,
			cm 		: colm,
			clicksToEdit : config.clicksToEdit||1,
			listeners:{
				afterrender:function(){
					if(config.gridStore == undefined
						|| config.gridStore.data.length==0)
						addRow();
				}
			}
		};
	//增加行记录
	var addRow = function(){
		var editorGrid = getExtCmpById(editorGridId);
		var store = editorGrid.getStore();
		//动态增加记录行
		var recordObject = config.recordObj();
		var count = store.getCount();// 获得总行数
		editorGrid.stopEditing();// 停止编辑
		store.insert(count, recordObject);// 插入到最后一行
		editorGrid.startEditing(count, startEditeCell);// 开始编辑1单元格
	}
	// 删除表格记录
	var rowDel = function() {
		var editorGrid = getExtCmpById(editorGridId);
		var store = editorGrid.getStore();
		var rows = editorGrid.getSelectionModel().getSelections();// 返回值为 Record 数组
		if (rows.length == 0) {
			warningMesg({
				msg:'请选择一条记录!'	
			});
		} else {
			if (rows) {
				for (var i = 0; i < rows.length; i++) {
					store.remove(rows[i]);
				}
				editorGrid.getView().refresh();
			}
		}
	}
	if(displayTbar){
		gridPanel.tbar = new Ext.Toolbar({
						items : [{
								 text:'增加',
								 iconCls:'table_add',
								 handler:function(){
								 	addRow();
								 }
							   },{
							   	 text:'删除',
							   	 iconCls:'table_delete',
								 handler:function(){
								 	rowDel();
								 }
						   }]
					  })
	}
	Ext.applyIf(gridPanel,config);
	return gridPanel;
}

/**
 * 公用的panel
 */
com.bhtec.view.util.CommonWidgets.prototype.panel = function(config){
		var panel = {
						xtype	  : 'panel',
						layout 	  : config.layout||'column',
						border    : config.border==undefined?false:config.border,
						height	  : config.height||Ext.getBody().getHeight()-300,
						width	  : config.width||Ext.getBody().getWidth()-300,
						buttonAlign:config.buttonAlign||'center',
						items	  :	config.items||[],
						autoScroll : config.autoScroll==undefined?true:config.autoScroll
		}
		Ext.applyIf(panel,config);
		return panel;
}

/**
 * 公用的formpanel
 */
com.bhtec.view.util.CommonWidgets.prototype.formPanel = function(config){
		var formPanel = {
						xtype	  : 'form',
						layout 	  : config.layout||'form',
						border    : config.border==undefined?false:config.border,
						height	  : config.height||Ext.getBody().getHeight()-300,
						width	  : config.width||Ext.getBody().getWidth()-300,
						buttonAlign:config.buttonAlign||'center',
						items	  :	config.items||[]
		}
		Ext.applyIf(formPanel,config);
		return formPanel;
}
/**
 * 公用的radio
 */
com.bhtec.view.util.CommonWidgets.prototype.radio = function(config){
		var radio = {
	        xtype	: 'radiogroup',
	        fieldLabel:config.fieldLabel||'无名称',
	        items	: config.items||[]
        }
		Ext.applyIf(radio,config);
		return radio;
}
/**
 * 公用的textArea
 */
com.bhtec.view.util.CommonWidgets.prototype.textArea = function(config){
		var textarea = {
	        xtype	: 'textarea',
	        fieldLabel:config.fieldLabel||'无名称',
	        height:config.height||150,
	        width:config.width||300,
	        items	: config.items||[]
        }
		Ext.applyIf(textarea,config);
		return textarea;
}

