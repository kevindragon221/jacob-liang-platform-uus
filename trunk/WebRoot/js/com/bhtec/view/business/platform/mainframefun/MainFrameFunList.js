/**
 * 主框架列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.mainFrameMgr
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.mainframefun');
com.bhtec.view.business.platform.mainframefun.MainFrameFunList = function(config){
	var funName_q = 'funName_q';
	var mainFrameFunGridId = 'mainFrameFunGridId';
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : "form",
//					height : 40,
					columnWidth : 0.33,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:funName_q,
								width:150,
								fieldLabel : config.funName,
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
				url : 'mainFrameFunAction!findMainFrameFunByCon.action',
				params : {
					mfFunName : getExtCmpValueById(funName_q)
				},
				callBack : function(returnData) {
					queryFillGridList(mainFrameFunGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(funName_q);
	}
	
	/**
	 * 主面框架列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'funId',
				width : 150,
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.funName,
				dataIndex : 'funName',
				width : 150,
				sortable: true
			});
		colsArr.push({
				header : config.funURI,
				dataIndex : 'funURI',
				width : 150,
				sortable: true,
				renderer:function(value){
					var pageStyle = com.bhtec.util.Data.pageStyle;
					for(i=0;i<pageStyle.length;i++){
						if(value == pageStyle[i].pageStyle){
							return pageStyle[i].pageStyleName;
						}
					}
				} 
			});
		colsArr.push({
				header : config.funURIDesc,
				dataIndex : 'funURIDesc',
				width : 150,
				sortable: true
			});
		colsArr.push({
				header : config.funMemo,
				dataIndex : 'funMemo',
				width : 150,
				sortable: true 
			});
		
		return colsArr;
	}
	
	 /**
	  * 主面框架grid store
	  */
	 var store = new Ext.data.JsonStore({
				fields : ['funId','funName',
			           	  'funURI','funURIDesc','funMemo'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'mainFrameFunList',
				id : 'mainFrameFunStoreId',
				url : 'mainFrameFunAction!findMainFrameFunByCon.action'
	});
	
    /**
	 * 为翻页加自定义参数
	 */
    store.on('beforeload', function(thiz,options) {
    	var new_params = {
						mfFunName : getExtCmpValueById('funName_q')
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
			currentPosition:basicConstant.PLTM+'主面框架管理->页面功能区管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:store,
			gridId:mainFrameFunGridId
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
