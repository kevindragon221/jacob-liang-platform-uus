/**
 * 用户管理列表页面
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserList
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.user');
com.bhtec.view.business.uum.user.UserList = function(config){
	var userName_q = 'userName_q';
	var userCode_q = 'userCode_q';
	var userGridId = 'userGridId';
	var userOrgRoleTreeId = 'userOrgRoleTreeId';
	var commWidgets = new com.bhtec.view.util.CommonWidgets();
	
	/**
	 * 查询条件
	 */
	var queryCondition = function(){ 
		var queryArr = new Array();
		queryArr.push({
					border : false,
					layout : 'form',
					columnWidth : 0.3,
					items : [commWidgets.textField({
								id:userCode_q,
								width:150,
								fieldLabel : config.userCode,
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
					items : [commWidgets.textField({
								id:userName_q,
								width:150,
								fieldLabel : config.userName,
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
				url : 'userAction!findUserByCon.action',
				params : {
					userName : getExtCmpValueById(userName_q),
					userCode : getExtCmpValueById(userCode_q)
				},
				callBack : function(returnData) {
					queryFillGridList(userGridId,returnData);
				}
			}
			ajaxRequest(configQuery);
	}
	/**
	 * 重置查询
	 */
	var reset = function(){
		resetCmpValueById(userName_q);
		resetCmpValueById(userCode_q);
	}
	
	/**
	 * 模块列模式
	 */
	var cols = function(){
		var colsArr = new Array();
		colsArr.push({
				header : 'userId',
				dataIndex : 'userId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : 'uumOrgRoleId',
				dataIndex : 'uumOrgRoleId',
				hidden:true,
				sortable: true 
			});
		colsArr.push({
				header : config.userCode,
				dataIndex : 'userCode',
				width : 60,
				sortable: true 
			});
		colsArr.push({
				header : config.userName,
				dataIndex : 'userName',
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.userGender,
				dataIndex : 'userGender',
				width : 40,
				sortable: true,
				renderer:function(data){
					var gender = com.bhtec.util.Data.gender;
					for(i=0;i<gender.length;i++){
						if(data == gender[i].genderId){
							return gender[i].genderDes;
						}
					}
				}
			});
		colsArr.push({
				header : config.userPosition,
				dataIndex : 'userPosition',
				width : basicConstant.GRID_COL_WIDTH,
				renderer:function(value){
					if(value == null)return;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				sortable: true 
			});
		colsArr.push({
				header : config.uumRole,
				dataIndex : 'uumRoleName',
				renderer:function(value){
					if(value == null)return;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.uumOrgan,
				dataIndex : 'uumOrgName',
				renderer:function(value){
					if(value == null)return;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		colsArr.push({
				header : config.userQq,
				dataIndex : 'userQq',
				renderer:function(value){
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true,
				renderer:formatDateToYMD
			});
		
		colsArr.push({
				header : config.userMobile,
				dataIndex : 'userMobile',
				renderer:function(value){
					if(value == null)return;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		
		colsArr.push({
				header : config.userEmail,
				dataIndex : 'userEmail',
				renderer:function(value){
					if(value == null)return;
					return '<span ext:qtip="'+value+'">'+value+'</span>';
				},
				width : basicConstant.GRID_COL_WIDTH,
				sortable: true 
			});
		
		colsArr.push({
				header : config.status,
				dataIndex : 'status',
				width : 60,
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
				width : 50,
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
	  * 模块grid store
	  */
    var listStore = new Ext.data.JsonStore({
				fields : ['userId','uumOrgRoleId', 'userCode', 'userName', 
						  'userGender', 'userPosition','uumRoleName',
						  'uumOrgName','userQq','userMobile',
						   'userEmail','userAvidate',
						  'status','creator','createDate'],
				autoLoad : true,
				totalProperty : 'count',
				root : 'userList',
				url : 'userAction!findUserByCon.action'
			});
			
	/**
	 * 树id隐含域
	 */
	var treeIdHidden = new Ext.form.Hidden({
				id:userOrgRoleTreeId,
				value:''
	});
	var treeNameHidden = new Ext.form.Hidden({
				id:'userOrgRoleTreeNodeNameId',
				value:''
	});
	
	/**
	 * 为翻页加自定义参数
	 */
    listStore.on('beforeload', function(thiz,options) {
    	var new_params = {
						userName : getExtCmpValueById(userName_q),
						userCode : getExtCmpValueById(userCode_q),
						treeId	   : getExtCmpValueById(userOrgRoleTreeId)
					}; 
		Ext.apply(options.params,new_params); 
	});	
	
	/**
	 * 模块树 panel
	 */
	var treePanel = commWidgets.asyncTreePanel({
							title:'机构角色树',
							rootText:basicConstant.ORGAN_ROOT,
							url:'userAction!findNextLevelChildNodes.action',
							rootVisible:true,
							clickNode:function(node, e){
								setCmpValueById(userOrgRoleTreeId,node.id);//点击树结点时，设置结点的id值
								setCmpValueById('userOrgRoleTreeNodeNameId',(node.parentNode.text)+'-'+node.text);//点击树结点时，设置结点的id值
								var configFind = {
										url:'userAction!findUserByCon.action',
										params:{//带参数查询
												orgOrRoleId : node.id,
												userName : getExtCmpValueById(userName_q),
												userCode : getExtCmpValueById(userCode_q)
										},
										callBack:function(returnData){
											queryFillGridList(userGridId,returnData);//点树结点刷新列表
										}
								};
								ajaxRequest(configFind);
							}
					});	
	/**
	 * 用户分配角色
	 */
	var userAssignRole = function(){
		var modDelRecord = modifyDelSelRecord(userGridId);//请选择一条件记录
		if(modDelRecord.status == 'disable'){
			warningMesg({
				msg:'请先启用此用户！'
			});
			return;
		}
		if(modDelRecord != ''){
			ajaxRequest({
					url : 'userAction!findRoleUserListByUserId.action',
					params : {
						userId : modDelRecord.userId
					},
					callBack : function(returnData) {
						var roleUserRefList = returnData.roleUserRefList;
						var roleUserRefArray1 = new Array();//放id和名称数组
						if(roleUserRefList != null){
							for(i=0;i<roleUserRefList.length;i++){
								var roleUserRef = roleUserRefList[i];
								var roleUserRefArray2 = new Array();//放id和名称
								roleUserRefArray2.push(roleUserRef.orgRoleId);
								roleUserRefArray2.push(roleUserRef.organName+'-'+roleUserRef.roleName);
								roleUserRefArray1.push(roleUserRefArray2);
							}
						}
						new com.bhtec.view.business.uum.user.UserAssignRole({
									userName:modDelRecord.userName,
									userId:modDelRecord.userId,
									roleUserRefArray1:roleUserRefArray1,
									defaultRoleNamee:(modDelRecord.uumOrgName+'-'+modDelRecord.uumRoleName),
									defaultRoleIdd:modDelRecord.uumOrgRoleId
						});
					}
			});
		}
	};
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
			}else if(basicConstant.OPTPRI_OPT_LINK == optFunLink){
					var modDelRecord = modifyDelSelRecord(userGridId);//请选择一条件记录
					if(modDelRecord.status == 'disable'){
						warningMesg({
							msg:'请先启用此用户！'
						});
						return;
					}else if(modDelRecord.uumOrgRoleId == 0){
						warningMesg({
							msg:'请先未此用户分配有效角色！'
						});
						return;
					}
					if(modDelRecord != ''){
						new com.bhtec.view.business.
						uum.user.UserAssignModOpt({
								userName:modDelRecord.userName,
								userId:modDelRecord.userId
							});
					}
			}else if('assignrole' == optFunLink){
				userAssignRole();
			}else if('resetpwd' == optFunLink){
				var modDelRecord = modifyDelSelRecord(userGridId);//请选择一条件记录
				if(modDelRecord != ''){
					ajaxRequest({
						url:'userAction!resetPwd.action',
						noMask:true,
						params:{
							userId:modDelRecord.userId,
							userName:modDelRecord.userName
						},
						callBack:function(returnData){
							showSucMesg({
								msg:'密码重置成功!'
							})
						}
					})
				}
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
			currentPosition:basicConstant.UUM+'用户管理->用户信息管理'
	}
	/**
	 * 列表区
	 */
	var gridListPara = {
			cols:cols(),
			store:listStore,
			gridId		:	userGridId,
			treeWin		:	{//先渲染treepanel在装载数据
					treePanel:treePanel
			},
			treeWidth:0.2,
			gridWidth:0.8
	}
	/**
	 * 按钮区
	 */
	var toolbarPara = {
		toolbar		:	toolbar()
	}
	/**
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
