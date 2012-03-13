/**
 * 公告列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.affiche.AfficheList
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.affiche');
com.bhtec.view.business.platform.affiche.AfficheList = function(config){
	var afficheTitle_q = 'afficheTitle_q';
	var affichePulish_q = 'affichePulish_q';
	var afficheGridId = 'afficheGridId';
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : "form",
					columnWidth : 0.3,
					items : [
						com.bhtec.view.util.CommonWidgets.prototype.textField({
								id:afficheTitle_q,
								width:150,
								fieldLabel : config.afficheTitle,
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
					columnWidth : 0.3,
					items : [
						//是否发布
						com.bhtec.view.util.CommonWidgets.prototype.radio({
								id : affichePulish_q,
								fieldLabel : config.affichePulish,
								width:150,
								items:[
									{boxLabel: '是', name: 'publish', inputValue: 0},
				                	{boxLabel: '否', name: 'publish', inputValue: 1}
				                ]
						})
						]
				});
		return queryArr;
	}
	/**
	 * 查询操作
	 */
	var query = function(){	
		var affichePulish_para = getExtCmpValueById(affichePulish_q)==null?null:
    							getExtCmpValueById(affichePulish_q).inputValue;
		var configQuery = {
				url : 'afficheAction!findAfficheByCon.action',
				params : {
					afficheTitle : getExtCmpValueById(afficheTitle_q),
					affichePulish : affichePulish_para
				},
				callBack : function(returnData) {
					queryFillGridList(afficheGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(afficheTitle_q);
	}
	
	/**
	 * 公告列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'afficheId',
				width : 150,
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.afficheTitle,
				dataIndex : 'afficheTitle',
				width : 300,
				sortable: true,
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				} 
			});
		colsArr.push({
				header : config.afficheInvalidate,
				dataIndex : 'afficheInvalidate',
				width : 150,
				sortable: true,
				renderer:function(value){
					return formatDateToYMD(value);
				}
			});
		colsArr.push({
				header : config.affichePulish,
				dataIndex : 'affichePulish',
				width : 150,
				sortable: true,
				renderer:function(value){
					if(value == 0)
						return '已发布';
					return '未发布';
				}
			});
		
		return colsArr;
	}
	
	 /**
	  * 主面框架grid store
	  */
	 var store = new Ext.data.JsonStore({
				fields : ['afficheId','afficheTitle',
			           	  'afficheInvalidate','affichePulish'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'afficheList',
				url : 'afficheAction!findAfficheByCon.action'
	});
	
    /**
	 * 为翻页加自定义参数
	 */
    store.on('beforeload', function(thiz,options) {
    	var affichePulish_para = getExtCmpValueById(affichePulish_q)==null?null:
    						getExtCmpValueById(affichePulish_q).inputValue;
    	var new_params = {
			afficheTitle : getExtCmpValueById(afficheTitle_q),
			affichePulish : affichePulish_para
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
			currentPosition:basicConstant.PLTM+'系统管理->系统公告管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:store,
			gridId:afficheGridId
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
