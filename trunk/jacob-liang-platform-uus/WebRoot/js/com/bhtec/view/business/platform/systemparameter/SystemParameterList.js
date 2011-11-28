/**
 * 系统参数列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.bussiness.mainFrameMgr
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.systemparameter');
com.bhtec.view.business.platform.systemparameter.SystemParameterList = function(config){
	var xmlDoc = config.xmlDoc;//域描述信息
	var systemParName_q = 'systemParName_q';
	var systemParaKyName_q = 'systemParaKyName_q';
	var moduleGridId = 'systemParaGridId';
	
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
								id:systemParName_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'paraName'),
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
					items : [com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:systemParaKyName_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'paraKeyName'),
								listeners: {
					                specialkey: function(field, e){
					                    if (e.getKey() == e.ENTER) {
					                       query();
					                    }
					                }
					            }
							})]
				});
		return queryArr;
	}
	/**
	 * 查询操作
	 */
	var query = function(){		
		var configQuery = {
				url : 'systemParameterAction!findSystemParaByCon.action',
				params : {
					paraName : getExtCmpValueById(systemParName_q),
					paraKeyName : getExtCmpValueById(systemParaKyName_q)
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
		resetCmpValueById(systemParName_q);
		resetCmpValueById(systemParaKyName_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'paraId',
				hidden:true,
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'paraName'),
				dataIndex : 'paraName',
				width : 150,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'paraKeyName'),
				dataIndex : 'paraKeyName',
				width : 150,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'paraValue'),
				dataIndex : 'paraValue',
				width : 100,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'paraType'),
				dataIndex : 'paraType',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'status'),
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
				header : getFormFieldName(xmlDoc,'creator'),
				dataIndex : 'creator',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'createDate'),
				dataIndex : 'createDate',
				width : 200,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var moduleStore = new Ext.data.JsonStore({
				fields : ['paraId', 'paraName', 'paraKeyName', 
						  'paraValue', 'paraType',
						   'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'systemParameterServiceList',
				url : 'systemParameterAction!findSystemParaByCon.action'
			});
			
	
	/**
	 * 为翻页加自定义参数
	 */
    moduleStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						paraName : getExtCmpValueById(systemParName_q),
						paraKeyName : getExtCmpValueById(systemParaKyName_q)
					}; 
		Ext.apply(options.params,new_params); 
	});	
	
	
	/**
	 * 工具栏按钮
	 */	
    var toolbar = function(){
		var frametoolbar = new Array();
		var modOptList = fourthModOpt[config.moduleId];
		for(i=0;i<modOptList.length;i++){
			var modOpt = modOptList[i];
			var handlerFun = '';
			if(basicConstant.ADD_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.saveForm;
			}else if(basicConstant.MODIFY_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.modifyForm;
			}else if(basicConstant.DELETE_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.delRecord;
			}else if(basicConstant.VIEW_OPT_LINK == modOpt.optFunLink){
				handlerFun = config.moduleForm.viewForm;
			}else if(basicConstant.ENABLE_OPT_LINK == modOpt.optFunLink){
				handlerFun = function(){config.moduleForm.disEnable(basicConstant.ENABLE_OPT_LINK)};
			}else if(basicConstant.DISABLE_OPT_LINK == modOpt.optFunLink){
				handlerFun = function(){config.moduleForm.disEnable(basicConstant.DISABLE_OPT_LINK)};
			}
			frametoolbar.push({
				text:modOpt.modName,
				iconCls:modOpt.modImgCls,
				handler:handlerFun
			},'-');
		}
		
		return frametoolbar;
	};
	
	/**
	 * 查询区
	 */
	var queryPara = {
			query:query,
			reset:reset,
			queryCondition:queryCondition(),
			queryColWidth:0.2,
			currentPosition:basicConstant.PLTM+'系统管理->系统参数管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:moduleStore,
			gridId		:	moduleGridId
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
