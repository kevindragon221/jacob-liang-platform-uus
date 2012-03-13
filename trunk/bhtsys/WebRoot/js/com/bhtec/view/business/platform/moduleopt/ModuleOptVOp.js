/**
 * 模块操作页面
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.view.business.platform.moduleopt.ModuleOptVOp
 * @date 2010-10-28
 */
Ext.namespace('com.bhtec.view.business.platform.moduleopt');
com.bhtec.view.business.platform.moduleopt.ModuleOptVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleOptGridId = 'moduleOptGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldModOptName') != getExtCmpValueById('operateName')){
				var syncUrl = 'moduleOptAction!findModuleOptByName.action';
				var data = syncAjaxReqDecode(syncUrl,'moduleOptName='+getExtCmpValueById('operateName'));
				if(data == '')return;
				if(data.existModuleOpt == true){
					warningMesg({
						msg:'对不起，模块操作名称已经存在!'
					});
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					optOrder : getExtCmpValueById('optOrder'),
					optGroup : getExtCmpValueById('optGroup'),
					status : getExtCmpValueById('status')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '模块操作保存成功!',
						fn : function(confirm) {
							if ('ok' == confirm) {
								if (saveFlag.save == 'save') {
									refreshGridList(moduleOptGridId);
									getExtCmpById(moduleVOp.cuvWindowId).close();
								} else {
									moduleVOp.enableSaveButton();//添加保存enable
									resetForm(basicConstant.FORM_ID);
									refreshGridList(moduleOptGridId);
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
			var url = 'moduleOptAction!saveModuleOpt.action';
			if(configForm.modify == true){
				url = 'moduleOptAction!modifyModuleOpt.action';
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
				url:'moduleOptAction!saveModuleOpt.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		
		var oldModOptName = new Ext.form.Hidden({
				id : "oldModOptName",
				value:moduleData.operateName||''
		})
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//模块操作名称
		var operateName = moduleVOp.textField({
				id : "operateName",
				name : "operateName",
				allowBlank:false,
				fieldLabel : config.operateName,
				value:moduleData.operateName||'',
				maxLength  : 10
		});
		
		//模块图片
		var optImgLink = moduleVOp.textField({
				name : "optImgLink",
				value:moduleData.optImgLink||'',
				fieldLabel : config.optImgLink,
				maxLength  : 20
		});
		
		//模块操作ID
		var operateId = new Ext.form.Hidden({
				name : "operateId",
				value:moduleData.operateId||''
		});
		
		//模块操作功能链接
		var optFunLink = moduleVOp.textField({
				name : "optFunLink",
				value:moduleData.optFunLink||'',
				maxLength:20,
				fieldLabel : config.optFunLink			
		});
		
		//模块操作顺序
		var optOrder = moduleVOp.comboBox({
				id : "optOrder",
				fieldLabel : config.optOrder,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.number,
						fields 	: com.bhtec.util.Data.numberFields
					}),
				valueField 	: 'num',
				displayField: 'numName',
				value:moduleData.optOrder||1,
				allowBlank:false
		});
		
		//模块操作组
		var optGroup = moduleVOp.comboBox({
				id : "optGroup",
				fieldLabel : config.optGroup,
				store:new Ext.data.JsonStore({
						data 	: com.bhtec.util.Data.number,
						fields 	: com.bhtec.util.Data.numberFields
					}),
				valueField 	: 'num',
				displayField: 'numName',
				allowBlank:false,
				value:moduleData.optGroup||1
		});
		
		//模块操作状态
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
		
		//调用父类方法进行窗口构造
		moduleVOp.cuvWindow({
			title:configForm.title,				//窗口title
			columnFields:[operateName,optImgLink,optFunLink,
						  optOrder,optGroup,status,
						  memo,operateId,creator,createDate],	//表单第一列
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
			title:'模块操作添加'
		}
		busiForm(configSave);
	}
	/**
	 * 点击列表修改，弹出修改页面
	 */
	var modifyForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleOptGridId);//请选择一条件记录
		if(modDelRecord != ''){
				ajaxRequest({
						url:'moduleOptAction!findModuleOptByOperateId.action',
						params:{modViewRecId:modDelRecord.operateId},
						callBack:function(returnData){
								var configForm = {
									title:'模块操作修改',
									moduleData:returnData.model,
									modify:true
								}
								busiForm(configForm);
						}
				});
		}
	}
	/**
	 * 点击列表查看，弹出查看页面
	 */
	var viewForm = function(){
		var modDelRecord = modifyDelSelRecord(moduleOptGridId);//请选择一条件记录
		if(modDelRecord != ''){
			ajaxRequest({
					url:'moduleOptAction!findModuleOptByOperateId.action',
					params:{modViewRecId:modDelRecord.operateId},
					callBack:function(returnData){
						var configForm = {
							title:'模块操作查看',
							moduleData:returnData.model,
							allButtonHidden:true
						}
						busiForm(configForm);
					}
			});
		}
	}
	
	/**
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleOptGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'moduleOptAction!disEnableModuleOpt.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.operateId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '操作启用成功!';
						}else{
							msg = '操作停用成功!';
						}
						selModDelRecord.set('status', flag);
						moduleStore = moduleGridPanel.store;
						moduleStore.commitChanges();
					}else{
						msg = '操作失败!';
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
		var modDelRecord = modifyDelSelRecord(moduleOptGridId);//请选择一条件记录
		if(modDelRecord == '')return;
		if(flag == modDelRecord.status){
			var msg='';
			if(flag == 'enable'){
				msg = '此操作已经启用!';
			}else{
				msg = '此操作已经停用!';
			}
			showSucMesg({
				msg:msg
			})
			return;
		};
		if(flag == 'disable'){
			askMesg({
				msg:'您确认停用此操作吗?如果停用,相应模块下的此操作将自动清除,并重登生效。',
				fn:function(confirm){
					if(confirm == 'ok'){
						disableEnalbeOpt(flag,modDelRecord);
					}
				}
			})
		}else{
			disableEnalbeOpt(flag,modDelRecord);
		}
		
	}
	
	return {
			saveForm:saveForm,
			modifyForm:modifyForm,
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.platform.moduleopt.ModuleOptVOp, com.bhtec.view.util.CommonWidgets, {});