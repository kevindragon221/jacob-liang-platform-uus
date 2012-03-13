/**
 * 公用列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.util.List
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.util');
com.bhtec.view.util.List = function(config) {
	var bodyHeight = Ext.getBody().getHeight();
	var queryHandle='',		//查询操作
		resetHandle='',		//重置查询操作
		queryConditions=[],	//动态查询条件域
		queryColWidth=0.5,	//查询列宽
		toolbars=[],		//列表工具栏按钮
		colums=[],			//列模式
		gridStore=[],		//grid数据
		gridId='',			//gridId
		treeWin='',			//列表树
		treeWidth=0.18,		//列表树宽度
		gridWidth=0.82,		//grid宽度
		currentPosition='',     //当前位置
		plugins='',
		queryPanelHeight = bodyHeight*0.135,
		toolBarHeight = bodyHeight*0.04,
		gridHeight = bodyHeight*0.59
	/**
	 * 参数判断校验
	 */
	if(config != undefined){
		//查询参数
		queryHandle = config.queryPara.query||queryHandle;
		resetHandle = config.queryPara.reset||resetHandle;
		queryConditions = config.queryPara.queryCondition||queryConditions;
		queryColWidth = config.queryPara.queryColWidth||queryColWidth;
		queryPanelHeight = config.queryPara.queryPanelHeight||queryPanelHeight;
		currentPosition = config.queryPara.currentPosition||currentPosition;
		toolbars = config.toolbarPara.toolbar||toolbars;
		colums = config.gridListPara.cols||colums;
		gridStore = config.gridListPara.store||gridStore;
		gridId = config.gridListPara.gridId||gridId;
		treeWin = config.gridListPara.treeWin||treeWin;
		treeWidth = config.gridListPara.treeWidth||treeWidth;
		gridWidth = config.gridListPara.gridWidth||gridWidth;
		plugins = config.gridListPara.plugins||plugins;
		gridHeight = config.gridListPara.gridHeight||gridHeight;
	}
	/**
	 * 查询按钮
	 */
	var queryButton = {
					xtype:'button',
					iconCls:'table_find',
					text : "查询",
					handler : queryHandle
				};
	/**
	 * 重置按钮
	 */		
	var resetButton = {
					xtype:'button',
					iconCls:'table',
					text : "重置",
					handler : resetHandle
				};
				
	/**
	 * 查询域
	 */
	var queryField = function(){		
		var queryArray = new Array();
		queryArray = queryArray.concat(queryConditions);
		queryArray.push({
					border : false,
					layout : "form",
					columnWidth : queryColWidth,
					items : [queryButton]
				});
		queryArray.push({
					border : false,
					layout : "form",
					columnWidth : queryColWidth,
					items : [resetButton]
				});
		return queryArray;
	}
	
	/**
	 * 公用查询条件
	 */		
	var fieldSet = {
			xtype:'fieldset',
			title:'查询条件',
			layout:'column',
			autoHeight:true,
			border:true,
			items:queryField()
		};
	/**
	 * 当前位置
	 */
	var currentPosition = {
		xtype:'label',
		height:20,
		border:false,
		cls:'currPosition',
		autoWidth:true,
		text:'当前位置:'+currentPosition
	}
	/**
	 * 查询面板
	 */
	var queryPanel = {
		xtype:'panel',
		frame:true,
		bodyStyle : 'padding-left:1px;padding-right:5px;',
		layout:'form',
		border:false,
		height:queryPanelHeight,
		items:[currentPosition,fieldSet]
	}
	
	/**
	 * 工具栏按钮
	 */
	var toolBar = {
				xtype:'toolbar',
				height:toolBarHeight,
				items:toolbars
			};
	/**
	 * checkbox选择模式
	 */	
	var selModel = new Ext.grid.CheckboxSelectionModel();
	
	/**
	 * 构造列
	 */
	var constractCols = function(){		
		var cols = new Array();
		cols.push(selModel);
		cols.push(new Ext.grid.RowNumberer({
						header : "序号",
						dataIndex : "dataIndex",
						width : 35
				}));
		//列追加
		cols = cols.concat(colums);
		return cols;
	}
	
	//列模式
	var colm = new Ext.grid.ColumnModel(constractCols());
	
	/**
	 * 公用grid列表
	 */
	var gridPanel = {
				xtype:'grid',
				title:((config != undefined) && (config.gridListPara.title))?config.gridListPara.title:'',
				id : gridId,
				frame : true,
				sm : selModel,
				border : false,
				columnLines : true, 
				stripeRows : true,
				viewConfig : new Ext.grid.GridView({
							autoFill : true
						}),
				store : gridStore,
				height : gridHeight, 
//       			width : ((config != undefined) && (config.gridListPara.width))?config.gridListPara.width:colm.getTotalWidth(false)+60,//basicConstant.BODY_WIDTH*0.8-10 //boolean参数指定是否包括隐藏列的宽度
				autoScroll : true,
//        		loadMask:true,
				plugins: plugins,
				cm : colm,
				bbar : {
							xtype:'paging',
							store : gridStore,
							pageSize : basicConstant.LIMIT,
							displayInfo : true,
							plugins : new Ext.ux.Andrie.pPageSize(),
							displayMsg : '显示第 {0}-{1} 条记录，一共 {2} 条',
							emptyMsg : '没有记录'
							
						}
			};
			
	/**
	 * 是否带树的grid
	 */
	var treeGridPanel = '';
	if(treeWin == ''){
		treeGridPanel = gridPanel;
	}else{
		treeGridPanel = {
				xtype:'panel',
				layout:'column',
				items:[{
							border : false,
							layout : "form",
							columnWidth : treeWidth,
							items : treeWin.treePanel
						},
						{
							border : false,
							layout : "form",
							columnWidth : gridWidth,
							items : gridPanel
						}
					]
		};
	}
	
	/**
	 * 公用formPanel
	 */
	var formPanel = {
				xtype:'form',
				autoScroll : true,
				border : false,
				items : [queryPanel, toolBar, treeGridPanel]
			};
	/**
	 * 返回列表页面
	 */
	return {
		formPanel:formPanel
	}
}