/**
 * 编码列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.code.CodeList
 * @date 2011-01-04
 */
Ext.namespace('com.bhtec.view.business.platform.code');
com.bhtec.view.business.platform.code.CodeList = function(config){
	var codeEngName_code_q = 'codeEngName_code_q';
	var codeName_code_q = 'codeName_code_q';
	var moduleName_code_q = 'moduleName_code_q';
	var codeGridId = 'codeGridId';
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : "form",
					columnWidth : 0.25,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:codeEngName_code_q,
								width:100,
								fieldLabel : config.codeEngName,
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
					layout : "form",
					columnWidth : 0.25,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:codeName_code_q,
								width:100,
								fieldLabel : config.codeName,
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
					layout : "form",
					columnWidth : 0.25,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:moduleName_code_q,
								width:100,
								fieldLabel : config.moduleName,
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
				url : 'codeAction!findCodeByCon.action',
				params : {
					codeEngName : getExtCmpValueById(codeEngName_code_q),
					codeName :  getExtCmpValueById(codeName_code_q),
					moduleName :  getExtCmpValueById(moduleName_code_q)
				},
				callBack : function(returnData) {
					queryFillGridList(codeGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(codeEngName_code_q);
		resetCmpValueById(codeName_code_q);
		resetCmpValueById(moduleName_code_q);
	}
	
	/**
	 * 公告列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'codeId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.codeEngName,
				dataIndex : 'codeEngName',
				width : 100,
				sortable: true,
				renderer:function(value){
					value = value==null?'':value;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : config.codeName,
				dataIndex : 'codeName',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:function(value){
					value = value==null?'':value;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : config.moduleName,
				dataIndex : 'moduleName',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:function(value){
					value = value==null?'':value;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : config.codeEffect,
				dataIndex : 'codeEffect',
				width : 150,
				sortable: true,
				renderer:function(value){
					value = value==null?'':value;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				}
			});
		colsArr.push({
				header : config.delimite,
				dataIndex : 'delimite',
				width :  basicConstant.GRID_COL_WIDTH,
				sortable: true
			});
		colsArr.push({
				header : config.partNum,
				dataIndex : 'partNum',
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
				width : 120,
				sortable: true
			});
		return colsArr;
	}
	
	 /**
	  * 主面框架grid store
	  */
	 var store = new Ext.data.JsonStore({
				fields : ['codeId','codeEngName','codeName',
			           	  'moduleName','codeEffect','delimite',
			           	  'partNum','status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'codeList',
				url : 'codeAction!findCodeByCon.action'
	});
	
    /**
	 * 为翻页加自定义参数
	 */
    store.on('beforeload', function(thiz,options) {
    	var new_params = {
					codeEngName : getExtCmpValueById(codeEngName_code_q),
					codeName :  getExtCmpValueById(codeName_code_q),
					moduleName :  getExtCmpValueById(moduleName_code_q)
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
			queryColWidth:0.125,
			currentPosition:basicConstant.PLTM+'系统管理->系统编码管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:store,
			gridId:codeGridId
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar:toolbar()
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
