/**
 * 角色分配管理操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.role.RoleOrganVOp
 * @date 2010-09-28
 */
Ext.namespace('com.bhtec.view.business.uum.role.RoleOrganVOp');
com.bhtec.view.business.uum.role.RoleOrganVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'roleOrganGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
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
				url : 'roleOrganAction!findUnselectedRoleByOrgId.action'
			});
	/**
	 * 为翻页加自定义参数
	 */
    listStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						orgId : getExtCmpValueById('roleOrganTreeId')
					}; 
		Ext.apply(options.params,new_params); 
	});	
	
	moduleVOp.window({
		id:'assignRoleWinId',
		title:'选择角色',
		items:moduleVOp.gridPanel({
				id		:	'assignRoleGridId',
				title	:	'为机构选择角色',
				colums  :   cols(),
				border  :   true,
				gridStore:  listStore,
				columnLines : true, 
				buttons:[moduleVOp.saveButton({
							handler:function(){
								var roleSelected = getExtCmpById('assignRoleGridId').getSelectionModel()
											.getSelections();
								if(roleSelected.length == 0){
									warningMesg({
										msg:'请选择一条或多条记录!'
									});
									return false;
								}
								//角色数组
								var roleIdArray = new Array();
								for(i=0;i<roleSelected.length;i++){
									roleIdArray.push(roleSelected[i].data.roleId);
								}
								//机构ID
								var orgId = getExtCmpValueById('roleOrganTreeId');
								var configSave = {
										url:'roleOrganAction!saveOrganRoleRef.action',
										params:{orgId:orgId,roleIds:roleIdArray},
										callBack:function(returnData){
												var configCb = {
													msg : '分配角色成功!',
													fn : function(confirm) {
														if ('ok' == confirm) {
															refreshGridList(moduleGridId);
															getExtCmpById('assignRoleWinId').close();
														}
													}
												}
												showSucMesg(configCb);
										}
								}
								ajaxRequest(configSave);
							}
						}),moduleVOp.closeButton({
							handler:function(){
								this.ownerCt.ownerCt.ownerCt.close();
							}
						})]
			})
	});
	
    };
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		//机构ID
		var orgId = getExtCmpValueById('roleOrganTreeId');
		if(orgId == ''){
			askMesg({
				title:'提示',
				msg  : '请选择要分配的机构!'
			});
			return false;
		}
		busiForm();
	}
	
	/**
	 * 点击删除按钮调用方法
	 */
	var delRecord = function() {
		var roleSelected = getExtCmpById(moduleGridId).getSelectionModel()
											.getSelections();
		if(roleSelected.length == 0){
			warningMesg({
				msg:'请选择一条或多条记录!'
			});
			return false;
		}
		askMesg({
			msg:'您确认删除此记录吗?',
			fn:function(confirm){
				if(confirm == 'ok'){
					//角色数组
					var roleIdArray = new Array();
					for(i=0;i<roleSelected.length;i++){
						roleIdArray.push(roleSelected[i].data.roleId);
					}
					//机构ID
					var orgId = getExtCmpValueById('roleOrganTreeId');
					var configDel = {
						url : 'roleOrganAction!deleteOrganRoleRef.action',
						params : {
							orgId:orgId,
							roleIds:roleIdArray
						},
						callBack : function(returnData) {
							var roleHasUser = returnData.roleHasUser;
							if(roleHasUser){
								warningMesg({
									msg : '您不能删除选择角色,角色下已有用户,请先将相应用户的角色删除!'
								});
							}else{
								showSucMesg({
										msg : '角色删除成功!',
										fn : function(confirm) {
											if ('ok' == confirm) {
												refreshGridList(moduleGridId);
											}
										}
									});
							}
							
						}
					}
					ajaxRequest(configDel);
				}
			}
		});
	}
	
	return {
			saveForm:saveForm,
			delRecord:delRecord
	}
}

Ext.extend(com.bhtec.view.business.uum.role.RoleOrganVOp, com.bhtec.view.util.CommonWidgets, {});