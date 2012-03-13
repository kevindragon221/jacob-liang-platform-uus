/**
 * 用户管理操作
 * @author lianglp
 * @version 1.0
 * @class com.bhtec.view.business.uum.user.UserInfoModify
 * @date 2010-09-23
 */
Ext.namespace('com.bhtec.view.business.commonused.userinfomodify');
com.bhtec.view.business.commonused.userinfomodify.UserInfoModify = function(config){
	var moduleVOp = this;   //父类调用
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function() {
			/**
			 * 检查修改的用户号是否重复
			 */
			if(getExtCmpValueById('oldUserCode') != getExtCmpValueById('userCode')){
				var syncUrl = 'userAction!findUserByUserCode.action';
				var data = syncAjaxReqDecode(syncUrl,'userCodee='+getExtCmpValueById('userCode'));
				if(data == '')return;
				if(data.existUser == true){
					var configExist = {
						msg:'对不起，用户号称已经存在!'
					};
					warningMesg(configExist);
					getExtCmpById('saveButton').setDisabled(false);//添加保存enable
					return false;
				}
			}
			/**
			 * 检查用户原密码是否正确
			 */
			var syncUrl = 'userAction!findUserByUserId.action';
			var data = syncAjaxReqDecode(syncUrl,'modViewRecId='+getExtCmpValueById('userId'));
			if(data != ''){
				var uumUser = data.uumUser;
				if(uumUser.userPassword != getExtCmpValueById('userOldPassword')){
					warningMesg({
						msg:'对不起，原密码不正确!',
						fn:function(confirm){
							var oldPwd = getExtCmpById('userOldPassword');
							oldPwd.setValue('');
							oldPwd.focus();
							getExtCmpById('saveButton').setDisabled(false);//添加保存enable
						}
					})
					return false;
				}
			}
			var configSave = {
				url : 'userAction!modifyUserInfo.action',
				params : {
					userPassword : getExtCmpValueById('userNewPassword')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '用户信息修改成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								getExtCmpById('userInfoModifyId').close();
							}
						}
					}
					showSucMesg(configCb);
				},
				formId:'userInfoModFormPanelId'
			}
			submitForm(configSave);
		}
		
		/**
		 * 老的用户号
		 */
		var oldUserCode = new Ext.form.Hidden({
				id : "oldUserCode",
				value:moduleData.userCode||''
		})
		
		//用户号
		var userCode = moduleVOp.textField({
				id : "userCode",
				name : "userCode",
				allowBlank:false,
				fieldLabel : config.userCode,
				value:moduleData.userCode||'',
				vtype:'alphanum',
				maxLength:15
		});
		
		//用户名
		var userName = moduleVOp.textField({
				name : "userName",
				value:moduleData.userName||'',
				allowBlank:false,
				fieldLabel : config.userName,
				maxLength:15			
		});
		
		//用户ID
		var userId = new Ext.form.Hidden({
				id : "userId",
				name : "userId",
				value:moduleData.userId||''
		});
		
		Ext.apply(Ext.form.VTypes,{
			dateRange:function(val,field){
				if(field.dateRange){
					var newPwdId = field.dateRange.newPwd;
					this.newPwdField = Ext.getCmp(newPwdId);
					var conPwdId = field.dateRange.conPwd;
					this.conPwdField = Ext.getCmp(conPwdId);
					var newPwd = this.newPwdField.getValue();
					var conPwd = this.conPwdField.getValue();
				}
				if(newPwd == conPwd){
					return true;
				}else{
					return false;
				}
			},
			dateRangeText:'两次新密码不一致!'
		})
		
		//用户原密码
		var userOldPassword = moduleVOp.textField({
				id : "userOldPassword",
				value:moduleData.userPassword||'',
				inputType:'password',
				allowBlank:false,
				fieldLabel : config.userOldPassword,
				vtype:'alphanum',
				maxLength:15
		});
		
		//用户新密码
		var userNewPassword = moduleVOp.textField({
				id : "userNewPassword",
				inputType:'password',
				allowBlank:false,
				fieldLabel : config.userNewPassword,
				vtype:'alphanum',
				maxLength:15
		});
		
		//确认密码
		var userConfirmPassword = moduleVOp.textField({
				id : "userConfirmPassword",
				inputType:'password',
				allowBlank:false,
				fieldLabel : config.userConfirmPassword,
				dateRange:{newPwd:'userNewPassword',conPwd:'userConfirmPassword'},
				vtype:'dateRange',
				maxLength:15
		});
		/**
		 * 修改信息列
		 */
		var formCols = [userCode,userName,userOldPassword,userNewPassword,userConfirmPassword,userId];
		var columnFields = new Array();
		for(i=0;i<formCols.length;i++){
			columnFields.push({
					border : false,
					layout : "form",
					columnWidth : 1,
					items : formCols[i]
			});
		}
		/**
		 * 修改信息formpanel
		 */
		var userInfoModFormPanel = moduleVOp.formPanel({
										id:'userInfoModFormPanelId',
										layout:'column',
										items:columnFields,
										buttonAlign:'center',
										labelWidth:80,
										buttons:[moduleVOp.saveButton({
											handler:function(){
												saveCommon();
											}
										}),moduleVOp.closeButton({
											handler:function(){
												this.ownerCt.ownerCt.ownerCt.close();
											}
										})]
								   });
		//调用父类方法进行窗口构造
		moduleVOp.window({
			id:'userInfoModifyId',
			title:configForm.title,				//窗口title
			layout:'fit',
			items:userInfoModFormPanel,
			width:370,
			height:300
		});	
		//为表单域加回车键
		formEnterEvent();
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		var syncUrl = 'baseInfoAction!getUserInfoForModify.action';
		var data = syncAjaxReqDecode(syncUrl);
		if(data == '')return;
		var uumUser = data.uumUser||'';
		busiForm({
			title:'用户信息修改',
			moduleData:uumUser
		});
	}
	
	return {
			saveForm:saveForm
	}
}

Ext.extend(com.bhtec.view.business.commonused.userinfomodify.UserInfoModify, 
		   com.bhtec.view.util.CommonWidgets, {});