/**
 * 角色管理操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.role.RoleVOp
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.uum.role.RoleVOp');
com.bhtec.view.business.uum.role.RoleVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'roleGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldRoleName') != getExtCmpValueById('roleName')){
				var syncUrl = 'roleAction!findRoleByRoleName.action';
				var data = syncAjaxReqDecode(syncUrl,'roleName='+getExtCmpValueById('roleName'));
				if(data == '')return;
				if(data.existRole == true){
					warningMesg({
						msg:'对不起，角色名称已经存在!'
					});
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			
			var configSave = {
				url : saveFlag.url,
				params : {
					status : getExtCmpValueById('status'),
					roleLevel : getExtCmpValueById('roleLevel')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '角色保存成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								if (saveFlag.save == 'save') {
									refreshGridList(moduleGridId);
									getExtCmpById(moduleVOp.cuvWindowId).close();
								} else {
									moduleVOp.enableSaveButton();//添加保存enable
									resetForm(basicConstant.FORM_ID);
									refreshGridList(moduleGridId);
								}
							}
						}
					}
					showSucMesg(configCb);
				}
			}
			submitForm(configSave);
		}
		
		/**
		 * 点击保存按钮调用方法
		 */
		var save = function() {
			var url = 'roleAction!saveRole.action';
			if(configForm.modify == true){
				url = 'roleAction!modifyRole.action';
			}
			var configSave = {
				url:url,
				save:'save'
			}
			saveCommon(configSave);
		}

		/**
		 * 点击保存增加按钮调用方法
		 */
		var saveAdd = function() {
			var configSave = {
				url:'roleAction!saveRole.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		/**
		 * 修改前的角色名称
		 */
		var oldRoleName = new Ext.form.Hidden({
				id : "oldRoleName",
				value:moduleData.roleName||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//角色名称
		var roleName = moduleVOp.textField({
				id : "roleName",
				name : "roleName",
				allowBlank:false,
				maxLength:10,
				fieldLabel : config.roleName,
				value:moduleData.roleName||''
		});
		
		//角色级别
		var roleLevel = moduleVOp.comboBox({
				id : "roleLevel",
				fieldLabel : config.roleLevel,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.number,
						fields 	: com.bhtec.util.Data.numberFields
					}),
				valueField 	: 'num',
				displayField: 'numName',
				value:moduleData.roleLevel||'1',
				allowBlank:false
		});
	
		//角色状态
		var status = moduleVOp.comboBox({
				id : "status",
				fieldLabel : config.status,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.status,
						fields 	: com.bhtec.util.Data.statusFields
					}),
				valueField 	: 'status',
				displayField: 'statusName',
				value:moduleData.status||'enable',
				allowBlank:false
		});
		
		//角色ID
		var roleId = new Ext.form.Hidden({
				id : "roleId",
				value:moduleData.roleId||''
		});
		
		//备注
		var memo = moduleVOp.textField({
				name : "memo",
				value:moduleData.memo||'',
				fieldLabel : config.memo,
				maxLength:50
		});
		
		//创建时间
		var creator = new Ext.form.Hidden({
				name : "creator",
				value:moduleData.creator||''
		});
		//创建人
		var createDate = new Ext.form.Hidden({
				name : "createDate",
				value:moduleData.createDate||''
		});
		
		//调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			columnFields:[roleName,roleLevel,status,memo,
						  roleId,creator,createDate],	//表单第一列
			save:save,							//保存按钮调用的方法
			saveAdd:saveAdd,					//保存并添加按钮调用的方法
			modify:configForm.modify,			//窗口判断是否显示保存增加按钮
			allButtonHidden:configForm.allButtonHidden
		});	
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		var configSave = {
			title:'角色添加'
		}
		busiForm(configSave);
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
				var configFind = {
						url:'roleAction!findRoleByRoleId.action',
						params:{modViewRecId:modDelRecord.roleId},
						callBack:function(returnData){
								var configForm = {
									title:'角色修改',
									moduleData:returnData.model,
									modify:true
								}
								busiForm(configForm);
						}
				}
				ajaxRequest(configFind);
		}
	}
	/**
	 * 点击列表查看，弹出查看页面
	 */
	var viewForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var configFind = {
					url:'roleAction!findRoleByRoleId.action',
					params:{modViewRecId:modDelRecord.roleId},
					callBack:function(returnData){
						var configForm = {
							title:'角色查看',
							moduleData:returnData.model,
							allButtonHidden:true
						}
						busiForm(configForm);
					}
			}
			ajaxRequest(configFind);
		}
	}
	/**
	 * 停用启用操作
	 */
	var disableEnableOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'roleAction!disEnableRole.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.roleId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(flag == 'enable'){
						msg = '角色启用成功!';
					}else{
						msg = '角色停用成功!';
					}
					selModDelRecord.set('status', flag);
					moduleStore = moduleGridPanel.store;
					moduleStore.commitChanges();
					var configCb = {
						msg : msg,
						fn : function(confirm) {
						}
					}
					showSucMesg(configCb);
				}
		}
		ajaxRequest(configdisEnable);
	}
	
	/**
	 * 启用 停用
	 */
	var disEnable = function(flag){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		if(flag == modDelRecord.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此角色已经启用!';
			}else{
				msg = '此角色已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		}
		
		if(flag == 'enable'){
			disableEnableOpt(flag,modDelRecord);
		}else{
			ajaxRequest({
				url:'roleAction!roleDisableCheckUser.action',
				noMask:true,
				params:{
					roleId:modDelRecord.roleId
				},
				callBack:function(returnData){
					var disEnableResultFlag = returnData.disEnableResultFlag;
					if(disEnableResultFlag == 1){
						warningMesg({
							msg:'角色已分配用户,您不能停用此角色!如停用,请先将用户角色删除。'
						});
						return;
					}else{
						askMesg({
							msg:'您确认停用此角色?如停用,相应机构下的此角色将自动清除。',
							fn:function(confirm){
								if('ok' == confirm){
									disableEnableOpt(flag,modDelRecord);
								}
							}
						});
					}
				}
			});
		}
	}
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.uum.role.RoleVOp, com.bhtec.view.util.CommonWidgets, {});