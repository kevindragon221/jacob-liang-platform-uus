/**
 * 模块操作列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.moduleopt
 * @date 2010-10-28
 */
Ext.namespace('com.bhtec.view.business.platform.moduleopt');
com.bhtec.view.business.platform.moduleopt.ModuleOptList = function(config){
	var modOptName_q = 'modOptName_q';
	var moduleOptGridId = 'moduleOptGridId';
	
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
								id:modOptName_q,
								width:150,
								fieldLabel : config.operateName,
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
				url : 'moduleOptAction!findModuleOptByCon.action',
				params : {
					moduleOptName : getExtCmpValueById(modOptName_q)
				},
				callBack : function(returnData) {
					queryFillGridList(moduleOptGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(modOptName_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'operateId',
				hidden:true,
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.operateName,
				dataIndex : 'operateName',
				width : 100,
				sortable: true,
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : config.optImgLink,
				dataIndex : 'optImgLink',
				width : 100,
				sortable: true 
			});
		colsArr.push({
				header : config.optFunLink,
				dataIndex : 'optFunLink',
				width : 100,
				sortable: true 
			});
		colsArr.push({
				header : config.optOrder,
				dataIndex : 'optOrder',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.optGroup,
				dataIndex : 'optGroup',
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
				fields : ['operateId', 'operateName', 'optImgLink','optFunLink', 
						  'optOrder', 'optGroup','status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'moduleOptList',
				id : 'moduleStoreId',
				url : 'moduleOptAction!findModuleOptByCon.action'
			});
			
	/**
	 * 为翻页加自定义参数
	 */
    moduleStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						moduleName : getExtCmpValueById(modOptName_q)
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
			}else if(basicConstant.DELETE_OPT_LINK == optFunLink){
				moduleForm.delRecord();
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
	 * 查询区
	 */
	var queryPara = {
			query:query,
			reset:reset,
			queryCondition:queryCondition(),
			queryColWidth:0.2,
			currentPosition:basicConstant.PLTM+'模块操作管理->操作按钮管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:moduleStore,
			gridId	:	moduleOptGridId,
			width	: 	basicConstant.BODY_WIDTH-230
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
