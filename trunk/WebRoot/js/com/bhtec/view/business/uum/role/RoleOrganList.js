/**
 * 角色分配管理列表页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.uum.role.RoleOrganList
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.role');
com.bhtec.view.business.uum.role.RoleOrganList = function(config){
	var roleOrganName_q = 'roleOrganName_q';
	var roleOrganGridId = 'roleOrganGridId';
	var roleOrganTreeId = 'roleOrganTreeId';
	
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
								id:roleOrganName_q,
								width:150,
								fieldLabel : config.roleName,
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
				url : 'roleAction!findRoleByCon.action',
				params : {
					roleName : getExtCmpValueById(roleOrganName_q)
				},
				callBack : function(returnData) {
					queryFillGridList(roleOrganGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(roleOrganName_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				header : 'roleId',
				dataIndex : 'roleId',
				hidden:true,
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.roleName,
				dataIndex : 'roleName',
				width : basicConstant.GRID_COL_WIDTH+50,
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
				width : 200,
				sortable: true
			});
		colsArr.push({
				header : config.memo,
				dataIndex : 'memo',
				width : 200,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var listStore = new Ext.data.JsonStore({
				fields : ['roleId', 'roleName','memo', 
						  'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'roleList',
				url : 'roleOrganAction!findSelectedRoleByOrgId.action'
			});
			
	/**
	 * 树id隐含域
	 */
	var treeIdHidden = new Ext.form.Hidden({
				id:roleOrganTreeId,
				value:''
	});
	
	/**
	 * 为翻页加自定义参数
	 */
    listStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						roleName : getExtCmpValueById(roleOrganName_q),
						treeId	   : getExtCmpValueById(roleOrganTreeId)
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
			if('roleassign' == optFunLink){
				moduleForm.saveForm();
			}else if('roledel' == optFunLink){
				moduleForm.delRecord();
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
							title:'机构树(请首先选择机构)',
							url:'organAction!findNextLevelChildNodes.action',
							rootText:basicConstant.ORGAN_ROOT,
							rootVisible:true,
							clickNode:function(node, e){
								setCmpValueById(roleOrganTreeId,node.id);//点击树结点时，设置结点的id值
								getExtCmpById(roleOrganGridId).setTitle(node.text+'  已分配角色');
								var configFind = {
										url:'roleOrganAction!findSelectedRoleByOrgId.action',
										params:{//带参数查询
												treeId : node.id,
												roleName : getExtCmpValueById(roleOrganName_q)
										},
										callBack:function(returnData){
											queryFillGridList(roleOrganGridId,returnData);//点树结点刷新列表
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
			currentPosition:basicConstant.UUM+'角色管理->角色分配管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:listStore,
			gridId		:	roleOrganGridId,
			treeWin		:	{//先渲染treepanel在装载数据
					treePanel:treePanel
			},
			title:'  已分配角色'
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
