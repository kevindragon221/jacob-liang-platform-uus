/**
 * 主框架页面操作
 * @author lianglp
 * @version 2.0
 * @class com.bhtec.bussiness.mainFrameMgr.ModuleVOp
 * @date 2010-07-12
 */
Ext.namespace('com.bhtec.view.business.platform.systemparameter');
com.bhtec.view.business.platform.systemparameter.SystemParameterVOp = function(config){
	var moduleVOp = this;   //父类调用
	var moduleGridId = 'systemParaGridId';//form表单id
	
	/**
	 * 功能区增加修改表单
	 */
	var busiForm = function(configForm){
		var moduleData = configForm.moduleData||'';
		/**
		 * 公用保存设置
		 */
		var saveCommon = function(saveFlag) {
			if(getExtCmpValueById('oldParaKeyName') != getExtCmpValueById('paraKeyName')){
				var syncUrl = 'systemParameterAction!findSystemParaByParaName.action';
				var data = syncAjaxReqDecode(syncUrl,'paraKeyName='+getExtCmpValueById('paraKeyName'));
				if(data == '')return;
				if(data.model != null){
					var configExist = {
						msg:'对不起，参数键名称已经存在!'
					}
					warningMesg(configExist);
					moduleVOp.enableSaveButton();//添加保存enable
					return false;
				}
			}
			var configSave = {
				url : saveFlag.url,
				params : {
					paraType : getExtCmpValueById('paraType'),
					status : getExtCmpValueById('status')
				},
				callBack : function(returnData) {
					var configCb = {
						msg : '系统参数保存成功!',
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
			var url = 'systemParameterAction!saveSystemParameter.action';
			if(configForm.modify == true){
				url = 'systemParameterAction!modifySysplSysParameter.action';
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
				url:'systemParameterAction!saveSystemParameter.action',
				save:'saveAdd'
			}
			saveCommon(configSave);
		}
		
		
		var oldParaKeyName = new Ext.form.Hidden({
				id : "oldParaKeyName",
				value:moduleData.paraKeyName||''
		})
		
		//参数ID
		var paraId = new Ext.form.Hidden({
				name : "paraId",
				value:moduleData.paraId||''
		});
		
		/**
		 * 公用增加修改删除表单
		 */
		
		//参数名称
		var paraName = moduleVOp.textField({
				name : "paraName",
				maxLength  : 20,
				allowBlank:false,
				fieldLabel : config.paraName,
				value:moduleData.paraName||''
		});
		
		//参数键名称
		var paraKeyName = moduleVOp.textField({
				id:"paraKeyName",
				name : "paraKeyName",
				value:moduleData.paraKeyName||'',
				fieldLabel : config.paraKeyName,
				maxLength  : 20,
				allowBlank:false,
				style:(moduleData.paraKeyName||'')==''?'':'background:#DFDFDF',
				readOnly:configForm.modify==undefined?false:true,
				listeners:{
					 render: function(c) {
					 	    if(moduleData.paraKeyName == undefined)return;
	                            Ext.QuickTips.register({
		                            target: c.getEl(),
		                            text: '此项修改时不能编辑'
	                            });
                        }
				}	
		});
		
		//参数键值
		var paraValue = moduleVOp.textField({
				name : "paraValue",
				value:moduleData.paraValue||'',
				fieldLabel : config.paraValue,
				maxLength  : 20,
				allowBlank:false
		});
		
		//系统类别
		var paraType = moduleVOp.comboBox({
				id : "paraType",
				fieldLabel : config.paraType,
				store:new Ext.data.JsonStore({
						url:'typeDictionaryAction!findSmallTypeDicByBigTypeCode.action',
						baseParams :{
							bigTypeCode:'systemType'
						},
						root:'sysplDicSmallTypeListt',
						autoLoad : true,
						fields 	: ['smallTypeCode','smallTypeName'],
						listeners :{
					        load:function(){
					        	if(moduleData.paraType){
					          		getExtCmpById('paraType').setValue(moduleData.paraType);
					          	}else{
						        	getExtCmpById('paraType').setValue('platform');
					          	}
					        }
					     } 
					}),
				valueField 	: 'smallTypeCode',
				displayField: 'smallTypeName',
				allowBlank:false
		});
		
		//模块状态
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
			columnFields:[paraName,paraKeyName,paraValue,
						  paraType,status,memo,
						  creator,createDate,paraId,oldParaKeyName],	//表单第一列
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
			title:'系统参数添加'
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
						url:'systemParameterAction!findSysplSysParameterById.action',
						params:{modViewRecId:modDelRecord.paraId},
						callBack:function(returnData){
								var configForm = {
									title:'系统参数修改',
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
					url:'systemParameterAction!findSysplSysParameterById.action',
					params:{modViewRecId:modDelRecord.paraId},
					callBack:function(returnData){
						var configForm = {
							title:'系统参数查看',
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
	 * 启用停用操作
	 */
	var disableEnalbeOpt = function(flag,modDelRecord){
		var moduleGridPanel = getExtCmpById(moduleGridId);
		var selModDelRecord = moduleGridPanel.getSelectionModel().getSelected();
		var configdisEnable = {
				url:'systemParameterAction!disEnablePara.action',
				noMask:true,
				params:{
					modViewRecId:modDelRecord.paraId,
					disEnableFlag:flag
				},
				callBack:function(returnData){
					var msg = '';
					if(returnData.disEnableBol){
						if(flag == 'enable'){
							msg = '系统参数启用成功!';
						}else{
							msg = '系统参数停用成功!';
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
				msg = '此参数已经启用!';
			}else{
				msg = '此参数已经停用!';
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
			viewForm:viewForm,
			disEnable:disEnable
	}
}

Ext.extend(com.bhtec.view.business.platform.systemparameter.SystemParameterVOp,
	com.bhtec.view.util.CommonWidgets, {});