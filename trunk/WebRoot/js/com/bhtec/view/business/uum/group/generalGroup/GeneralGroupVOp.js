/**
 * 普通群组操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.uum.group.GeneralGroupVOp
 * @date 2011-1-22
 */
Ext.namespace('com.bhtec.view.business.uum.group.generalGroup');
com.bhtec.view.business.uum.group.generalGroup.GeneralGroupVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'generalGroupGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldGroupName') != getExtCmpValueById('groupNameId')){
				var syncUrl = 'generalGroupAction!findGroupNameIsExist.action';
				var data = syncAjaxReqDecode(syncUrl,'groupName='+getExtCmpValueById('groupNameId'));
				if(data == '')return;
				if(data.groupNameIsExist == true){
					var configExist = {
						msg:'对不起，组名已经存在!'
					};
					warningMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var uumGroupListStr;
			var mul = getExtCmpById('rightmultiselectGeneralGroup');
			var multiSelCount = mul.store.getCount();
			if(multiSelCount > 0){
				uumGroupListStr = new Array();
				for (var j = 0; j < multiSelCount; j++) {
					var userId = mul.store.getAt(j).get("code");
					uumGroupListStr.push(userId);
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					status : getExtCmpValueById('status'),
					uumGroupListStr:uumGroupListStr
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '普通组保存成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								if (saveFlag.save == 'save') {
									refreshGridList(moduleGridId);
									getExtCmpById('generalGroupWinId').close();
								} else {
									moduleVOp.enableSaveButton();//添加保存enable
									resetForm(basicConstant.FORM_ID);
									refreshGridList(moduleGridId);
									mul.store.removeAll();
									getExtCmpById('leftmultiselectGeneralGroup').store.removeAll();
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
			var url = 'generalGroupAction!saveUumGroup.action';
			if(configForm.modify == true){
				url = 'generalGroupAction!modifyUumGroup.action';
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
				url:'generalGroupAction!saveUumGroup.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		//组名称
		var oldGroupName = new Ext.form.Hidden({
				id : "oldGroupName",
				value:moduleData.groupName||''
		})
		
		//组ID
		var groupId = new Ext.form.Hidden({
				name : "groupId",
				value:moduleData.groupId||''
		});
		
		/**
		 * 公用增加修改删除表单
		 */
		//组名称
		var groupName = moduleVOp.textField({
				id : "groupNameId",
				name : "groupName",
				maxLength  : 20,
				allowBlank:false,
				fieldLabel : config.groupName,
				value:moduleData.groupName||''
		});
		
		//组状态
		var status = moduleVOp.comboBox({
				id : "status",
				fieldLabel : config.status,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.status,
						fields 	: com.bhtec.util.Data.statusFields
					}),
				valueField 	: 'status',
				displayField: 'statusName',
				allowBlank:false,
				value:moduleData.status||'enable'
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
		//组信息
		var generalGroupInfo = moduleVOp.fieldSet({
			title:config.generalGroupInfoAdd,				//窗口title
			columnFields:[groupName,status,memo,
						  creator,createDate,groupId,oldGroupName]	//表单第一列
		});
		var generalGroupMember = new com.bhtec.view.business.uum.group.generalGroup.GeneralGroupMember({
								uumGroupMembers:moduleData.uumGroupMembers,
								fieldNames:config
							});
		//组成员
		var userMember = moduleVOp.fieldSet({
			title:config.generalGroupAdd,				//窗口title
			customColumnItems:true,
			layout:'form',
			columnFields:[generalGroupMember]	//表单第一列
		});
		
		Ext.apply(configForm,{save:save,saveAdd:saveAdd});
		var paddingLeft = 'padding-left:200px;';
		if(Ext.isIE)paddingLeft = 'padding-left:20px;';
		//普通组panel
		var generalGroupPanel = moduleVOp.formPanel({
			id:basicConstant.FORM_ID,
			autoHeight:true,
			items:[generalGroupInfo,userMember,
						{
							xtype	  : 'panel',
							layout 	  : 'column',
							border    : false,
							bodyStyle : paddingLeft,
							autoHeight:true,			
							items:[{
										border : false,
										layout : "form",
										columnWidth : 0.2
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.saveAddButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.saveButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.resetButton(configForm)]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.15,
										items : [moduleVOp.closeButton({winId:moduleVOp.cuvWindowId})]
									},{
										border : false,
										layout : "form",
										columnWidth : 0.2
									}]
						}
			]
		});
		
		moduleVOp.window({
			id:'generalGroupWinId',
			title:configForm.title,
			items:generalGroupPanel,
			height:650
		});
		//为表单域加回车键
		formEnterEvent();
	};
	
	/**
	 * 点击列表保存，弹出保存页面
	 */
	var saveForm = function(){
		var configSave = {
			title:config.generalGroupInfoAdd
		}
		busiForm(configSave);
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleGridId);//请选择一条件记录
		if(modDelRecord != ''){
			var model = {
				groupId:modDelRecord.groupId,
				groupName:modDelRecord.groupName,
				status:modDelRecord.status,
				memo:modDelRecord.memo,
				creator:modDelRecord.creator,
				createDate:modDelRecord.createDate,
				uumGroupMembers:modDelRecord.uumGroupMembers
			}
			var configForm = {
							title:'普通组修改',
							moduleData:model,
							modify:true
						}
			busiForm(configForm);
		}
	}
	
	/**
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'generalGroupAction!disEnableGroup.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.groupId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '普通组启用成功!';
						}else{
							msg = '普通组停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
					}
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
				msg = '此普通组已经启用!';
			}else{
				msg = '此普通组已经停用!';
			}
			warningMesg({
				msg:msg
			})
			return;
		}
		
		disableEnalbeOpt(flag,modDelRecord);
	}
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.uum.group.generalGroup.GeneralGroupVOp,
	com.bhtec.view.util.CommonWidgets, {});