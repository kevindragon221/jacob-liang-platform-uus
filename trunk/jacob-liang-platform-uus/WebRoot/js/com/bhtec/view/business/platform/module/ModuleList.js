/**
 * 主框架列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.bussiness.mainFrameMgr
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.module');
com.bhtec.view.business.platform.module.ModuleList = function(config){
	var xmlDoc = config.xmlDoc;//域描述信息
	var modName_q = 'modName_q';
	var modId_q = 'modId_q';
	var moduleGridId = 'moduleGridId';
	var modTreeId = 'modTreeId';
	
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
								id:modName_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'modName'),
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
								id:modId_q,
								width:150,
								fieldLabel : getFormFieldName(xmlDoc,'modEnId'),
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
				url : 'moduleAction!findModuleByCon.action',
				params : {
					moduleName : getExtCmpValueById(modName_q),
					moduleEnId : getExtCmpValueById(modId_q)
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
		resetCmpValueById(modName_q);
		resetCmpValueById(modId_q);
	}
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				dataIndex : 'moduleId',
				hidden:true,
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'modName'),
				dataIndex : 'modName',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					return '<span qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'modEnId'),
				dataIndex : 'modEnId',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'modImgCls'),
				dataIndex : 'modImgCls',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'modLevel'),
				dataIndex : 'modLevel',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true ,
				renderer:function(data){
					var moduleLevel = com.bhtec.util.Data.moduleLevel;
					for(i=0;i<moduleLevel.length;i++){
						if(data == moduleLevel[i].level){
							return moduleLevel[i].levelName;
						}
					}
				}
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'modOrder'),
				dataIndex : 'modOrder',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'privilegeLevel'),
				dataIndex : 'privilegeLevel',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:function(data){
					var privilegeLevel = com.bhtec.util.Data.privilegeLevel;
					for(i=0;i<privilegeLevel.length;i++){
						if(data == privilegeLevel[i].privilege){
							return privilegeLevel[i].privilegeName;
						}
					}
				}
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'status'),
				dataIndex : 'status',
				width : basicConstant.GRID_COL_WIDTH-10,
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
				width : basicConstant.GRID_COL_WIDTH-10,
				sortable: true 
			});
		colsArr.push({
				header : getFormFieldName(xmlDoc,'createDate'),
				dataIndex : 'createDate',
				width : basicConstant.GRID_COL_WIDTH+40,
				sortable: true
			});
		
		return colsArr;
	}
	 /**
	  * 模块grid store
	  */
    var moduleStore = new Ext.data.JsonStore({
				fields : ['moduleId', 'modName', 'modEnId', 
						  'modImgCls', 'modLevel','modOrder',
						  'privilegeLevel', 'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'moduleList',
				id : 'moduleStoreId',
				url : 'moduleAction!findModuleByCon.action'
			});
			
	/**
	 * 树id隐含域
	 */
	var treeIdHidden = new Ext.form.Hidden({
				id:modTreeId,
				value:''
	});
	
	/**
	 * 为翻页加自定义参数
	 */
    moduleStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						moduleName : getExtCmpValueById(modName_q),
						moduleEnId : getExtCmpValueById(modId_q),
						treeId	   : getExtCmpValueById(modTreeId)
					}; 
		Ext.apply(options.params,new_params); 
	});	
	/**
	 * 模块分配操作
	 */
	var moduleAssignOpts = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		if(modDelRecord.modLevel != 3){
			warningMesg({
				msg : '请您选择第三级模块菜单!'
			});
			return;
		}
		ajaxRequest({
				url : 'moduleAction!obtainAssignedAUnAssignedOpt.action',
				params : {
					moduleId : modDelRecord.moduleId
				},
				callBack : function(returnData) {
					var assiAUnassMap = returnData.assiAUnassMap;
					var assignedModOpts = assiAUnassMap.assignedModOpts;
					var unassignedModOpts = assiAUnassMap.unassignedModOpts;
					
					//已分配的操作
					var assignedArray1 = new Array();//放id和名称数组
					if(assignedModOpts != null){
						for(i=0;i<assignedModOpts.length;i++){
							var operate = assignedModOpts[i];
							var assignedArray2 = new Array();//放id和名称
							assignedArray2.push(operate.operateId);
							assignedArray2.push(operate.operateName);
							assignedArray1.push(assignedArray2);
						}
					}
					//未分配的操作
					var unassignedArray1 = new Array();//放id和名称数组
					if(unassignedModOpts != null){
						for(i=0;i<unassignedModOpts.length;i++){
							var operate = unassignedModOpts[i];
							var unassignedArray2 = new Array();//放id和名称
							unassignedArray2.push(operate.operateId);
							unassignedArray2.push(operate.operateName);
							unassignedArray1.push(unassignedArray2);
						}
					}
					new com.bhtec.view.business.platform.module.ModuleAssignOptVOp({
								moduleName:modDelRecord.modName,
								moduleId : modDelRecord.moduleId,
								assignedArray1:assignedArray1,
								unassignedArray1:unassignedArray1
					});
				}
		});
	}
	
	/**
	 * 工具栏按钮
	 */	
    var toolbar = function(){
		var frametoolbar = new Array();
		var modOptList = fourthModOpt[config.moduleId];
		if(modOptList == undefined)return;
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
			}else if(basicConstant.ROW_PRIVILEGE == modOpt.optFunLink){
				//权限级别
				var privilegeLevel = com.bhtec.view.util.CommonWidgets
					.prototype.comboBox({
						id : "rowprivilegeId",
						width:100,
						emptyText : '行权限',
						store:new Ext.data.JsonStore({
								data 	: com.bhtec.util.Data.privilegeLevel,
								fields 	: com.bhtec.util.Data.privilegeLevelFields
							}),
						valueField 	: 'privilege',
						displayField: 'privilegeName',
						listeners:{
							select:function(combo ,record){
								var moduleGridPanel = getExtCmpById(moduleGridId);
								var moduleSelected = moduleGridPanel.getSelectionModel()
											.getSelections();
								if(moduleSelected.length == 0){
									warningMesg({
										msg:'请选择一条记录!'
									});
									return false;
								}
								//角色数组
								var moduleIdArray = new Array();
								for(i=0;i<moduleSelected.length;i++){
									moduleIdArray.push(moduleSelected[i].data.moduleId);
								}
								ajaxRequest({
									url:'moduleAction!modifyModuleRowPrivilege.action',
									params:{
										moduleIdList:moduleIdArray,
										rowPrivilegeLevel:record.data.privilege
									},
									callBack:function(returnData){
										showSucMesg({
											msg:'行权限设置成功!',
											fn:function(){
												for(i=0;i<moduleSelected.length;i++){
													moduleSelected[i].set('privilegeLevel', record.data.privilege);
												}
												moduleStore = moduleGridPanel.store;
												moduleStore.commitChanges();
											}
										});
										
									}
								});
							}
						}
				});
				frametoolbar.push({iconCls:modOpt.modImgCls});
				frametoolbar.push(privilegeLevel);
				continue;
			}else if('moduleLabel' == modOpt.optFunLink){
				handlerFun = config.moduleForm.moduleLabelOp;
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
							id:'treePanelModuleId',
							title:'模块树',
							rootVisible:true,
							rootText:'模块树',
							url:'moduleAction!findNextLevelChildNodes.action',
							clickNode:function(node, e){
								setCmpValueById(modTreeId,node.id);//点击树结点时，设置结点的id值
								var configFind = {
										url:'moduleAction!findModuleByCon.action',
										params:{//带参数查询
												treeId : node.id,
												moduleName : getExtCmpValueById(modName_q),
												moduleEnId : getExtCmpValueById(modId_q)
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
			currentPosition:basicConstant.PLTM+'模块操作管理->模块菜单管理'
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
