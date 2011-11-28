/**
 * 机构管理列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.bussiness.uum.organ.OrganList
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.organ');
com.bhtec.view.business.uum.organ.OrganList = function(config){
	var xmlDoc = config.xmlDoc;//域描述信息
	var orgName_q = 'orgName_q';
	var orgCode_q = 'orgCode_q';
	var organGridId = 'organGridId';
	var organTreeId = 'organTreeId';
	
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
								id:orgName_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'orgSimpleName'),
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
								id:orgCode_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'orgCode'),
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
				url : 'organAction!findOrganByCon.action',
				params : {
					orgName : getExtCmpValueById(orgName_q),
					orgCode : getExtCmpValueById(orgCode_q)
				},
				callBack : function(returnData) {
					queryFillGridList(organGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(orgName_q);
		resetCmpValueById(orgCode_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				header : 'orgId',
				dataIndex : 'orgId',
				hidden:true,
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'orgSimpleName'),
				dataIndex : 'orgSimpleName',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'orgCode'),
				dataIndex : 'orgCode',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'orgAddress1'),
				dataIndex : 'orgAddress1',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'orgTel1'),
				dataIndex : 'orgTel1',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'orgBeginDate'),
				dataIndex : 'orgBeginDate',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:formatDateToYMD
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'upOrgId'),
				dataIndex : 'upOrgName',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
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
				width : 120,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var listStore = new Ext.data.JsonStore({
				fields : ['orgId', 'orgSimpleName', 'orgCode', 
						  'orgAddress1', 'orgTel1','orgBeginDate',
						  'upOrgName', 'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'orgList',
//				id : 'listStoreId',
				url : 'organAction!findOrganByCon.action'
			});
			
	/**
	 * 树id隐含域
	 */
	var treeIdHidden = new Ext.form.Hidden({
				id:organTreeId,
				value:''
	});
	
	/**
	 * 为翻页加自定义参数
	 */
    listStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						orgName : getExtCmpValueById(orgName_q),
						orgCode : getExtCmpValueById(orgCode_q),
						treeId	   : getExtCmpValueById(organTreeId)
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
			}else if(basicConstant.OPTASSIGN_OPT_LINK == modOpt.optFunLink){
				handlerFun = moduleAssignOpts;
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
	 * 模块树 panel
	 */
	var treePanel = com.bhtec.view.util.CommonWidgets.prototype.asyncTreePanel({
							id:'treePanelOrganId',
							title:'机构树',
							url:'organAction!findNextLevelChildNodes.action',
							rootText:basicConstant.ORGAN_ROOT,
							rootVisible:true,
							clickNode:function(node, e){
								setCmpValueById(organTreeId,node.id);//点击树结点时，设置结点的id值
								var configFind = {
										url:'organAction!findOrganByCon.action',
										params:{//带参数查询
												treeId : node.id,
												orgName : getExtCmpValueById(orgName_q),
												orgCode : getExtCmpValueById(orgCode_q)
										},
										callBack:function(returnData){
											queryFillGridList(organGridId,returnData);//点树结点刷新列表
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
			currentPosition:basicConstant.UUM+'机构管理->机构信息管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:listStore,
			gridId		:	organGridId,
			treeWin		:	{//先渲染treepanel在装载数据
					treePanel:treePanel
			}
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar		:	toolbar(),
		saveForm	:	config.moduleForm.saveForm,
		modifyForm	:	config.moduleForm.modifyForm,
		viewForm	:	config.moduleForm.viewForm
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
