/**
 * 地区列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.platform.district.DistrictList
 * @date 2010-12-28
 */
Ext.namespace('com.bhtec.view.business.platform.district');
com.bhtec.view.business.platform.district.DistrictList = function(config){
	var districtName_q = 'districtName_q';
	var districtLevel_q = 'districtLevel_q';
	var moduleGridId = 'districtGridId';
	var districtId = 'districtId';
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : 'form',
					columnWidth : 0.3,
					items : [com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:districtName_q,
								width:150,
								fieldLabel : config.districtName,
								listeners: {
					                specialkey: function(field, e){
					                    if (e.getKey() == e.ENTER) {
					                       query();
					                    }
					                }
					            }
							})]
				},{
					border : false,
					layout : 'form',
					columnWidth : 0.3,
					items : [ //地区级别
								com.bhtec.view.util.CommonWidgets.prototype.comboBox({
										id : districtLevel_q,
										fieldLabel : config.districtLevel,
										width:150,
										store:new Ext.data.JsonStore({
												url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
												baseParams :{
													bigTypeCode:'district'
												},
												root:'sysplDicSmallTypeListt',
												autoLoad : true,
												fields 	: ['smallTypeCode','smallTypeName']
											}),
										valueField 	: 'smallTypeCode',
										displayField: 'smallTypeName',
										value:''
								})
							]
				});
		return queryArr;
	}
	/**
	 * 查询操作
	 */
	var query = function(){		
		var configQuery = {
				url : 'districtAction!findDistrictByCon.action',
				params : {
					districtName : getExtCmpValueById(districtName_q),
					districtLevel : getExtCmpValueById(districtLevel_q)
				},
				callBack : function(returnData) {
					queryFillGridList(moduleGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(districtName_q);
		resetCmpValueById(districtLevel_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'districtId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.districtName,
				dataIndex : 'districtName',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : config.districtCode,
				dataIndex : 'districtCode',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.districtPostal,
				dataIndex : 'districtPostal',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.districtTelcode,
				dataIndex : 'districtTelcode',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.districtLevel,
				dataIndex : 'districtLevel',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.upDistrict,
				dataIndex : 'upDistrictName',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true
			});
		colsArr.push({
				header : config.status,
				dataIndex : 'status',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:function(data){
					var status = com.bhtec.util.Data.status;
					for(i=0;i<status.length;i++){
						if(data == status[i].status){
							return status[i].statusName;
						}
					}
				}
			});
		colsArr.push({
				header : config.creator,
				dataIndex : 'creator',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.createDate,
				dataIndex : 'createDate',
				width : 150,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var moduleStore = new Ext.data.JsonStore({
				fields : ['districtId', 'districtName', 'districtCode', 
						  'districtPostal', 'districtTelcode','districtLevel',
						  'upDistrictName', 'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'districtList',
				url : 'districtAction!findDistrictByCon.action'
			});
			
	/**
	 * 树id隐含域
	 */
	var treeIdHidden = new Ext.form.Hidden({
				id:districtId,
				value:''
	});
	var treeNameHidden = new Ext.form.Hidden({
				id:'districtNameId',
				value:''
	});
	
	/**
	 * 为翻页加自定义参数
	 */
    moduleStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						districtName : getExtCmpValueById(districtName_q),
						districtLevel : getExtCmpValueById(districtLevel_q),
						treeId	   : getExtCmpValueById(districtId)
					}; 
		Ext.apply(options.params,new_params); 
	});	
	
	/**
	 * 按钮功能操作
	 */
	var operateFunction = function(optFunLink){
		return function(){
			var opView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(config.moduleId+'_'+basicConstant.OP_VIEW);
			if(opView == undefined || opView == null){
				var xmlDoc = basicConstant.DYNAMIC_LOAD_XMLDOC.get(config.moduleId);
				dynamicLoadModuleJs(xmlDoc,basicConstant.OP_VIEW,config.moduleId);
				opView = basicConstant.DYNAMIC_JS_INSTANCE_MAP.get(config.moduleId+'_'+basicConstant.OP_VIEW);
			}
			var moduleForm = eval(opView);
			if(basicConstant.ADD_OPT_LINK == optFunLink){
				moduleForm.saveForm();
			}else if(basicConstant.MODIFY_OPT_LINK == optFunLink){
				moduleForm.modifyForm();
			}else if(basicConstant.VIEW_OPT_LINK == optFunLink){
				moduleForm.viewForm();
			}else if(basicConstant.ENABLE_OPT_LINK == optFunLink){
				moduleForm.disEnable(basicConstant.ENABLE_OPT_LINK);
			}else if(basicConstant.DISABLE_OPT_LINK == optFunLink){
				moduleForm.disEnable(basicConstant.DISABLE_OPT_LINK);
			}
		}
	}
	
	/**
	 * 工具栏按钮
	 */	
    var toolbar = function(){
		var frametoolbar = new Array();
		var modOptList = fourthModOpt[config.moduleId];
		for(i=0;i<modOptList.length;i++){
			var modOpt = modOptList[i];
			var handlerFun = operateFunction(modOpt.optFunLink);
			frametoolbar.push({
				text:modOpt.modName,
				iconCls:modOpt.modImgCls,
				handler:handlerFun
			},'-');
		}
		
		return frametoolbar;
	};
	
	
	/**
	 * 模块树 panel
	 */
	var treePanel = com.bhtec.view.util.CommonWidgets.prototype.asyncTreePanel({
							id:'treePanelDistrictId',
							title:'地区树',
							rootVisible:true,
							rootText:'地区树',
							url:'districtAction!findNextLevelChildNodes.action',
							clickNode:function(node, e){
								setCmpValueById(districtId,node.id);//点击树结点时，设置结点的id值
								setCmpValueById('districtNameId',node.text);
								var configFind = {
										url:'districtAction!findDistrictByCon.action',
										params:{//带参数查询
												treeId : node.id,
												districtName : getExtCmpValueById(districtName_q),
												districtLevel : getExtCmpValueById(districtLevel_q)
										},
										callBack:function(returnData){
											queryFillGridList(moduleGridId,returnData);//点树结点刷新列表
										}
								};
								ajaxRequest(configFind);
							}
					});
	/**
	 * 查询区
	 */
	var queryPara = {
			query:query,
			reset:reset,
			queryCondition:queryCondition(),
			queryColWidth:0.2,
			currentPosition:basicConstant.PLTM+'省市地区->省市地区管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:moduleStore,
			gridId		:	moduleGridId,
			treeWin		:	{//先渲染treepanel在装载数据
					treePanel:treePanel
			}
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar		:	toolbar()
	}
	/**
	 * 整个列表
	 */
	var configList = {
			queryPara:queryPara,
			toolbarPara:toolbarPara,
			gridListPara:gridListPara
	}	
	return configList;
}
